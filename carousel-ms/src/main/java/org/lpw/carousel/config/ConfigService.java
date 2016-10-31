package org.lpw.carousel.config;

/**
 * @author lpw
 */
public interface ConfigService {
    enum Update {Success, Exception, Empty, NameIllegal, ActionsIllegal, HandlerIllegal}

    /**
     * 更新配置。
     *
     * @param value 配置值。
     * @return 更新结果。
     */
    Update update(String value);

    /**
     * 获取帮助信息。
     *
     * @param handler 处理器名称。
     * @return 帮助信息。
     */
    String help(String handler);

    /**
     * 获取配置值。
     *
     * @param id 配置ID值。
     * @return 配置；如果不存在则返回null。
     */
    ConfigModel findById(String id);

    /**
     * 获取配置值。
     *
     * @param name 配置名称值。
     * @return 配置；如果不存在则返回null。
     */
    ConfigModel findByName(String name);

    /**
     * 获取执行步骤集。
     *
     * @param id 配置ID值。
     * @return 执行步骤集。
     */
    Action[] getActions(String id);
}
