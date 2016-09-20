package org.lpw.carousel.config;

import org.lpw.carousel.handler.Parameter;

/**
 * 执行步骤配置。
 *
 * @author lpw
 */
public interface Action {
    /**
     * 获取步骤名称。
     *
     * @return 步骤名称。
     */
    String getName();

    /**
     * 设置步骤名称。
     *
     * @param name 步骤名称。
     */
    void setName(String name);

    /**
     * 获取处理器名称。
     *
     * @return 处理器名称。
     */
    String getHandler();

    /**
     * 设置处理器名称。
     *
     * @param handler 处理器名称。
     */
    void setHandler(String handler);

    /**
     * 获取处理器参数配置。
     *
     * @return 处理器参数配置。
     */
    Parameter getParameter();

    /**
     * 设置处理器参数配置。
     *
     * @param parameter 处理器参数配置。
     */
    void setParameter(Parameter parameter);
}
