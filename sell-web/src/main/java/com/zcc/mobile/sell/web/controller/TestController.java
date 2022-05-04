package com.zcc.mobile.sell.web.controller;

import com.zcc.mobile.sell.domain.dao.UserDao;
import com.zcc.mobile.sell.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class TestController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/test")
    public List<UserEntity> findUsers() {
        return userDao.findUsers();
    }

    @GetMapping("/is")
    public int insertUser() {
        return userDao.save(new UserEntity() {{
            setId(new Random().nextInt(100));
            setAge(26);
        }});
    }
}
