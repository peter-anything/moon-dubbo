package com.peter.moon.dubbo.consumer;

import com.peter.moon.dubbo.consumer.config.EmployeeRegistrar;
import com.peter.moon.dubbo.consumer.service.Department;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@EnableDubbo(scanBasePackages = "com.peter.moon.dubbo.consumer.controller")
@SpringBootApplication
@Import(EmployeeRegistrar.class)
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    Department getDepartment() {
        Department department = new Department();
        department.setName("software department");
        System.out.println(department.getName());
        return department;
    }
}
