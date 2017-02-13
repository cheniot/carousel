package org.lpw.carousel.handler.http;

import com.alibaba.fastjson.JSONObject;
import org.lpw.carousel.handler.Parameter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lpw
 */
@Component("carousel.handler.http.parameter")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ParameterImpl implements Parameter {
    private Map<String, String> map = new HashMap<>();

    @Override
    public void set(JSONObject object) {
        if (!object.containsKey("url"))
            return;

        map.put("url", object.getString("url"));
        map.put("success", object.containsKey("success") ? object.getString("success") : "");
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <T> T get(String key) {
        return (T) map.get(key);
    }
}
