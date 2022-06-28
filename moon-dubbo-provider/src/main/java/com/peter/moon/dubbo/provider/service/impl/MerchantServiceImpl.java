package com.peter.moon.dubbo.provider.service.impl;

import com.peter.moon.dubbo.service.MerchantService;
import com.peter.moon.dubbo.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version="${service.version}", interfaceClass= MerchantService.class)
public class MerchantServiceImpl implements MerchantService {
    @Override
    public String buy(String productName) {
        return "This product is very good for you, " + productName;
    }
}
