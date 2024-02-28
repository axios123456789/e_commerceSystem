package com.example.e_commerce.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.e_commerce.model.entity.user.UserInfo;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

//服务网关下做登录拦截, 指定请求被拦截
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisTemplate redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //得到请求路径
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        //判断路径是否满足/api/**/auth/**
        if (antPathMatcher.match("/api/**/auth/**", path)){//登录校验
            UserInfo userInfo = this.getUserInfo(request);
            if (userInfo == null){
                ServerHttpResponse response = exchange.getResponse();
                out(response, ResultCodeEnum.LOGIN_AUTH);
            }
        }

        return chain.filter(exchange);
    }

    //登录校验
    private UserInfo getUserInfo(ServerHttpRequest request) {
        //从请求头获得token
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if (tokenList != null){
            token = tokenList.get(0);
        }

        //判断token是否为空
        if (!StringUtils.isEmpty(token)){
            //查询redis
            String userJson = (String) redisTemplate.opsForValue().get("userInfo" + token);
            if (StringUtils.isEmpty(userJson)){
                return null;
            }else {
                UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);
                return userInfo;
            }
        }

        return null;
    }

    //错误提示
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
