package com.nowcoder;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthencationTest {
private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void adduser(){
        simpleAccountRealm.addAccount("root","12345");
    }

    @Test
    public void testAuthencate(){
        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        //登录
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("root","12345");
        subject.login(usernamePasswordToken);

        //认证
        System.out.println("登录认证："+subject.isAuthenticated());

        //退出
        subject.logout();

        //认证
        System.out.println("退出认证："+subject.isAuthenticated());
    }
}
