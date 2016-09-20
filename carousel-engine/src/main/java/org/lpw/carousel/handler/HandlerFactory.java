package org.lpw.carousel.handler;

import java.util.Set;

/**
 * 处理器工厂。
 *
 * @author lpw
 */
public interface HandlerFactory {
    /**
     * 获取指定名称的处理器。
     *
     * @param name 名称。
     * @return 处理器；如果不存在则返回null。
     */
    Handler get(String name);

    /**
     * 创建新的参数实例。
     *
     * @param handler 处理器名称。
     * @return 新的参数实例。
     */
    Parameter newParameter(String handler);

    /**
     * 获取处理器名称集。
     *
     * @return 处理器名称集。
     */
    Set<String> names();
}
