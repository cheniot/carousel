package org.lpw.carousel.handler;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lpw
 */
public interface Parameter {
    /**
     * 设置配置参数。
     *
     * @param object 配置参数实例。
     */
    void set(JSONObject object);

    /**
     * 获取参数值。
     *
     * @param key 参数key。
     * @param <T> 参数值类型。
     * @return 参数值；如果不存在则返回null。
     */
    <T> T get(String key);
}
