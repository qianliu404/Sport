package com.ex.sport.controller;

import com.alibaba.fastjson.JSON;
import com.ex.sport.bean.User;
import com.ex.sport.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LoginController {
    @Autowired
    UserDao userDao;
    @CrossOrigin("http://localhost:8080")
    @RequestMapping("/login")
    public String userLogin(@RequestBody User user){
        String flag = "error";
        User us = userDao.getUserByMessage(user.getUsername(),user.getPassword());
        System.out.println("user:"+us);
        HashMap<String, Object> res = new HashMap<>();
        if(us!=null){
            flag = "ok";
        }
        res.put("flag",flag);
        res.put("user",user);
       String res_json = JSON.toJSONString(res);
        return res_json;
    }

}
