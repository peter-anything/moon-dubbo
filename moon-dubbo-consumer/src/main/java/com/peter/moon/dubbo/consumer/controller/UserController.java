package com.peter.moon.dubbo.consumer.controller;

import com.peter.moon.dubbo.consumer.service.Employee;
import com.peter.moon.dubbo.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Reference(version="${service.version}",check = false)
    UserService userService;

    @Autowired
    Employee employee;

    @RequestMapping(value="/login/{username}", method= RequestMethod.GET)
    public String login(@PathVariable String username) {
        System.out.println(employee.getDepartment());
        System.out.println(employee.getDepartment().getName());
        return userService.sayHello(username);
    }
}
