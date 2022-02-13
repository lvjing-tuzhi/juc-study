package com.tuzhi;

import java.util.Arrays;
import java.util.List;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-13 16:02
 **/

public class Test {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("A", "B");
        strings.forEach(System.out::println);
    }
}
