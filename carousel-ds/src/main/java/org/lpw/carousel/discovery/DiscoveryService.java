package org.lpw.carousel.discovery;

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
     */
    void register(String key, String service, String validate);

    /**
     * 执行请求。
     *
     * @param header    请求头。
     * @param parameter 参数。
     * @return 执行结果。
     */
    String execute(Map<String, String> header, Map<String, String> parameter);
}
