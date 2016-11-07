package org.lpw.carousel.discovery;

import org.lpw.tephra.dao.orm.PageList;
import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(DiscoveryModel.NAME + ".dao")
class DiscoveryDaoImpl implements DiscoveryDao {
    @Autowired
    protected LiteOrm liteOrm;

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
}
