package com.peter.moon.dubbo.service;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * 代码创建: yellowcong <br/>
 * 创建日期: 2019年3月30日 <br/>
 * 功能描述:
 */
@SPI
public interface UserService {

    String sayHello(String username);
}