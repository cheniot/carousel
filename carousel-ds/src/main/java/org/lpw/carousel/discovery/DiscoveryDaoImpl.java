package org.lpw.carousel.discovery;

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
@Repository(DiscoveryModel.NAME + ".dao")
class DiscoveryDaoImpl implements DiscoveryDao {
    @Inject
    private Validator validator;
    @Inject
    private DataSource dataSource;
    @Inject
    private LiteOrm liteOrm;

    @Override
    public DiscoveryModel findByKeyService(String key, String service) {
        return liteOrm.findOne(new LiteQuery(DiscoveryModel.class).where("c_key=? and c_service=?"), new Object[]{key, service});
    }

    @Override
    public void save(DiscoveryModel discovery) {
        liteOrm.save(discovery);
    }

    @Override
    public PageList<DiscoveryModel> query(String key, int state) {
        return liteOrm.query(new LiteQuery(DiscoveryModel.class).where("c_key=? and c_state=?"), new Object[]{key, state});
    }

    @Override
    public PageList<DiscoveryModel> query() {
        return liteOrm.query(new LiteQuery(DiscoveryModel.class), null);
    }

    @Override
    public PageList<DiscoveryModel> query(String key, String service, int pageSize, int pageNum) {
        StringBuilder where = new StringBuilder();
        List<Object> args = new ArrayList<>();
        if (!validator.isEmpty(key)) {
            where.append("c_key like ?");
            args.add(dataSource.getDialect(null).getLike(key, true, true));
        }
        if (!validator.isEmpty(service)) {
            if (!args.isEmpty())
                where.append(" and ");
            where.append("c_service like ?");
            args.add(dataSource.getDialect(null).getLike(service, true, true));
        }

        return liteOrm.query(new LiteQuery(DiscoveryModel.class).where(where.toString()).order("c_register desc").size(pageSize).page(pageNum), args.toArray());
    }
}
