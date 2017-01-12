package org.lpw.carousel.process;

import org.lpw.tephra.dao.orm.PageList;
import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.dao.orm.lite.LiteQuery;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Repository(ProcessModel.NAME + ".dao")
public class ProcessDaoImpl implements ProcessDao {
    @Inject
    private LiteOrm liteOrm;

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

    @Override
    public PageList<ProcessModel> query(String config, int pageSize, int pageNum) {
        return liteOrm.query(new LiteQuery(ProcessModel.class).where("c_config=?").order("c_start desc").size(pageSize).page(pageNum), new Object[]{config});
    }
}
