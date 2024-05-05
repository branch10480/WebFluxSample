package com.example.samplewebfluxapp.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class MyConf {
    @Autowired
    private HelloController helloController;

    // Beanとして登録できるRouterFunctionは１つだけ
    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(GET("/f/hello"), HelloController::hello)
            // 2つ目以降はandRouteで追加
            .andRoute(GET("/f/hello2"), HelloController::hello2)
            .andRoute(GET("/fetchData"), helloController::fetchData);
    }
}
