package com.cmos.smart4j.framework.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params);
            } else {
                return proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
        }

        return null;
    }

    private void after(Class<?> cls, Method method, Object[] params) {
    }

    private void error(Class<?> cls, Method method, Object[] params, Exception e) {
    }

    private void before(Class<?> cls, Method method, Object[] params) {
    }

    private boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    private void begin() {
    }
}
