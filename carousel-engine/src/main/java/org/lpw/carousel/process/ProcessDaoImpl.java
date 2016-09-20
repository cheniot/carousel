package org.lpw.carousel.process;

import org.lpw.tephra.dao.orm.PageList;
import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository("carousel.process.dao")
public class ProcessDaoImpl implements ProcessDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public ProcessModel findById(String id) {
        return liteOrm.findById(ProcessModel.class, id);
    }

    @Override
    public void save(ProcessModel process) {
        liteOrm.save(process);
    }

    @Override
    public PageList<ProcessModel> state(int state, int failure) {
        return liteOrm.query(new LiteQuery(ProcessModel.class).where("c_state=? and c_failure<=?"), new Object[]{state, failure});
    }
}
