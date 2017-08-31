package com.cmos.smart4j.web.controller;

import com.cmos.smart4j.framework.annotation.Action;
import com.cmos.smart4j.framework.annotation.Controller;
import com.cmos.smart4j.framework.annotation.Inject;
import com.cmos.smart4j.framework.bean.Param;
import com.cmos.smart4j.framework.bean.View;
import com.cmos.smart4j.web.service.HelloService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    @Inject
    private HelloService helloService;

    @Action("get:/hello")
    public View hello(Param param) {
        long id = param.getLong("id");
        System.out.println("id===" + id);
        Map<String, String> result = new HashMap<String, String>();
        result.put("CurrentTime", String.valueOf(new Date()));
        result.put("KKKK", "AAAAAAA");
        return new View("index.jsp").addModel("result", "kkkkkkkkkkk");
    }


}
