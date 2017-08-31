package com.cmos.smart4j.web.service;

import com.cmos.smart4j.framework.annotation.Service;

@Service
public class HelloService {

    public String hello() {
        return "test hello";
    }

}
