package com.simon.shiro.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String defaultExceptionHandler(HttpServletRequest request, Exception e) {
        System.out.println(e.getClass().toString());
        return e.getMessage();
    }

    /**
     * 没有相应的权限
     * */
    @ExceptionHandler(UnauthorizedException.class)
    public String HasNoRolesExceptionHandler(HttpServletRequest request, Exception e) {
        return "该角色没有权限";
    }
}
