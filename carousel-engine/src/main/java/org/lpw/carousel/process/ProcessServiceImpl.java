package org.lpw.carousel.process;

import org.lpw.carousel.config.ConfigModel;
import org.lpw.carousel.config.ConfigService;
import org.lpw.carousel.process.step.StepService;
import org.lpw.tephra.util.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author lpw
 */
@Service("carousel.process.service")
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    protected ConfigService configService;
    @Autowired
    protected StepService stepService;
    @Autowired
    protected ProcessDao processDao;
    @Value("${carousel.process.max-failure:9}")
    protected int maxFailure;

    @Override
    public ProcessModel begin(ConfigModel config, int delay, String data) {
        if (config == null)
            return null;

        if (delay <= 0)
            delay = config.getDelay();
        if (delay < 0)
            delay = 0;

        ProcessModel process = new ProcessModel();
        process.setConfig(config.getId());
        process.setIn(data);
        process.setOut(data);
        process.setStart(new Timestamp(System.currentTimeMillis() + TimeUnit.Second.getTime() * delay));
        process.setEnd(new Timestamp(System.currentTimeMillis()));
        processDao.save(process);

        return process;
    }

    @Override
    public void done(String process, int step, String data) {
        done(process, step, data, true);
        stepService.save(process, step, data);
    }

    @Override
    public void failure(String processId, int step, String data) {
        ProcessModel process = processDao.findById(processId);
        if (process == null)
            return;

        process.setFailure(process.getFailure() + 1);
        processDao.save(process);
        stepService.save(processId, step, data);
    }

    @Override
    public ProcessModel finish(String processId, int step, String data) {
        ProcessModel process = done(processId, step, data, false);
        if (process == null)
            return null;

        ConfigModel config = configService.findById(process.getConfig());
        if (config == null)
            return null;

        process.setTimes(process.getTimes() + 1);
        if (config.getInterval() == 0 || process.getTimes() >= config.getTimes()) {
            process.setState(1);
            processDao.save(process);

            return null;
        }

        process.setStep(0);
        process.setFailure(0);
        processDao.save(process);

        return process;
    }

    protected ProcessModel done(String processId, int step, String data, boolean save) {
        ProcessModel process = processDao.findById(processId);
        if (process == null)
            return null;

        process.setStep(step);
        process.setOut(data);
        process.setEnd(new Timestamp(System.currentTimeMillis()));
        if (save)
            processDao.save(process);

        return process;
    }

    @Override
    public List<ProcessModel> unfinished() {
        return processDao.state(0, maxFailure).getList();
    }

    @Override
    public int getMaxFailure() {
        return maxFailure;
    }
}
