package org.lpw.carousel.process;

import org.lpw.carousel.config.ConfigModel;

import java.util.List;

/**
 * @author lpw
 */
public interface ProcessService {
    /**
     * 开启流程。
     *
     * @param config 流程配置。
     * @param delay  延迟时间，单位：秒。
     * @param data   处理数据。
     * @return 处理流程实例。
     */
    ProcessModel begin(ConfigModel config, int delay, String data);

    /**
     * 标记执行完成步骤。
     *
     * @param process 流程ID值。
     * @param step    执行完成步骤。
     * @param data    执行结果。
     */
    void done(String process, int step, String data);

    /**
     * 执行失败。
     *
     * @param process 流程ID值。
     * @param step    执行完成步骤。
     * @param data    执行结果。
     */
    void failure(String process, int step, String data);

    /**
     * 结束流程。
     *
     * @param process 流程ID值。
     * @param step    执行步骤。
     * @param data    结果数据。
     * @return 如果重新开始执行则返回当前流程；否则返回null。
     */
    ProcessModel finish(String process, int step, String data);

    /**
     * 检索未完成的流程集。
     *
     * @return 未完成的流程集。
     */
    List<ProcessModel> unfinished();

    /**
     * 获取最大失败次数设置。
     *
     * @return 最大失败次数设置。
     */
    int getMaxFailure();
}
