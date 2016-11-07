package org.lpw.carousel.discovery;

import org.lpw.tephra.bean.ContextRefreshedListener;
import org.lpw.tephra.cache.Cache;
import org.lpw.tephra.dao.orm.PageList;
import org.lpw.tephra.scheduler.SecondsJob;
import org.lpw.tephra.util.Generator;
import org.lpw.tephra.util.Http;
import org.lpw.tephra.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lpw
 */
@Service(DiscoveryModel.NAME + ".service")
public class DiscoveryServiceImpl implements DiscoveryService, ContextRefreshedListener, SecondsJob {
    private static final String CACHE_KEY = DiscoveryModel.NAME + ".service.key:";

    @Autowired
    protected Cache cache;
    @Autowired
    protected Generator generator;
    @Autowired
    protected Http http;
    @Autowired
    protected Logger logger;
    @Autowired
    protected DiscoveryDao discoveryDao;
    @Value("${" + DiscoveryModel.NAME + ".pool-size:5}")
    protected int poolSize;
    protected ExecutorService pool;

    @Override
    public void register(String key, String service, String validate) {
        DiscoveryModel discovery = discoveryDao.findByKeyService(key, service);
        if (discovery == null) {
            discovery = new DiscoveryModel();
            discovery.setKey(key);
            discovery.setService(service);
        }
        discovery.setValidate(validate);
        discovery.setState(0);
        discovery.setRegister(new Timestamp(System.currentTimeMillis()));
        discoveryDao.save(discovery);
        cache.remove(CACHE_KEY + key);
    }

    @Override
    public String execute(Map<String, String> header, Map<String, String> parameter) {
        String service = get(header.get("carousel-ds-key"));
        if (service == null)
            return null;

        return http.post(service, header, parameter);
    }

    protected String get(String key) {
        String[] array = query(key);

        return array.length > 0 ? array[generator.random(0, array.length - 1)] : null;
    }

    protected String[] query(String key) {
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
    public int getContextRefreshedSort() {
        return 21;
    }

    @Override
    public void onContextRefreshed() {
        pool = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void executeSecondsJob() {
        Map<String, List<DiscoveryModel>> map = new HashMap<>();
        discoveryDao.query().getList().forEach(discovery -> {
            if (discovery.getState() > 9)
                return;

            List<DiscoveryModel> list = map.get(discovery.getValidate());
            if (list == null)
                list = new ArrayList<>();
            list.add(discovery);
            map.put(discovery.getValidate(), list);
        });
        map.keySet().forEach(validate -> {
            String string = http.get(validate, null, "");
            if (string != null && string.startsWith("{\"code\":0,")) {
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
