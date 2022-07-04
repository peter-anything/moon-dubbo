package com.peter.moon.dubbo.provider.service.impl;

import com.peter.moon.dubbo.provider.service.DiscoveryService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.peter.moon.dubbo.service.UserService;

@Component
@Service(version="${service.version}",interfaceClass=UserService.class)
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String sayHello(String username) {
        logger.info(username);
        ExtensionLoader<DiscoveryService> extensionLoader =
                ExtensionLoader.getExtensionLoader(DiscoveryService.class);
        DiscoveryService discoveryService = extensionLoader.getExtension("zookeeper");
        URL url = URL.valueOf("test://localhost/test?loadbalance=redis");
        discoveryService.discovery(url);
        return "hello peter ,rpc -->" +username;
    }
}
