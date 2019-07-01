package com.bihuniak.piotr.reactiveBePatient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @GetMapping
    public Mono<TestEntity> test(){
        TestEntity testEntity = new TestEntity();
        testEntity.setName("ala");
        return testRepository.save(testEntity);
    }

    @GetMapping("a")
    public Flux<TestEntity> test2(){
        return testRepository.findAll();
    }
}
