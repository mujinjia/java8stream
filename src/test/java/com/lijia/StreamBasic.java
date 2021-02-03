package com.lijia;

import com.lijia.pojo.Dish;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @author lijia
 */
public class StreamBasic {


    /**
     * 从小到大出输出卡路里小于400的菜单名  (1.8之前)
     */
    @Test
    @Benchmark
    public void getLowCaloricDishesNamesInJava7() {
        final List<Dish> dishes = Dish.menu;
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish d : dishes) {
            if (d.getCalories() < 400) {
                lowCaloricDishes.add(d);
            }
        }
        List<String> lowCaloricDishesName = new ArrayList<>();
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish d1, Dish d2) {
                return Integer.compare(d1.getCalories(), d2.getCalories());
            }
        });
        for (Dish d : lowCaloricDishes) {
            lowCaloricDishesName.add(d.getName());
        }
        lowCaloricDishesName.forEach(System.out::println);
    }

    /**
     * 从小到大出输出卡路里小于400的菜单名  (1.8)
     */
    @Test
    @Benchmark
    public void getLowCaloricDishesNamesInJava8() {
        final List<Dish> dishes = Dish.menu;
        final List<String> stringList = dishes.stream()
                .sorted(comparing(Dish::getCalories))
                .filter(d -> d.getCalories() < 400)
                .map(Dish::getName)
                .collect(Collectors.toList());
        stringList.forEach(System.out::println);
    }


    @Test
    public void
    launchBenchmark() throws Exception {

        Options opt = new OptionsBuilder()
                // 指定要运行的基准。
                // 如果您希望每个测试仅运行一个基准，则可以更加具体。
                .include(this.getClass().getName())
                // 根据需要设置以下选项
                .mode(Mode.AverageTime)// 吞吐量
                .timeUnit(TimeUnit.MICROSECONDS)// 结果所使用的时间单位
                .warmupIterations(4)
                .measurementIterations(10)
                .threads(2)// 线程数
                .forks(1)//Fork进行的数目
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();
        new Runner(opt).run();
    }
}
