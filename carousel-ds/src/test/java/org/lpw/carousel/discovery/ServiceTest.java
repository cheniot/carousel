package org.lpw.carousel.discovery;

import org.junit.Assert;
import org.junit.Test;
import org.lpw.tephra.test.HttpAspect;
import org.lpw.tephra.test.SchedulerAspect;
import org.lpw.tephra.util.Thread;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lpw
 */
public class ServiceTest extends TestSupport {
    @Inject
    private Thread thread;
    @Inject
    private SchedulerAspect schedulerAspect;
    @Inject
    private HttpAspect httpAspect;
    @Inject
    private DiscoveryService discoveryService;

    @Test
    public void executeSecondsJob() throws Exception {
        schedulerAspect.pause();
        httpAspect.reset();
        List<String> urls = new ArrayList<>();
        List<Map<String, String>> headers = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        contents.add("content");
        httpAspect.get(urls, headers, parameters, contents);

        DiscoveryModel discovery0 = create("key 0", 0, "success.*");
        Assert.assertEquals(0, discovery0.getState());
        execute(urls, headers, parameters);
        Assert.assertEquals(1, urls.size());
        Assert.assertEquals("validate 0", urls.get(0));
        http(1, headers, parameters);
        state(discovery0, 1);

        DiscoveryModel discovery1 = create("key 1", 1, "success.*");
        discovery1.setValidate("validate 0");
        liteOrm.save(discovery1);
        Assert.assertEquals(1, discovery1.getState());
        execute(urls, headers, parameters);
        Assert.assertEquals(1, urls.size());
        Assert.assertEquals("validate 0", urls.get(0));
        http(1, headers, parameters);
        state(discovery0, 2);
        state(discovery1, 2);

        contents.clear();
        for (int i = 0; i < 2; i++)
            contents.add("success " + i);
        for (int i = 0; i < 2; i++) {
            execute(urls, headers, parameters);
            Assert.assertEquals(1, urls.size());
            Assert.assertEquals("validate 0", urls.get(0));
            http(1, headers, parameters);
            state(discovery0, 0);
            state(discovery1, 0);
        }

        DiscoveryModel discovery2 = create("key 2", 2);
        for (int i = 0; i < 20; i++) {
            execute(urls, headers, parameters);
            if (i < 10) {
                Assert.assertEquals(2, urls.size());
                Assert.assertTrue(urls.contains("validate 0"));
                Assert.assertTrue(urls.contains("validate 2"));
                http(2, headers, parameters);
                state(discovery2, i + 1);
            } else {
                Assert.assertEquals(1, urls.size());
                Assert.assertEquals("validate 0", urls.get(0));
                http(1, headers, parameters);
                state(discovery2, 10);
            }
            state(discovery0, 0);
            state(discovery1, 0);
        }

        Field field = DiscoveryServiceImpl.class.getDeclaredField("failure");
        field.setAccessible(true);
        field.set(discoveryService, 0);
        execute(urls, headers, parameters);
        Assert.assertEquals(2, urls.size());
        Assert.assertTrue(urls.contains("validate 0"));
        Assert.assertTrue(urls.contains("validate 2"));
        http(2, headers, parameters);
        state(discovery0, 0);
        state(discovery1, 0);
        state(discovery2, 11);

        field.set(discoveryService, 0);
        field = DiscoveryServiceImpl.class.getDeclaredField("validate");
        field.setAccessible(true);
        field.set(discoveryService, false);
        execute(urls, headers, parameters);
        Assert.assertEquals(0, urls.size());
        http(0, headers, parameters);
        state(discovery0, 0);
        state(discovery1, 0);
        state(discovery2, 11);

        field.set(discoveryService, true);
        httpAspect.reset();
        schedulerAspect.press();
    }

    private void execute(List<String> urls, List<Map<String, String>> headers, List<Object> parameters) {
        urls.clear();
        headers.clear();
        parameters.clear();
        ((DiscoveryServiceImpl) discoveryService).executeSecondsJob();
    }

    private void http(int size, List<Map<String, String>> headers, List<Object> parameters) {
        Assert.assertEquals(size, headers.size());
        for (int i = 0; i < size; i++)
            Assert.assertNull(headers.get(i));
        Assert.assertEquals(size, parameters.size());
        for (int i = 0; i < size; i++)
            Assert.assertEquals("", parameters.get(i));
    }

    private void state(DiscoveryModel discovery, int state) {
        Assert.assertEquals(state, findById(discovery.getId()).getState());
    }
}
