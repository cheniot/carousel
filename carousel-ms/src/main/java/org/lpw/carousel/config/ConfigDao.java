package org.lpw.carousel.config;

import org.lpw.tephra.dao.orm.PageList;

/**
 * @author lpw
 */
public interface ConfigDao {
    ConfigModel findById(String id);

    ConfigModel findByName(String name);

    void save(ConfigModel config);

    void delete(String name);

    PageList<ConfigModel> query();
}
