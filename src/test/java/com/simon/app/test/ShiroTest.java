package com.simon.app.test;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class ShiroTest {

    @Test
    public void test1() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            // 4. 登录
            subject.login(token);
        } catch (AuthenticationException e) {
            // 登录失败

            System.out.println("login error!! "+e.getMessage());

        }

        // 是否又role1角色
        Assert.assertEquals(true, subject.hasRole("role1"));

        // 是否有某个权限
        Assert.assertEquals(true, subject.isPermitted("user:create"));
        Assert.assertEquals(false, subject.isPermitted("user:delete"));

        // 是否登录
        Assert.assertEquals(true, subject.isAuthenticated());


        subject.logout();
    }
}
