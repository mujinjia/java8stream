package com.lijia;

import com.lijia.pojo.Dish;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Grouping {


    /**
     * 按类型分组
     */
    @Test
    public void groupDishesByType() {
        final Map<Dish.Type, List<Dish>> typeListMap = Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType));
        typeListMap.forEach((key, value) -> {
            System.out.printf("%s->%s\n", key, value);
        });
    }

    /**
     * 输出每种类型菜的数量
     */
    @Test
    public void countDishesInGroups() {
        final Map<Dish.Type, Long> typeLongMap = Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        typeLongMap.forEach((key, value) -> {
            System.out.printf("%s->%s\n", key, value);
        });
    }

    /**
     * 每种类型卡路里总和
     */
    @Test
    public void sumCaloriesByType() {
        final Map<Dish.Type, Integer> typeIntegerMap = Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.summingInt(Dish::getCalories)));
        typeIntegerMap.forEach((key, value) -> {
            System.out.printf("%s->%s\n", key, value);
        });
    }

    /**
     * 将菜名按类型分组
     */
    @Test
    public void groupDishNamesByType() {
        final Map<Dish.Type, List<String>> typeListMap = Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));
        typeListMap.forEach((key, value) -> {
            System.out.printf("%s->%s\n", key, value);
        });
    }

    /**
     * 输出每种类型热量最大的菜
     */
    @Test
    public void mostCaloricDishesByType() {
        final Map<Dish.Type, Optional<Dish>> typeOptionalMap = Dish.menu.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
        typeOptionalMap.forEach((key, value) -> {
            System.out.printf("%s->%s\n", key, value.orElse(null));
        });
    }


    /**
     * 先按类型分组、再按卡路里级别分组
     */
    @Test
    public void groupDishedByTypeAndCaloricLevel() {
        final Map<Dish.Type, Map<CaloricLevel, List<Dish>>> typeMapMap = Dish.menu.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy((Dish dish) -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })
                )
        );
        typeMapMap.forEach((key, value) -> {
            System.out.printf("%s->{\n", key);
            value.forEach((valueKey, valueValue) -> {
                System.out.printf("\t\t%s->%s\n", valueKey, valueValue);
            });
            System.out.print("}\n");

        });
    }


    enum CaloricLevel {DIET, NORMAL, FAT}
}
