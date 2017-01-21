package org.lpw.carousel.discovery;

import org.lpw.carousel.CarouselTestSupport;
import org.lpw.tephra.dao.orm.lite.LiteOrm;
import org.lpw.tephra.dao.orm.lite.LiteQuery;
import org.lpw.tephra.util.TimeUnit;

import javax.inject.Inject;
import java.sql.Timestamp;

/**
 * @author lpw
 */
public class TestSupport extends CarouselTestSupport {
    @Inject
    LiteOrm liteOrm;

    DiscoveryModel create(String key, int i) {
        return create(key, i, "success " + i);
    }

    DiscoveryModel create(String key, int i, String success) {
        DiscoveryModel discovery = new DiscoveryModel();
        discovery.setKey(key);
        discovery.setService("service " + i);
        discovery.setValidate("validate " + i);
        discovery.setSuccess(success);
        discovery.setState(i % 2);
        discovery.setRegister(new Timestamp(System.currentTimeMillis() - i * TimeUnit.Hour.getTime()));
        liteOrm.save(discovery);

        return discovery;
    }

    DiscoveryModel findById(String id) {
        return liteOrm.findById(DiscoveryModel.class, id);
    }

    DiscoveryModel findByKeyService(String key, String service) {
        return liteOrm.findOne(new LiteQuery(DiscoveryModel.class).where("c_key=? and c_service=?"), new Object[]{key, service});
    }
}
