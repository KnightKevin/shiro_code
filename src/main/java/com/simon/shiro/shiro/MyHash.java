package com.simon.shiro.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MyHash {
    private String algorithmName="md5";
    private String password;
    private String salt;
    private Integer hashIterations = 2;

    public MyHash(String password, String salt) {
        this.password = password;
        this.salt = salt;
    }

    public SimpleHash hash() {
        return new SimpleHash(algorithmName, password, salt, hashIterations);
    }
}