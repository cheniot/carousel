package org.lpw.carousel.handler;

import net.sf.json.JSONObject;

/**
 * 处理器接口规范。
 *
 * @author lpw
 */
public interface Handler {
    /**
     * 处理器名称，必须全局唯一。
     *
     * @return 处理器名称。
     */
    String getName();

    /**
     * 验证参数是否有效。
     *
     * @param parameter 参数值。
     * @return 如果有效则返回true；否则返回false。
     */
    boolean isValid(JSONObject parameter);

    /**
     * 创建新的参数实例。
     *
     * @return 新的参数实例。
     */
    Parameter newParameter();

    /**
     * 执行业务处理。
     *
     * @param parameter 参数配置。
     * @param object    要进行处理的数据。
     * @return 处理后的数据。
     */
    Object execute(Parameter parameter, Object object);

    /**
     * 判断执行结果是否成功。
     *
     * @param parameter 参数配置。
     * @param object    执行结果。
     * @return 如果成功则返回true；否则返回false。
     */
    boolean isSuccess(Parameter parameter, Object object);
}
