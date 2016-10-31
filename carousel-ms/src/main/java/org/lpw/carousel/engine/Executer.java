package org.lpw.carousel.engine;

import org.lpw.carousel.process.ProcessModel;

import java.util.concurrent.Callable;

/**
 * 流程执行器。
 *
 * @author lpw
 */
public interface Executer extends Callable<Object> {
    /**
     * 设置流程参数。
     *
     * @param process 流程实例。
     * @return 当前流程执行器。
     */
    Executer set(ProcessModel process);

    /**
     * 重置执行器。
     *
     * @return 当前流程执行器。
     */
    Executer reset();
}
