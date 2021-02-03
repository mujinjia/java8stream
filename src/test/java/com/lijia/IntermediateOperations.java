package com.lijia;


import com.lijia.pojo.Dish;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * @author lijia
 */
public class IntermediateOperations {


    /**
     * 输出所有素食菜单
     */
    @Test
    public void filter() {
        Dish.menu.stream()
                .filter(Dish::isVegetarian)
                .forEach(System.out::println);

    }

    /**
     * 输出所有菜单名
     */
    @Test
    public void map() {
        Dish.menu.stream()
                .map(Dish::getName)
                .forEach(System.out::println);
    }


    /**
     * 输出[1, 2, 1, 3, 3, 2, 4] 中所有偶数并去重
     */
    @Test
    public void distinct() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 输出卡路里大于300的前3个菜单
     */
    @Test
    public void limit() {
        Dish.menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .forEach(System.out::println);
    }


    /**
     * 输出卡路里大于300的菜单并跳过前2个
     */
    @Test
    public void skip() {
        Dish.menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .forEach(System.out::println);

    }


    /**
     * 按顺序输出卡路里小于400的菜单
     */
    @Test
    public void sorted() {
        Dish.menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .forEach(System.out::println);

    }


    /**
     * 输出所有菜单名
     */
    @Test
    public void mapToInt() {
//        Dish.menu.stream()
//                .mapToInt(Dish::getCalories)
//                .collect(Collectors.toList());
    }
}
