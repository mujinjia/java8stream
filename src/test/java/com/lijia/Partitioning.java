package com.lijia;

import com.lijia.pojo.Dish;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;

public class Partitioning {

    /**
     * 安荤素分区
     */
    @Test
    public void partitionByVegetarian() {
        final Map<Boolean, List<Dish>> booleanListMap = Dish.menu.stream().collect(partitioningBy(Dish::isVegetarian));
        booleanListMap.forEach((key, value) -> {
            System.out.printf("%s->%s\n", key, value);
        });
    }

    /**
     * 先安荤素分区，再按类型分组
     */
    @Test
    public void vegetarianDishesByType() {
        final Map<Boolean, Map<Dish.Type, List<Dish>>> booleanMapMap = Dish.menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        booleanMapMap.forEach((key, value) -> {
            System.out.printf("%s->{\n", key);
            value.forEach((valueKey, valueValue) -> {
                System.out.printf("\t\t%s->%s\n", valueKey, valueValue);
            });
            System.out.print("}\n");

        });
    }
}

