package org.lpw.carousel.config;

import net.sf.json.JSONObject;

/**
 * @author lpw
 */
public interface ConfigService {
    enum Update {Success, Exception, Empty, NameIllegal, ActionsIllegal, HandlerIllegal}

    String VALIDATOR_EXISTS=ConfigModel.NAME+".validator.exists";

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

    /**
     * 检索数据。
     *
     * @param name     配置名称，模糊匹配。
     * @param pageSize 每页显示记录数。
     * @param pageNum  当前显示页数。
     * @return 数据集。
     */
    JSONObject query(String name, int pageSize, int pageNum);
}
