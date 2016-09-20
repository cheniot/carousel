package org.lpw.carousel.engine;

/**
 * 引擎接口规范。
 *
 * @author lpw
 */
public interface Engine {
    /**
     * 处理流程。
     *
     * @param name  流程配置名称。
     * @param delay 延迟时间，单位：秒。
     * @param data  要处理的数据。
     * @return 处理结果。
     */
    Object execute(String name, int delay, String data);

    /**
     * 执行处理器。
     * 用于处理异常时重新加入到处理队列中。
     *
     * @param executer 处理器。
     */
    void execute(Executer executer);
}
