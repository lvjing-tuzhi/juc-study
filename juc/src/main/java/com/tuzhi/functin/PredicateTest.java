package com.tuzhi.functin;

import java.util.function.Predicate;

/**
 * @program: JUC-study
 * @description: 断定型接口
 * @author: 兔子
 * @create: 2022-02-13 15:31
 **/

public class PredicateTest {
    public static void main(String[] args) {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.equals("A");
            }
        };
        Predicate<String> predicate1 = s -> {return s.equals("A");};
    }
}
