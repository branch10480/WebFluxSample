package com.example.samplewebfluxapp.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.samplewebfluxapp.model.Person;
import com.example.samplewebfluxapp.repositories.PersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class HelloController {
    
    private final WebClient webClient;
    
    /*
     * Beanが登録されるまでの流れ
     * ------------------------
     * 
     * 1. アプリケーション起動時に、@Repositoryをつけられたインタフェースを検索し、自動的にその実装インスタンスが生成され、
     * 更にそのインスタンスがBeanとして登録される
     * 
     * 2. コントローラーなどのクラスがロードされる際、@Autowiredアノテーションがつけられたフィールドがあると
     * 登録済みのBeanから同じクラスのものを検索し、自動的にそのフィールドにインスタンスを割り当てる
     */
    
    // Autowiredアノテーションを使ってPersonRepositoryをインジェクション(インタフェースであってもインスタンスが自動生成される)
    @Autowired
    private PersonRepository repository;
    
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
    
    // Method for functional routing
    public static Mono<ServerResponse> hello2(ServerRequest request) {
        return ServerResponse
            .ok()
            .body(
                Mono.just("Hello Functional routing world2!"), String.class
            );
    }
    
    public Mono<ServerResponse> fetchData(ServerRequest request) {
        // findAllメソッドは jpaRepository に用意されているメソッド
        Iterable<Person> people = repository.findAll();
        var map = new HashMap<String, Object>();
        map.put("title", "Flux/Function routing");
        map.put("msg", "This is sample for Flux/Function routing.");
        map.put("people", people);
        return ServerResponse
            .ok()
            .contentType(MediaType.TEXT_HTML)
            .render("flux", map);
    }
    
}