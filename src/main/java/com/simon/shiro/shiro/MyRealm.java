package com.simon.shiro.shiro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;


public class MyRealm extends AuthorizingRealm {

    Log log = LogFactory.getLog(MyRealm.class);


    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        String passsword = token.getCredentials().toString();

        log.info(username);
        log.info(passsword);


        if ("zhang".equals(username)) {
            return new SimpleAuthenticationInfo("zhang", "123", "asdfasd");
        } else {
            throw new IncorrectCredentialsException("用户名或密码错误！");

        }
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRole("role1");

        info.addStringPermission("user:create");
        info.addStringPermission("user:update");


        return info;
    }
}
