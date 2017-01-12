package org.lpw.carousel.discovery;

import net.sf.json.JSONObject;
import org.lpw.tephra.cache.Cache;
import org.lpw.tephra.dao.orm.PageList;
import org.lpw.tephra.scheduler.SecondsJob;
import org.lpw.tephra.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lpw
 */
@Service(DiscoveryModel.NAME + ".service")
public class DiscoveryServiceImpl implements DiscoveryService, SecondsJob {
    private static final String CACHE_KEY = DiscoveryModel.NAME + ".service.key:";

    @Inject
    private Cache cache;
    @Inject
    private Generator generator;
    @Inject
    private Validator validator;
    @Inject
    private Http http;
    @Inject
    private DateTime dateTime;
    @Inject
    private Logger logger;
    @Inject
    private DiscoveryDao discoveryDao;
    @Value("${" + DiscoveryModel.NAME + ".validate:true}")
    private boolean validate;
    @Value("${" + DiscoveryModel.NAME + ".failure:9}")
    private int failure;
    @Value("${" + DiscoveryModel.NAME + ".success:}")
    private String success;

    @Override
    public void register(String key, String service, String validate, String success) {
        DiscoveryModel discovery = discoveryDao.findByKeyService(key, service);
        if (discovery == null) {
            discovery = new DiscoveryModel();
            discovery.setKey(key);
            discovery.setService(service);
        }
        discovery.setValidate(validate);
        discovery.setSuccess(validator.isEmpty(success) ? this.success : success);
        discovery.setState(0);
        discovery.setRegister(dateTime.now());
        discoveryDao.save(discovery);
        cache.remove(CACHE_KEY + key);

        if (logger.isDebugEnable())
            logger.debug("注册服务[key={},service={},validate={},success={}]。", key, service, validate, success);
    }

    @Override
    public String execute(Map<String, String> header, Map<String, String> parameter) {
        String service = get(header.get("carousel-ds-key"));
        if (service == null)
            return null;

        return http.post(service, header, parameter);
    }

    private String get(String key) {
        String[] array = query(key);

        return array.length > 0 ? array[generator.random(0, array.length - 1)] : null;
    }

    private String[] query(String key) {
        String cacheKey = CACHE_KEY + key;
        String[] array = cache.get(cacheKey);
        if (array == null) {
            PageList<DiscoveryModel> pl = discoveryDao.query(key, 0);
            array = new String[pl.getList().size()];
            for (int i = 0; i < array.length; i++)
                array[i] = pl.getList().get(i).getService();
            cache.put(cacheKey, array, false);
        }

        return array;
    }

    @Override
    public JSONObject query(String key, String service, int pageSize, int pageNum) {
        return discoveryDao.query(key, service, pageSize, pageNum).toJson();
    }

    @Override
    public void executeSecondsJob() {
        if (!validate)
            return;

        Map<String, List<DiscoveryModel>> map = new HashMap<>();
        discoveryDao.query().getList().forEach(discovery -> {
            if (discovery.getState() > failure && failure > 0)
                return;

            List<DiscoveryModel> list = map.get(discovery.getValidate());
            if (list == null)
                list = new ArrayList<>();
            list.add(discovery);
            map.put(discovery.getValidate(), list);
        });
        map.keySet().forEach(validate -> {
            if (validator.isMatchRegex(map.get(validate).get(0).getSuccess(), http.get(validate, null, ""))) {
                map.get(validate).stream().filter(discovery -> discovery.getState() != 0).forEach(discovery -> {
                    discovery.setState(0);
                    discoveryDao.save(discovery);
                    cache.remove(CACHE_KEY + discovery.getKey());
                });
            } else {
                map.get(validate).forEach(discovery -> {
                    discovery.setState(discovery.getState() + 1);
                    discoveryDao.save(discovery);
                    cache.remove(CACHE_KEY + discovery.getKey());
                });
            }
        });
    }
}
