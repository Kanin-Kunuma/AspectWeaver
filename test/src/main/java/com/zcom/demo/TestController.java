package com.zcom.demo;

import com.zcom.demo.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @Auth("admin")
    public String test() {
        System.out.println("业务方法执行");
        return "ok";
    }
}