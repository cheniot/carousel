package org.lpw.carousel.process;

import org.lpw.tephra.dao.orm.PageList;

/**
 * @author lpw
 */
public interface ProcessDao {
    ProcessModel findById(String id);

    void save(ProcessModel process);

    PageList<ProcessModel> state(int state, int failure);
}
