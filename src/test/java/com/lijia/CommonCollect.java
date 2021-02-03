package com.lijia;

import com.lijia.pojo.Dish;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @author lijia
 */
public class CommonCollect {

    /**
     * 获取菜单名list集合
     */
    public void toList() {
        final List<String> nameList = Dish.menu.stream().map(Dish::getName).collect(Collectors.toList());
        nameList.forEach(System.out::println);
    }

    /**
     * 获取菜单名set集合
     */
//    @Test
    public void toSet() {
        final Set<String> nameSet = Dish.menu.stream().map(Dish::getName).collect(Collectors.toSet());
        nameSet.forEach(System.out::println);
    }


    /**
     * 获取每种类型卡路里最大的菜
     */
//    @Test
    public void toMap() {
        final Map<Dish.Type, Dish> typeDishMap = Dish.menu.stream().collect(
                Collectors.toMap(Dish::getType, Function.identity(), (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        typeDishMap.forEach((key, value) -> {
            System.out.printf("%s->%s", key, value);
        });
    }

    /**
     * 拼接菜单名
     */
//    @Test
    public void joining() {
        // 无分隔符
        final String nameSet1 = Dish.menu.stream().map(Dish::getName).collect(Collectors.joining());
        System.out.println(nameSet1);

        // 以逗号分隔
        final String nameSet2 = Dish.menu.stream().map(Dish::getName).collect(Collectors.joining(","));
        System.out.println(nameSet2);

        // 以逗号分隔，前缀为`{`，后缀为`}`
        final String nameSet3 = Dish.menu.stream().map(Dish::getName).collect(Collectors.joining(",", "{", "}"));
        System.out.println(nameSet3);
    }

    /**
     * 统计
     */

    public void statistics() {
        // 计算素菜的个数
        final Long count = Dish.menu.stream().filter(Dish::isVegetarian).collect(Collectors.counting());
        System.out.println(count);
        // 获取素菜中卡路里最大的菜
        final Optional<Dish> maxDish = Dish.menu.stream().filter(Dish::isVegetarian).collect(Collectors.maxBy(comparing(Dish::getCalories)));
        System.out.println(maxDish);
        // 获取素菜中卡路里最小的菜
        final Optional<Dish> minDish = Dish.menu.stream().filter(Dish::isVegetarian).collect(Collectors.minBy(comparing(Dish::getCalories)));
        System.out.println(minDish);
        // 获取素菜的卡路里总和
        final Integer sumCalories = Dish.menu.stream().filter(Dish::isVegetarian).collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(sumCalories);
        //计算素菜卡路里的平均值
        final Double averageCalories = Dish.menu.stream().filter(Dish::isVegetarian).collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println(averageCalories);

        // 获取素菜列表中卡路里的统计值（总数、最大卡路里、最小卡路里、卡路里总和、卡路里的平均值）
        final IntSummaryStatistics intSummaryStatistics = Dish.menu.stream().filter(Dish::isVegetarian).collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(intSummaryStatistics.getCount());
        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getMin());
        System.out.println(intSummaryStatistics.getSum());
        System.out.println(intSummaryStatistics.getAverage());

    }

    @Benchmark
    public void benchmarkMapToInt() {
        final Integer sumCalories = Dish.menu.stream().filter(Dish::isVegetarian).mapToInt(Dish::getCalories).sum();
        System.out.println(sumCalories);

    }

    @Benchmark
    public void benchmarkSummingInt() {
        final Integer sumCalories = Dish.menu.stream().filter(Dish::isVegetarian).collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(sumCalories);
    }


    @Test
    public void launchBenchmark() throws Exception {

        Options opt = new OptionsBuilder()
                // 指定要运行的基准。
                // 如果您希望每个测试仅运行一个基准，则可以更加具体。
                .include(this.getClass().getName())
                // 根据需要设置以下选项
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MICROSECONDS)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(2)
                .measurementTime(TimeValue.seconds(1))
                .measurementIterations(10)
                .threads(2)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                //.jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining")
                //.addProfiler(WinPerfAsmProfiler.class)
                .build();

        new Runner(opt).run();
    }

}
