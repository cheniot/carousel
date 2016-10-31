package org.lpw.carousel.process.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author lpw
 */
@Service(StepModel.NAME + ".service")
public class StepServiceImpl implements StepService {
    @Autowired
    protected StepDao stepDao;

    @Override
    public void save(String process, int index, String data) {
        StepModel step = new StepModel();
        step.setProcess(process);
        step.setSerial(stepDao.count(process));
        step.setIndex(index);
        step.setData(data);
        step.setTime(new Timestamp(System.currentTimeMillis()));
        stepDao.save(step);
    }
}
