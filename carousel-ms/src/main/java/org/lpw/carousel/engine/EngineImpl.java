package org.lpw.carousel.engine;

import org.lpw.carousel.config.ConfigModel;
import org.lpw.carousel.config.ConfigService;
import org.lpw.carousel.process.ProcessService;
import org.lpw.tephra.bean.BeanFactory;
import org.lpw.tephra.bean.ContextClosedListener;
import org.lpw.tephra.bean.ContextRefreshedListener;
import org.lpw.tephra.scheduler.SecondsJob;
import org.lpw.tephra.util.Logger;
import org.lpw.tephra.util.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author lpw
 */
@Service("carousel.engine")
public class EngineImpl implements Engine, ContextRefreshedListener, ContextClosedListener, SecondsJob {
    @Inject
    private Validator validator;
    @Inject
    private Logger logger;
    @Inject
    private ConfigService configService;
    @Inject
    private ProcessService processService;
    @Value("${carousel.engine.pool-size:5}")
    private int poolSize;
    @Value("${carousel.engine.auto:true}")
    private boolean auto;
    private ExecutorService pool;
    private List<Executer> executers;

    @Override
    public Object execute(String name, int delay, String data) {
        ConfigModel config = configService.findByName(name);
        if (config == null)
            return null;

        Future<Object> future = pool.submit(BeanFactory.getBean(Executer.class).set(processService.begin(config, delay, data)));
        if (config.getWait() == 0)
            return null;

        try {
            return future.get();
        } catch (Exception e) {
            logger.warn(e, "执行流程[{}:{}]时发生异常！", config.getValue(), data);

            return null;
        }
    }

    @Override
    public void execute(Executer executer) {
        executers(executer);
    }

    @Override
    public int getContextRefreshedSort() {
        return 17;
    }

    @Override
    public void onContextRefreshed() {
        pool = Executors.newFixedThreadPool(poolSize);
        executers = new ArrayList<>();
        if (auto)
            processService.unfinished().forEach(process -> executers.add(BeanFactory.getBean(Executer.class).set(process)));
    }

    @Override
    public int getContextClosedSort() {
        return 17;
    }

    @Override
    public void onContextClosed() {
        pool.shutdownNow();
    }

    @Override
    public void executeSecondsJob() {
        if (validator.isEmpty(executers))
            return;

        executers(null).forEach(executer -> pool.submit(executer.reset()));
    }

    private synchronized List<Executer> executers(Executer executer) {
        if (executer != null) {
            executers.add(executer);

            return null;
        }

        List<Executer> list = new ArrayList<>(executers);
        executers.clear();

        return list;
    }
}
