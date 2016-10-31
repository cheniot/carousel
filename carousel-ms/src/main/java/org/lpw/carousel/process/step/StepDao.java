package org.lpw.carousel.process.step;

/**
 * @author lpw
 */
public interface StepDao {
    void save(StepModel step);

    int count(String process);
}
