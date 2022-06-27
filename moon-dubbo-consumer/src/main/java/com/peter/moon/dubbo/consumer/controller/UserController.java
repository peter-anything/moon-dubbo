package com.peter.moon.dubbo.consumer.controller;

import com.peter.moon.dubbo.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Reference(version="${service.version}",check = false)
    UserService userService;

    @RequestMapping(value="/login/{username}", method= RequestMethod.GET)
    public String login(@PathVariable String username) {
        return userService.sayHello(username);
    }
}
