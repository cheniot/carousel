package org.lpw.carousel.config;

import org.lpw.tephra.dao.orm.PageList;
import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(ConfigModel.NAME + ".dao")
public class ConfigDaoImpl implements ConfigDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public ConfigModel findById(String id) {
        return liteOrm.findById(ConfigModel.class, id);
    }

    @Override
    public ConfigModel findByName(String name) {
        return liteOrm.findOne(new LiteQuery(ConfigModel.class).where("c_name=?"), new Object[]{name});
    }

    @Override
    public void save(ConfigModel config) {
        liteOrm.save(config);
    }

    @Override
    public void delete(String name) {
        liteOrm.delete(new LiteQuery(ConfigModel.class).where("c_name=?"), new Object[]{name});
    }

    @Override
    public PageList<ConfigModel> query() {
        return liteOrm.query(new LiteQuery(ConfigModel.class), null);
    }
}
