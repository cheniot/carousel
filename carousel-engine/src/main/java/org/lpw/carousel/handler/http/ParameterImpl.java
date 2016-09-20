package org.lpw.carousel.handler.http;

import net.sf.json.JSONObject;
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
    protected Map<String, String> map = new HashMap<>();

    @Override
    public void set(JSONObject object) {
        if (!object.has("url"))
            return;

        map.put("url", object.getString("url"));
        map.put("success", object.has("success") ? object.getString("success") : "");
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <T> T get(String key) {
        return (T) map.get(key);
    }
}
