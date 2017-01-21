package org.lpw.carousel.discovery;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.lpw.tephra.test.SchedulerAspect;

import javax.inject.Inject;

/**
 * @author lpw
 */
public class QueryTest extends TestSupport {
    @Inject
    private SchedulerAspect schedulerAspect;

    @Test
    public void query() {
        trustfulIp("/discovery/query");

        schedulerAspect.pause();
        for (int i = 0; i < 100; i++)
            create("key " + (i % 20), i);

        mockHelper.reset();
        mockHelper.getRequest().addParameter("pageSize", "2");
        mockHelper.getRequest().addParameter("pageNum", "1");
        mockHelper.mock("/discovery/query");
        JSONObject object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getInt("code"));
        JSONObject data = object.getJSONObject("data");
        Assert.assertEquals(100, data.getInt("count"));
        Assert.assertEquals(2, data.getInt("size"));
        Assert.assertEquals(1, data.getInt("number"));
        JSONArray list = data.getJSONArray("list");
        Assert.assertEquals(2, list.size());
        for (int i = 0; i < list.size(); i++) {
            JSONObject discovery = list.getJSONObject(i);
            Assert.assertEquals("key " + i, discovery.getString("key"));
            Assert.assertEquals("service " + i, discovery.getString("service"));
            Assert.assertEquals("validate " + i, discovery.getString("validate"));
            Assert.assertEquals("success " + i, discovery.getString("success"));
            Assert.assertEquals(i, discovery.getInt("state"));
        }

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", "ey 1");
        mockHelper.getRequest().addParameter("pageSize", "2");
        mockHelper.getRequest().addParameter("pageNum", "1");
        mockHelper.mock("/discovery/query");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getInt("code"));
        data = object.getJSONObject("data");
        Assert.assertEquals(55, data.getInt("count"));
        Assert.assertEquals(2, data.getInt("size"));
        Assert.assertEquals(1, data.getInt("number"));
        list = data.getJSONArray("list");
        Assert.assertEquals(2, list.size());
        int[] ns = new int[]{1, 10};
        for (int i = 0; i < ns.length; i++) {
            JSONObject discovery = list.getJSONObject(i);
            Assert.assertEquals("key " + ns[i], discovery.getString("key"));
            Assert.assertEquals("service " + ns[i], discovery.getString("service"));
            Assert.assertEquals("validate " + ns[i], discovery.getString("validate"));
            Assert.assertEquals("success " + ns[i], discovery.getString("success"));
            Assert.assertEquals(1 - i, discovery.getInt("state"));
        }

        mockHelper.reset();
        mockHelper.getRequest().addParameter("service", "ce 3");
        mockHelper.getRequest().addParameter("pageSize", "2");
        mockHelper.getRequest().addParameter("pageNum", "1");
        mockHelper.mock("/discovery/query");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getInt("code"));
        data = object.getJSONObject("data");
        Assert.assertEquals(11, data.getInt("count"));
        Assert.assertEquals(2, data.getInt("size"));
        Assert.assertEquals(1, data.getInt("number"));
        list = data.getJSONArray("list");
        Assert.assertEquals(2, list.size());
        ns = new int[]{3, 30};
        for (int i = 0; i < ns.length; i++) {
            JSONObject discovery = list.getJSONObject(i);
            Assert.assertEquals("key " + (ns[i] % 20), discovery.getString("key"));
            Assert.assertEquals("service " + ns[i], discovery.getString("service"));
            Assert.assertEquals("validate " + ns[i], discovery.getString("validate"));
            Assert.assertEquals("success " + ns[i], discovery.getString("success"));
            Assert.assertEquals(ns[i] % 2, discovery.getInt("state"));
        }

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", "ey 1");
        mockHelper.getRequest().addParameter("service", "ce 3");
        mockHelper.getRequest().addParameter("pageSize", "2");
        mockHelper.getRequest().addParameter("pageNum", "1");
        mockHelper.mock("/discovery/query");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getInt("code"));
        data = object.getJSONObject("data");
        Assert.assertEquals(10, data.getInt("count"));
        Assert.assertEquals(2, data.getInt("size"));
        Assert.assertEquals(1, data.getInt("number"));
        list = data.getJSONArray("list");
        Assert.assertEquals(2, list.size());
        ns = new int[]{30, 31};
        for (int i = 0; i < ns.length; i++) {
            JSONObject discovery = list.getJSONObject(i);
            Assert.assertEquals("key " + (ns[i] % 20), discovery.getString("key"));
            Assert.assertEquals("service " + ns[i], discovery.getString("service"));
            Assert.assertEquals("validate " + ns[i], discovery.getString("validate"));
            Assert.assertEquals("success " + ns[i], discovery.getString("success"));
            Assert.assertEquals(ns[i] % 2, discovery.getInt("state"));
        }

        schedulerAspect.press();
    }
}
