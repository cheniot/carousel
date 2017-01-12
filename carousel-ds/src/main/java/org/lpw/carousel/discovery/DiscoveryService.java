package org.lpw.carousel.discovery;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @author lpw
 */
public interface DiscoveryService {
    /**
     * 注册服务。
     *
     * @param key      服务key。
     * @param service  服务URL地址。
     * @param validate 验证URL地址。
     * @param success  验证成功标识。
     */
    void register(String key, String service, String validate, String success);

    /**
     * 执行请求。
     *
     * @param header    请求头。
     * @param parameter 参数。
     * @return 执行结果。
     */
    String execute(Map<String, String> header, Map<String, String> parameter);

    /**
     * 检索数据。
     * @param key 服务key，模糊匹配。
     * @param service 服务URL地址，模糊匹配。
     * @param pageSize 每页显示记录数。
     * @param pageNum 当前显示页数。
     * @return 数据集。
     */
    JSONObject query(String key, String service, int pageSize,int pageNum);
}
