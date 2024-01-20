package com.example.e_commerce.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

//读取配置文件
@Data
@ConfigurationProperties(prefix = "dsxm.auth")
public class UserProperties {
    private List<String> noAuthUrls;
}
