package com.example.samplewebfluxapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class HelloController {
    
    private final WebClient webClient;
    
    public HelloController(WebClient.Builder builder) {
        super();
        this.webClient = builder.baseUrl("jsonplaceholder.typicode.com").build();
    }

    @GetMapping("/")
    public String hello() {
        return "Hello, Spring WebFlux!!";
    }
    
    @GetMapping("/flux")
    public Flux<String> fluxSample() {
        return Flux.just("Hello, ", "!!");
    }
    
    @GetMapping("/web/{id}")
    public Mono<String> web(@PathVariable int id) {
        return this.webClient.get()
            .uri("/posts/" + id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);
    }
    
    @GetMapping("/web")
    public Mono<String> web2() {
        return this.webClient.get()
            .uri("/posts")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);
    }

    public static Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse
            .ok()
            .body(
                Mono.just("Hello Functional routing world!"), String.class
            );
    }
    
    public static Mono<ServerResponse> hello2(ServerRequest request) {
        return ServerResponse
            .ok()
            .body(
                Mono.just("Hello Functional routing world2!"), String.class
            );
    }
    
}