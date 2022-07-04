package com.peter.moon.dubbo.provider.service;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface DiscoveryService {
    @Adaptive("loadbalance")
    void discovery(URL url);
}
