package com.dethan.java.app.grocery.reactor;

import com.dethan.java.common.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class FluxReactorTest {

    private static final String FALLBACK = "fallback";

    private static final Predicate<User> PREDICATE_FALLBACK = u -> Objects.equals(u.getUserName(), FALLBACK);


    /**
     * 依次执行step1、step2、step3
     * step都执行成功并返回最终结果
     */
    @Test
    void testFluxReactor1() {
        String username = "step1step2step3";

        StepVerifier.create(exec2(username))
                .expectNextMatches(user -> {
                    Assertions.assertNotNull(user);
                    Assertions.assertEquals(username, user.getUserName());
                    return true;
                })
                .verifyComplete();
    }

    /**
     * 依次执行step1、step2、step3
     * step1执行成功，其它step失败，最终返回fallback结果
     */
    @Test
    void testFluxReactor2() {
        String username = "step1";
        StepVerifier.create(exec2(username))
                .expectNextMatches(user -> {
                    Assertions.assertNotNull(user);
                    Assertions.assertEquals(FALLBACK, user.getUserName());
                    return true;
                })
                .verifyComplete();
    }

    /**
     * 多个输入，依次执行step1、step2、step3
     * step全部fallback
     */
    @Test
    void testFluxReactor3() {
        List<String> inputs = List.of("invalid1", "invalid2", "invalid3", "invalid4");
        StepVerifier.create(Flux.fromIterable(inputs)
                        .flatMap(i -> this.exec1(i).switchIfEmpty(Mono.defer(this::fallback)))
                        .doOnNext(u -> System.out.println("finally user: " + u))
                ).recordWith(ArrayList::new)
                .expectNextCount(4)
                .consumeRecordedWith(users ->
                        Assertions.assertTrue(users.stream().allMatch(PREDICATE_FALLBACK)))
                .verifyComplete();
    }

    /**
     * 多个输入，依次执行step1、step2、step3
     * step全部fallback，但是fallback只创建一次
     */
    @Test
    void testFluxReactor4() {
        Mono<User> cachedFallback = Mono.defer(this::fallback).cache();

        List<String> inputs = List.of("invalid1", "invalid2", "invalid3", "invalid4");
        StepVerifier.create(Flux.fromIterable(inputs)
                        .flatMap(i -> this.exec1(i).switchIfEmpty(cachedFallback))
                        .doOnNext(u -> System.out.println("finally user: " + u))
                ).recordWith(ArrayList::new)
                .expectNextCount(4)
                .consumeRecordedWith(users ->
                        Assertions.assertTrue(users.stream().allMatch(PREDICATE_FALLBACK)))
                .verifyComplete();
    }


    private Mono<User> exec1(String username) {
        return create(username)
                .flatMap(this::step1)
                .doOnNext(u -> System.out.println("step1 user: " + u.getUserName()))
                .flatMap(this::step2)
                .doOnNext(u -> System.out.println("step2 user: " + u.getUserName()))
                .flatMap(this::step3)
                .doOnNext(u -> System.out.println("step3 user: " + u.getUserName()));
    }

    private Mono<User> exec2(String username) {
        return exec1(username)
                .switchIfEmpty(Mono.defer(this::fallback))
                .doOnNext(u -> System.out.println("finally user: " + u));
    }

    private Mono<User> create(String username) {
        User user = new User();
        user.setUserName(username);
        System.out.println("created user: " + user.getUserName());
        return Mono.just(user);
    }

    private Mono<User> step1(User user) {
        if (user.getUserName().startsWith("step1")) {
            return Mono.just(user);
        }
        return Mono.empty();
    }

    private Mono<User> step2(User user) {
        if (user.getUserName().contains("step2")) {
            return Mono.just(user);
        }
        return Mono.empty();
    }

    private Mono<User> step3(User user) {
        if (user.getUserName().contains("step3")) {
            return Mono.just(user);
        }
        return Mono.empty();
    }

    private Mono<User> fallback() {
        return this.create(FALLBACK);
    }
}
