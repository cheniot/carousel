package org.lpw.carousel.discovery;

import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.lpw.tephra.cache.Cache;
import org.lpw.tephra.test.HttpAspect;
import org.lpw.tephra.test.SchedulerAspect;
import org.lpw.tephra.util.Message;

import javax.inject.Inject;
import java.util.*;

/**
 * @author lpw
 */
public class ExecuteTest extends TestSupport {
    @Inject
    private Message message;
    @Inject
    private Cache cache;
    @Inject
    private SchedulerAspect schedulerAspect;
    @Inject
    private HttpAspect httpAspect;

    @SuppressWarnings({"unchecked"})
    @Test
    public void execute() {
        trustfulIp("/discovery/execute");

        schedulerAspect.pause();
        httpAspect.reset();
        mockHelper.reset();
        mockHelper.mock("/discovery/execute");
        JSONObject object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2311, object.getInt("code"));
        Assert.assertEquals(message.get(DiscoveryModel.NAME + ".key.not-exists"), object.getString("message"));

        mockHelper.reset();
        mockHelper.getHeader().addHeader("carousel-ds-key", "key");
        mockHelper.mock("/discovery/execute");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2311, object.getInt("code"));
        Assert.assertEquals(message.get(DiscoveryModel.NAME + ".key.not-exists"), object.getString("message"));

        for (int i = 0; i < 5; i++)
            create("key", i);
        mockHelper.reset();
        mockHelper.getHeader().addHeader("carousel-ds-key", "key");
        mockHelper.mock("/discovery/execute");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2311, object.getInt("code"));
        Assert.assertEquals(message.get(DiscoveryModel.NAME + ".key.not-exists"), object.getString("message"));

        List<String> urls = new ArrayList<>();
        List<Map<String, String>> headers = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            contents.add("content " + i);
        httpAspect.post(urls, headers, parameters, contents);
        cache.remove(DiscoveryModel.NAME + ".service.key:key");
        Set<String> set = new HashSet<>();
        for (int i = 0; i < contents.size(); i++) {
            mockHelper.reset();
            mockHelper.getHeader().addHeader("carousel-ds-key", "key");
            for (int j = 0; j < 5; j++) {
                mockHelper.getHeader().addHeader("header " + j, "header value " + j);
                mockHelper.getRequest().addParameter("parameter " + j, "parameter value " + j);
            }
            mockHelper.mock("/discovery/execute");
            object = mockHelper.getResponse().asJson();
            Assert.assertEquals(0, object.getInt("code"));
            Assert.assertEquals("content " + i, object.getString("data"));
            Assert.assertEquals(i + 1, urls.size());
            set.add(urls.get(i));
            Assert.assertEquals(i + 1, headers.size());
            Map<String, String> map = headers.get(i);
            Assert.assertEquals(6, map.size());
            Assert.assertEquals("key", map.get("carousel-ds-key"));
            for (int j = 0; j < 5; j++)
                Assert.assertEquals("header value " + j, map.get("header " + j));
            Assert.assertEquals(i + 1, parameters.size());
            map = (Map<String, String>) parameters.get(i);
            Assert.assertEquals(5, map.size());
            for (int j = 0; j < 5; j++)
                Assert.assertEquals("parameter value " + j, map.get("parameter " + j));
        }
        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.contains("service 0"));
        Assert.assertTrue(set.contains("service 2"));
        Assert.assertTrue(set.contains("service 4"));

        schedulerAspect.press();
    }
}
