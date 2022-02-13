package com.tuzhi.functin;

import java.util.function.Function;

/**
 * @program: JUC-study
 * @description:函数型接口
 * @author: 兔子
 * @create: 2022-02-13 13:39
 **/

public class FunctionTest {
    public static void main(String[] args) {
//        第一种使用方法
        Function<String, String> function = new Function<String,String>() {
            @Override
            public String apply(String o) {
                return "测试" + o;
            }
        };

//        第二种使用
        Function<String,String> function1 = (o) -> {return "测试" + o;};
        System.out.println(function.apply("hello world"));
    }
}
