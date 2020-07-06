package com.simon.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class index {
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @RequiresRoles("role5")
    @GetMapping("/page1")
    public String page1() {
        boolean res =SecurityUtils.getSubject().hasRole("role5");
        System.out.println(res);
        return "page1";
    }
}
