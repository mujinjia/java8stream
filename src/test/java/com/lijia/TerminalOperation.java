package com.lijia;

import com.lijia.pojo.Dish;
import org.junit.Test;

import java.util.Optional;

import static java.util.Comparator.comparing;

/**
 * @author lijia
 */
public class TerminalOperation {


    public static boolean isPerfectSquare(int n) {
        return Math.sqrt(n) % 1 == 0;
    }

    /**
     * 随机获取一个素菜
     */
    @Test
    public void findAny() {
        final Optional<Dish> dish = Dish.menu.stream().filter(Dish::isVegetarian).findAny();
        dish.ifPresent(d -> System.out.println(d.getName()));
    }

    /**
     * 获取第一个素菜
     */
    @Test
    public void findFirst() {
        final Optional<Dish> dish = Dish.menu.stream().filter(Dish::isVegetarian).findFirst();
        dish.ifPresent(d -> System.out.println(d.getName()));
    }

    /**
     * 判断是否存在卡路里小于500的菜
     */
    @Test
    public void anyMatch() {
        final boolean isExistVegetarianMenu = Dish.menu.stream().anyMatch(d -> d.getCalories() < 500);
        if (isExistVegetarianMenu) {
            System.out.println("存在卡路里小于500的菜");
        }
    }

    /**
     * 判断卡路里是否都小于500
     */
    @Test
    public void allMatch() {
        final boolean isHealthMenu = Dish.menu.stream().allMatch(d -> d.getCalories() < 500);
        if (isHealthMenu) {
            System.out.println("卡路里都小于500");
        }
    }

    /**
     * 判断卡路里是否都不大于500
     */
    @Test
    public void noneMatch() {
        final boolean isHealthMenu = Dish.menu.stream().noneMatch(d -> d.getCalories() < 500);
        if (isHealthMenu) {
            System.out.println("卡路里都不小于500");
        }
    }

    /**
     * 输出素菜的个数
     */
    @Test
    public void count() {
        System.out.println(Dish.menu.stream().filter(Dish::isVegetarian).count());
    }

    /**
     * 输出卡路里最高的素菜
     */
    @Test
    public void max() {
        System.out.println(Dish.menu.stream().filter(Dish::isVegetarian).max(comparing(Dish::getCalories)));
    }

    /**
     * 输出卡路里最低的荤菜
     */
    @Test
    public void min() {
        System.out.println(Dish.menu.stream().filter(dish -> !dish.isVegetarian()).min(comparing(Dish::getCalories)));
    }
}
