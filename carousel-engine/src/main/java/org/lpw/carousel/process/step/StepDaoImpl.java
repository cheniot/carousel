package org.lpw.carousel.process.step;

import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository("carousel.process.step.dao")
public class StepDaoImpl implements StepDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public void save(StepModel step) {
        liteOrm.save(step);
    }

    @Override
    public int count(String process) {
        return liteOrm.count(new LiteQuery(StepModel.class).where("c_process=?"), new Object[]{process});
    }
}
