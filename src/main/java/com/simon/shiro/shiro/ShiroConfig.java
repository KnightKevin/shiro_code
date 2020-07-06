package com.simon.shiro.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private static final String SHIRO_INTERCEPTOR_ANON="anon";

    private static final String SHIRO_INTERCEPTOR_AUTHC="authc";

    /**
     * 创建凭据的匹配器CredentialMatcher，注意要与加密方法的配置一致，否则无法验证成功，
     * 该类型的匹配器支持自定义盐
     * */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2);

        return matcher;
    }

    /**
     * 创建Realm的bean
     * */
    @Bean
    public MyRealm myRealm() {
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(credentialsMatcher());

        return realm;
    }

    /**
     * 创建SecurityManager的Bean，用于加载相应的Bean
     * */
    @Bean
    public DefaultSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm());

        // 可以在这设置会话管理

        return manager;
    }

    /**
     * 设置filter，这是将过滤器代理移交给Shiro，也就是安全的入口，需要配置哪些页面须要验证
     * */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/login");

        Map<String, String> map = new LinkedHashMap<>();
        map.put("/doLogin", SHIRO_INTERCEPTOR_ANON);
        map.put("/**", SHIRO_INTERCEPTOR_AUTHC);
        bean.setFilterChainDefinitionMap(map);

        return bean;
    }

    /**
     * 开启shiro的注解，需要借助Spring AOP扫描来使用shiro注解的类,必须要配置以下两个类，
     * (DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)
     * */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);

        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);

        return advisor;
    }
}
