package com.nowcoder.shiro.service.impl;

import com.nowcoder.model.shiro.UserInfo;
import com.nowcoder.shiro.dao.UserInfoDao;
import com.nowcoder.shiro.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }
}
