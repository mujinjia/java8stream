package com.lijia;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author lijia
 */
public class StreamException {

    @Test
    public void test1() {
        List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
        Stream<String> s = names.stream();
        s.forEach(System.out::println);
        // 下列代码将导致IllegalStateException
        // 因为流只能被消耗一次
        s.forEach(System.out::println);
    }

    @Test
    public void test2() {
        List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
        Stream<String> s = names.stream();
        s.filter(name -> name.length() > 4);
        s.map(String::length);
    }

    @Test
    public void test3() {
        List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
        Stream<String> s = names.stream();
        s.map(String::length);
        s.forEach(System.out::println);
    }
}