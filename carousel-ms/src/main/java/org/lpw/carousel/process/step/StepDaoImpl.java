package org.lpw.carousel.process.step;

import org.lpw.tephra.dao.orm.PageList;
import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.dao.orm.lite.LiteQuery;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Repository(StepModel.NAME + ".dao")
public class StepDaoImpl implements StepDao {
    @Inject
    private LiteOrm liteOrm;

    @Override
    public void save(StepModel step) {
        liteOrm.save(step);
    }

    @Override
    public int count(String process) {
        return liteOrm.count(new LiteQuery(StepModel.class).where("c_process=?"), new Object[]{process});
    }

    @Override
    public PageList<StepModel> query(String process) {
        return liteOrm.query(new LiteQuery(StepModel.class).where("c_process=?").order("c_serial"), new Object[]{process});
    }
}
