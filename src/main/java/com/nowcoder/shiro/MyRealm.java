package com.nowcoder.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    /**
     * 模拟数据库
     * @param principals
     * @return
     */
    Map<String,String> userMap = new HashMap<>(1);
    {
        userMap.put("root","d2f0e963198965722fd22e9ab414efbc");//加密后的密码
        super.setName("myRealm");
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = String.valueOf(principals.getPrimaryPrincipal());

        // 从数据库获取角色和权限数据
        Set<String> permissionSet = getPermessionGetByUserName(userName);
        Set<String> rolesSet = getRolesGetByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        simpleAuthorizationInfo.setRoles(rolesSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 1.从主体传过来的认证信息中，获得用户名
        String userName = String.valueOf(token.getPrincipal());

        // 2.通过用户名到数据库中获取凭证
        String passWord = getPassWordByUserName(userName);
        if(StringUtils.isBlank(passWord)){
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName,passWord,"myRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("mark"));//加盐
        return simpleAuthenticationInfo;
    }

    /**
     * 模拟从数据库中获取权限
     * @param
     * @return
     */
    private Set<String> getPermessionGetByUserName(String userName){
        Set<String> permission = new HashSet<>();
        permission.add("can update");
        permission.add("can delete");
        return permission;
    }

    /**
     * 模拟从数据库中获取角色
     * @param
     * @return
     */
    private Set<String> getRolesGetByUserName(String userName){
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    /**
     * 数据库获取凭证
     */
    private String getPassWordByUserName(String userName){
        return userMap.get(userName);
    }


    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("12345","mark");//md5加密，加盐
        System.out.printf(md5Hash.toString());
    }
}
