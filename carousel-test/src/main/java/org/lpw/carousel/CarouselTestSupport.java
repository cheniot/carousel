package org.lpw.carousel;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.lpw.tephra.test.MockHelper;
import org.lpw.tephra.test.TephraTestSupport;
import org.lpw.tephra.util.Context;
import org.lpw.tephra.util.Io;
import org.lpw.tephra.util.Thread;
import org.lpw.tephra.util.TimeUnit;

import javax.inject.Inject;

/**
 * @author lpw
 */
public class CarouselTestSupport extends TephraTestSupport {
    @Inject
    private Context context;
    @Inject
    private Io io;
    @Inject
    private Thread thread;
    @Inject
    protected MockHelper mockHelper;

    protected void trustfulIp(String uri) {
        String path = context.getAbsolutePath("/WEB-INF/trustful-ip");
        byte[] bytes = io.read(path);
        io.write(path, "".getBytes());
        thread.sleep(2, TimeUnit.Second);

        mockHelper.reset();
        mockHelper.mock(uri);
        JSONObject object = mockHelper.getResponse().asJson();
        Assert.assertEquals(9996, object.getIntValue("code"));

        io.write(path, bytes);
        thread.sleep(2, TimeUnit.Second);
    }
}
