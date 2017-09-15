package com.cmos.smart4j.web.controller;

import com.cmos.smart4j.framework.annotation.Action;
import com.cmos.smart4j.framework.annotation.Controller;
import com.cmos.smart4j.framework.annotation.Inject;
import com.cmos.smart4j.framework.bean.Data;
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

        helloService.hello();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("CurrentTime", String.valueOf(new Date()));
        result.put("KKKK", "AAAAAAA");
        return new View("index.jsp", result).addModel("result", "ASASASASSS");
    }

    @Action("get:/getData")
    public Data getData() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("CurrentTime", String.valueOf(new Date()));
        result.put("KKKK", "AAAAAAA");
        return new Data(result);
    }


}
