package org.lpw.carousel.discovery;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.lpw.tephra.cache.Cache;
import org.lpw.tephra.ctrl.validate.Validators;
import org.lpw.tephra.test.SchedulerAspect;
import org.lpw.tephra.util.Generator;
import org.lpw.tephra.util.Message;

import javax.inject.Inject;
import java.sql.Timestamp;

/**
 * @author lpw
 */
public class RegisterTest extends TestSupport {
    @Inject
    private Message message;
    @Inject
    private Generator generator;
    @Inject
    private Cache cache;
    @Inject
    private SchedulerAspect schedulerAspect;

    @Test
    public void reigster() {
        trustfulIp("/discovery/register");

        schedulerAspect.pause();
        mockHelper.reset();
        mockHelper.mock("/discovery/register");
        JSONObject object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2301, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "empty", message.get(DiscoveryModel.NAME + ".key")), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", generator.random(101));
        mockHelper.mock("/discovery/register");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2302, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(DiscoveryModel.NAME + ".key"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", "service key");
        mockHelper.mock("/discovery/register");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2303, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "empty", message.get(DiscoveryModel.NAME + ".service")), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", "service key");
        mockHelper.getRequest().addParameter("service", generator.random(101));
        mockHelper.mock("/discovery/register");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2304, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(DiscoveryModel.NAME + ".service"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", "service key");
        mockHelper.getRequest().addParameter("service", "service url");
        mockHelper.mock("/discovery/register");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2305, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "empty", message.get(DiscoveryModel.NAME + ".validate")), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", "service key");
        mockHelper.getRequest().addParameter("service", "service url");
        mockHelper.getRequest().addParameter("validate", generator.random(101));
        mockHelper.mock("/discovery/register");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2306, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(DiscoveryModel.NAME + ".validate"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", "service key");
        mockHelper.getRequest().addParameter("service", "service url");
        mockHelper.getRequest().addParameter("validate", "validate url");
        mockHelper.getRequest().addParameter("success", generator.random(101));
        mockHelper.mock("/discovery/register");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(2307, object.getIntValue("code"));
        Assert.assertEquals(message.get(Validators.PREFIX + "over-max-length", message.get(DiscoveryModel.NAME + ".success"), 100), object.getString("message"));

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", "service key");
        mockHelper.getRequest().addParameter("service", "service url");
        mockHelper.getRequest().addParameter("validate", "validate url");
        mockHelper.getRequest().addParameter("success", "success pattern");
        mockHelper.mock("/discovery/register");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        Assert.assertEquals("", object.getString("data"));
        DiscoveryModel discovery = findByKeyService("service key", "service url");
        Assert.assertEquals("service key", discovery.getKey());
        Assert.assertEquals("service url", discovery.getService());
        Assert.assertEquals("validate url", discovery.getValidate());
        Assert.assertEquals("success pattern", discovery.getSuccess());
        Assert.assertEquals(0, discovery.getState());
        Assert.assertTrue(System.currentTimeMillis() - discovery.getRegister().getTime() < 1000L);

        discovery.setValidate("validate 1");
        discovery.setSuccess("success 1");
        discovery.setState(1);
        discovery.setRegister(new Timestamp(System.currentTimeMillis() - 10000));
        liteOrm.save(discovery);
        close();
        cache.put(DiscoveryModel.NAME + ".service.key:service key", discovery, false);

        mockHelper.reset();
        mockHelper.getRequest().addParameter("key", "service key");
        mockHelper.getRequest().addParameter("service", "service url");
        mockHelper.getRequest().addParameter("validate", "validate 2");
        mockHelper.getRequest().addParameter("state", "2");
        mockHelper.getRequest().addParameter("register", "2017-01-02 03:04:05");
        mockHelper.mock("/discovery/register");
        object = mockHelper.getResponse().asJson();
        Assert.assertEquals(0, object.getIntValue("code"));
        Assert.assertEquals("", object.getString("data"));
        discovery = findByKeyService("service key", "service url");
        Assert.assertEquals("service key", discovery.getKey());
        Assert.assertEquals("service url", discovery.getService());
        Assert.assertEquals("validate 2", discovery.getValidate());
        Assert.assertEquals("", discovery.getSuccess());
        Assert.assertEquals(0, discovery.getState());
        Assert.assertTrue(System.currentTimeMillis() - discovery.getRegister().getTime() < 1000L);
        Assert.assertNull(cache.get(DiscoveryModel.NAME + ".service.key:service key"));
        schedulerAspect.press();
    }
}
