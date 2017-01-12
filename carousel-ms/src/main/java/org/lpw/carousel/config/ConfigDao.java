package org.lpw.carousel.config;

import org.lpw.tephra.dao.orm.PageList;

/**
 * @author lpw
 */
public interface ConfigDao {
    ConfigModel findById(String id);

    ConfigModel findByName(String name);

    void save(ConfigModel config);

    PageList<ConfigModel> query(String name, int pageSize, int pageNum);
}
