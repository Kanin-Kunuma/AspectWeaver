package com.zcom.demo;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Auth("test")
    public void testMethod() {
        System.out.println("method");
    }
}
