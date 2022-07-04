package com.peter.moon.dubbo.provider.service.impl;

import com.peter.moon.dubbo.provider.service.DiscoveryService;
import org.apache.dubbo.common.URL;

public class ZookeeperDiscoveryServiceImpl implements DiscoveryService {
    private DiscoveryService discoveryService;

    public void setRobot(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    @Override
    public void discovery(URL url) {
        this.discoveryService.discovery(url);
        System.out.println("I'm ZookeeperDiscoveryServiceImpl");
    }
}
