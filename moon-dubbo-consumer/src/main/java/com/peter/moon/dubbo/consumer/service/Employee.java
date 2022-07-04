package com.peter.moon.dubbo.consumer.service;

public class Employee {
    private Department department;
    private String name;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
        System.out.println(this.department);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
