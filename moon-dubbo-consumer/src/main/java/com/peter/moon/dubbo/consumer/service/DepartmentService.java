package com.peter.moon.dubbo.consumer.service;

import org.springframework.context.annotation.Bean;

public class DepartmentService {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
