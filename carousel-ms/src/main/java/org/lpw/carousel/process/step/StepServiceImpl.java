package org.lpw.carousel.process.step;

import com.alibaba.fastjson.JSONArray;
import org.lpw.tephra.dao.model.ModelHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;

/**
 * @author lpw
 */
@Service(StepModel.NAME + ".service")
public class StepServiceImpl implements StepService {
    @Inject
    private ModelHelper modelHelper;
    @Inject
    private StepDao stepDao;

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

    @Override
    public JSONArray query(String process) {
        return modelHelper.toJson(stepDao.query(process).getList());
    }
}
