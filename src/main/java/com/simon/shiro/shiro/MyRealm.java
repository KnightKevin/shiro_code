package com.simon.shiro.shiro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;


public class MyRealm extends AuthorizingRealm {

    Log log = LogFactory.getLog(MyRealm.class);

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();

        log.info(username);
        String salt      = "abc";

        // 查出该用户的真正密码
        String realPassword = "b106dc6352e5ec1f8aafd8c406d34d92";

        if ("zhang".equals(username)) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("zhang", realPassword,"asdfasd");
            info.setCredentialsSalt(ByteSource.Util.bytes(salt));
            return info;
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
