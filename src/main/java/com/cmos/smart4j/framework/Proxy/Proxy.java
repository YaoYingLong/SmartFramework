package com.cmos.smart4j.framework.Proxy;

public interface Proxy {

    /**
     * execute proxy chain
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
