package com.peter.moon.dubbo.provider.service.impl;

import com.peter.moon.dubbo.provider.service.DiscoveryService;
import org.apache.dubbo.common.URL;

public class RedisDiscoveryServiceImpl implements DiscoveryService {
    @Override
    public void discovery(URL url) {
        System.out.println("I'm RedisDiscoveryServiceImpl");
    }
}
