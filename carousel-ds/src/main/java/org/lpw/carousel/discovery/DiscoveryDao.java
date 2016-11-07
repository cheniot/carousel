package org.lpw.carousel.discovery;

import org.lpw.tephra.dao.orm.PageList;

/**
 * @author lpw
 */
interface DiscoveryDao {
    DiscoveryModel findByKeyService(String key, String service);

    void save(DiscoveryModel discovery);

    PageList<DiscoveryModel> query(String key, int state);

    PageList<DiscoveryModel> query();
}
