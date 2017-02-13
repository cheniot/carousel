package org.lpw.carousel.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.lpw.carousel.handler.Handler;
import org.lpw.carousel.handler.HandlerFactory;
import org.lpw.tephra.bean.BeanFactory;
import org.lpw.tephra.cache.Cache;
import org.lpw.tephra.util.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;

/**
 * @author lpw
 */
@Service("carousel.config.service")
public class ConfigServiceImpl implements ConfigService {
    private static final String CACHE_ID = "carousel.config.service.id:";
    private static final String CACHE_NAME = "carousel.config.service.name:";
    private static final String CACHE_ACTIONS = "carousel.config.service.actions:";
    private static final String CACHE_HELP = "carousel.config.service.help:";

    @Inject
    private Context context;
    @Inject
    private Cache cache;
    @Inject
    private Converter converter;
    @Inject
    private Validator validator;
    @Inject
    private Io io;
    @Inject
    private Logger logger;
    @Inject
    private HandlerFactory handlerFactory;
    @Inject
    private ConfigDao configDao;

    @Override
    public Update update(String value) {
        JSONObject json;
        try {
            json = JSON.parseObject(value);
        } catch (Exception e) {
            logger.warn(e, "解析配置[{}]为JSON时发生异常！", value);

            return Update.Exception;
        }

        if (json == null || json.isEmpty())
            return Update.Empty;

        if (!json.containsKey("name"))
            return Update.NameIllegal;

        if (!json.containsKey("actions"))
            return Update.ActionsIllegal;

        String name = json.getString("name");
        if (validator.isEmpty(name))
            return Update.NameIllegal;

        JSONArray array = json.getJSONArray("actions");
        if (array.isEmpty())
            return Update.ActionsIllegal;

        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            if (object.isEmpty() || !object.containsKey("handler"))
                return Update.HandlerIllegal;

            Handler handler = handlerFactory.get(object.getString("handler"));
            if (handler == null || !handler.isValid(object.getJSONObject("parameter")))
                return Update.HandlerIllegal;
        }

        ConfigModel config = configDao.findByName(name);
        if (config == null) {
            config = new ConfigModel();
            config.setName(name);
        }
        if (json.containsKey("description"))
            config.setDescription(json.getString("description"));
        if (json.containsKey("delay"))
            config.setDelay(Math.max(0, converter.toInt(json.get("delay"))));
        if (json.containsKey("interval"))
            config.setInterval(Math.max(0, converter.toInt(json.get("interval"))));
        if (json.containsKey("times"))
            config.setTimes(Math.max(0, converter.toInt(json.get("times"))));
        if (json.containsKey("wait"))
            config.setWait(Math.max(0, Math.min(1, converter.toInt(json.get("wait")))));
        config.setValue(json.toString());
        config.setTime(new Timestamp(System.currentTimeMillis()));
        configDao.save(config);
        cache.put(CACHE_ID + config.getId(), config, false);
        cache.put(CACHE_NAME + name, config, false);
        cache.remove(CACHE_ACTIONS + config.getId());

        if (logger.isInfoEnable())
            logger.info("更新流程配置[{}]。", json);

        return Update.Success;
    }

    @Override
    public String help(String handler) {
        if (validator.isEmpty(handler) || handlerFactory.get(handler) == null)
            handler = "config";
        String key = CACHE_HELP + handler;
        String help = cache.get(key);
        if (help == null) {
            help = new String(io.read(context.getAbsolutePath("/WEB-INF/help/" + handler + ".json")));
            if (handler.equals("config"))
                help = help.replaceAll("HANDLERS", converter.toString(handlerFactory.names()));
            cache.put(key, help, false);
        }

        return help;
    }

    @Override
    public ConfigModel findById(String id) {
        String key = CACHE_ID + id;
        ConfigModel config = cache.get(key);
        if (config == null)
            cache.put(key, config = configDao.findById(id), false);

        return config;
    }

    @Override
    public ConfigModel findByName(String name) {
        String key = CACHE_NAME + name;
        ConfigModel config = cache.get(key);
        if (config == null)
            cache.put(key, config = configDao.findByName(name), false);

        return config;
    }

    @Override
    public Action[] getActions(String id) {
        String key = CACHE_ACTIONS + id;
        Action[] actions = cache.get(key);
        if (actions == null) {
            ConfigModel config = findById(id);
            if (config == null)
                return null;

            JSONArray array = JSON.parseObject(config.getValue()).getJSONArray("actions");
            actions = new Action[array.size()];
            for (int i = 0; i < actions.length; i++) {
                JSONObject object = array.getJSONObject(i);
                actions[i] = BeanFactory.getBean(Action.class);
                actions[i].setName(object.getString("name"));
                actions[i].setHandler(object.getString("handler"));
                actions[i].setParameter(handlerFactory.newParameter(actions[i].getHandler()));
                if (actions[i].getParameter() != null)
                    actions[i].getParameter().set(object.getJSONObject("parameter"));
            }
            cache.put(key, actions, false);
        }

        return actions;
    }

    @Override
    public JSONObject query(String name, int pageSize, int pageNum) {
        return configDao.query(name, pageSize, pageNum).toJson();
    }
}
