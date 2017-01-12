package org.lpw.carousel.engine;

import org.lpw.carousel.config.Action;
import org.lpw.carousel.config.ConfigModel;
import org.lpw.carousel.config.ConfigService;
import org.lpw.carousel.handler.Handler;
import org.lpw.carousel.handler.HandlerFactory;
import org.lpw.carousel.process.ProcessModel;
import org.lpw.carousel.process.ProcessService;
import org.lpw.tephra.atomic.Closable;
import org.lpw.tephra.util.Logger;
import org.lpw.tephra.util.TimeUnit;
import org.lpw.tephra.util.Validator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author lpw
 */
@Service("carousel.engine.executer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExecuterImpl implements Executer {
    @Inject
    private Validator validator;
    @Inject
    private Logger logger;
    @Inject
    private Set<Closable> closables;
    @Inject
    private ConfigService configService;
    @Inject
    private HandlerFactory handlerFactory;
    @Inject
    private ProcessService processService;
    @Inject
    private Engine engine;
    private ConfigModel config;
    private String process;
    private Object data;
    private int index;
    private long time;
    private int failure;
    private boolean reset;

    @Override
    public Executer set(ProcessModel process) {
        this.process = process.getId();
        config = configService.findById(process.getConfig());
        data = process.getStep() == 0 ? process.getIn() : process.getOut();
        index = process.getStep();
        time = process.getStart().getTime() + process.getTimes() * config.getInterval() * TimeUnit.Second.getTime();
        failure = process.getFailure();

        return this;
    }

    @Override
    public Executer reset() {
        reset = true;

        return this;
    }

    @Override
    public Object call() throws Exception {
        if (config == null || process == null || failure >= processService.getMaxFailure())
            return null;

        if (reset)
            config = configService.findById(config.getId());
        if (time > System.currentTimeMillis() || (failure > 0 && time + (TimeUnit.Second.getTime() << (failure + 2)) > System.currentTimeMillis())) {
            engine.execute(this);

            return null;
        }

        try {
            execute();
        } catch (Exception e) {
            engine.execute(this);

            logger.warn(e, "执行流程[{}:{}]时发生异常！", config, index);
        } finally {
            commit();
        }

        return data;
    }

    private void execute() {
        Action[] actions = configService.getActions(config.getId());
        if (validator.isEmpty(actions))
            return;

        for (; index < actions.length; index++)
            if (!execute(actions[index]))
                break;

        ProcessModel process;
        if (index < actions.length || (process = processService.finish(this.process, index, data.toString())) == null)
            return;

        data = process.getIn();
        index = 0;
        time += config.getInterval() * TimeUnit.Second.getTime();
        failure = 0;
        engine.execute(this);
    }

    private boolean execute(Action action) {
        Handler handler = handlerFactory.get(action.getHandler());
        if (handler == null)
            return false;

        time = System.currentTimeMillis();
        Object object = handler.execute(action.getParameter(), data);
        if (object == null || !handler.isSuccess(action.getParameter(), object)) {
            failure++;
            processService.failure(process, index + 1, object == null ? null : object.toString());
            engine.execute(this);

            return false;
        }

        data = object;
        processService.done(process, index + 1, data.toString());

        return true;
    }

    private void commit() {
        closables.forEach(Closable::close);
    }
}
