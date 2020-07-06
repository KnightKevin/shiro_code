package com.simon.shiro.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

    Log log = LogFactory.getLog(Login.class);

    /**
     * 登录逻辑
     * */
    @GetMapping("/doLogin")
    public String doLogin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){

        log.info("username="+username);
        log.info("password="+password);


        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return "success";
    }

    /**
     * 登录错误
     * */
    @GetMapping("/error")
    public String loginError() {
        return "error!";
    }
}
