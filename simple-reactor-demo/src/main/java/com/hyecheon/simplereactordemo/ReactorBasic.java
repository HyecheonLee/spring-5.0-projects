package com.hyecheon.simplereactordemo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class ReactorBasic {
    private static List<String> carModels = Arrays.asList("Era", "Magna", "Sportz", "Astha", "Astha(O)");

    public static void main(String[] args) {
        final Flux<String> fewWord = Flux.just("Hello", "World");
        final Flux<String> manyWords = Flux.fromIterable(carModels);
        final Mono<String> singleWord = Mono.just("Single value");
        fewWord.subscribe(s -> System.out.println(s));
        System.out.println("-----------------------");
        manyWords.subscribe(System.out::println);
        System.out.println("-----------------------");
        singleWord.subscribe(System.out::println);
    }
}
