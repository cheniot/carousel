package org.lpw.carousel.config;

import org.lpw.tephra.dao.jdbc.DataSource;
import org.lpw.tephra.dao.orm.PageList;
import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.dao.orm.lite.LiteQuery;
import org.lpw.tephra.util.Validator;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpw
 */
@Repository(ConfigModel.NAME + ".dao")
public class ConfigDaoImpl implements ConfigDao {
    @Inject
    private Validator validator;
    @Inject
    private DataSource dataSource;
    @Inject
    private LiteOrm liteOrm;

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
    public PageList<ConfigModel> query(String name, int pageSize, int pageNum) {
        StringBuilder where = new StringBuilder();
        List<Object> args = new ArrayList<>();
        if (!validator.isEmpty(name)) {
            where.append("c_key like ?");
            args.add(dataSource.getDialect(null).getLike(name, true, true));
        }

        return liteOrm.query(new LiteQuery(ConfigModel.class).where(where.toString()).order("c_time desc").size(pageSize).page(pageNum), args.toArray());
    }
}
