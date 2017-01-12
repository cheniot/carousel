package org.lpw.carousel.process.step;

import net.sf.json.JSONArray;

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

    /**
     * 检索步骤集。
     *
     * @param process 流程ID值。
     * @return 步骤集。
     */
    JSONArray query(String process);
}
