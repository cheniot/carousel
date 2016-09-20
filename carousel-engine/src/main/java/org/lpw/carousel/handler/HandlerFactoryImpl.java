package org.lpw.carousel.handler;

import org.lpw.tephra.bean.BeanFactory;
import org.lpw.tephra.bean.ContextRefreshedListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lpw
 */
@Service("carousel.engine.handler.factory")
public class HandlerFactoryImpl implements HandlerFactory, ContextRefreshedListener {
    @Value("${carousel.engine.pool.size:512}")
    protected int size;
    protected Map<String, Handler> handlers;

    @Override
    public Handler get(String name) {
        return handlers.get(name);
    }

    @Override
    public Parameter newParameter(String name) {
        Handler handler = get(name);

        return handler == null ? null : handler.newParameter();
    }

    @Override
    public Set<String> names() {
        return new HashSet<>(handlers.keySet());
    }

    @Override
    public int getContextRefreshedSort() {
        return 13;
    }

    @Override
    public void onContextRefreshed() {
        if (handlers != null)
            return;

        handlers = new HashMap<>();
        BeanFactory.getBeans(Handler.class).forEach(handler -> handlers.put(handler.getName(), handler));
    }
}
