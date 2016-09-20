package org.lpw.carousel.process.step;

/**
 * @author lpw
 */
public interface StepService {
    /**
     * 保存步骤数据。
     *
     * @param process 流程ID值。
     * @param index   步骤。
     * @param data    数据。
     */
    void save(String process, int index, String data);
}
