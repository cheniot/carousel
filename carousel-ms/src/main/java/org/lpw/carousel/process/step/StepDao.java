package org.lpw.carousel.process.step;

import org.lpw.tephra.dao.orm.PageList;

/**
 * @author lpw
 */
public interface StepDao {
    void save(StepModel step);

    int count(String process);

    PageList<StepModel> query(String process);
}
