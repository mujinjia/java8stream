package com.lijia;

import com.lijia.pojo.Dish;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @author lijia
 */
public class ExecutedProcess {


    /**
     * 没有终端操作，中间操作不会执行
     */
    @Test
    public void notExecuted() {
        List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
        names.stream()
                .peek(System.out::println)
                .filter(name -> name.length() > 4)
                .peek(System.out::println)
                .map(String::length)
                .peek(System.out::println);
    }

    @Test
    public void test1() {
        Dish.menu.stream()
                .peek(dish -> {
                    System.out.println("-----------------");
                    System.out.printf("filter 执行之前,{%s} vegetarian:  %s\n", dish.getName(), dish.isVegetarian());
                })
                .filter(Dish::isVegetarian)
                .peek(dish -> System.out.printf("map 执行之前, {%s}\n", dish.getName()))
                .map(Dish::getName)
                .peek(name -> System.out.printf("forEach 执行之前,{%s}\n", name))
                .forEach(System.out::println);

    }

    @Test
    public void test2() {
        Dish.menu.stream()
                .peek(dish -> {
                    System.out.println("-----------------");
                    System.out.printf("filter 执行之前,{%s} vegetarian:  %s\n", dish.getName(), dish.isVegetarian());
                })
                .filter(Dish::isVegetarian)
                .peek(dish -> System.out.printf("map 执行之前, {%s}\n", dish.getName()))
                .map(Dish::getName)
                .peek(name -> System.out.printf("limit 执行之前,{%s}\n", name))
                .limit(3)
                .peek(name -> System.out.printf("forEach 执行之前,{%s}\n", name))
                .forEach(System.out::println);

    }

    @Test
    public void test3() {
        Dish.menu.stream()
                .peek(dish -> {
                    System.out.println("-----------------");
                    System.out.printf("filter 执行之前,{%s} vegetarian:  %s\n", dish.getName(), dish.isVegetarian());
                })
                .filter(Dish::isVegetarian)
                .peek(dish -> System.out.printf("map 执行之前, {%s}\n", dish.getName()))
                .map(Dish::getName)
                .peek(name -> System.out.printf("String::length 执行之前, {%s}\n", name))
                .map(String::length)
                .peek(len -> System.out.printf("distinct 执行之前,{%s}\n", len))
                .distinct()
                .peek(len -> System.out.printf("forEach 执行之前,{%s}\n", len))
                .forEach(System.out::println);

    }

    @Test
    public void test4() {
        Dish.menu.stream()
                .peek(dish -> {
                    System.out.println("-----------------");
                    System.out.printf("filter 执行之前,{%s} vegetarian:  %s\n", dish.getName(), dish.isVegetarian());
                })
                .filter(Dish::isVegetarian)
                .peek(dish -> System.out.printf("sorted 执行之前, {%s}  calories: %s\n", dish.getName(), dish.getCalories()))
                .sorted(comparing(Dish::getCalories))
                .peek(dish -> {
                    System.out.println("-----------------");
                    System.out.printf("forEach 执行之前,{%s}  calories: %s\n", dish.getName(), dish.getCalories());
                })
                .forEach(System.out::println);

    }


    @Test
    public void test5() {
        final Map<Dish.Type, Map<Grouping.CaloricLevel, List<Dish>>> typeMapMap = Dish.menu.stream()
                .peek(dish -> {
                    System.out.println("-----------------");
                    System.out.printf("groupingBy 执行之前,{%s}\n", dish.getName());
                })
                .collect(
                        Collectors.groupingBy(dish -> {
                                    System.out.printf("按type分组中： {%s} type: %s\n", dish.getName(), dish.getType());
                                    return dish.getType();
                                },
                                Collectors.groupingBy((Dish dish) -> {
                                    System.out.printf("按calories范围分组中： {%s} calories: %s\n", dish.getName(), dish.getCalories());
                                    if (dish.getCalories() <= 400) return Grouping.CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700) return Grouping.CaloricLevel.NORMAL;
                                    else return Grouping.CaloricLevel.FAT;
                                })
                        )
                );
        System.out.println("===========================================");
        typeMapMap.forEach((key, value) -> {
            System.out.printf("%s->{\n", key);
            value.forEach((valueKey, valueValue) -> {
                System.out.printf("\t\t%s->%s\n", valueKey, valueValue);
            });
            System.out.print("}\n");

        });
    }

}
