package com.tuzhi.stream;

/**
 * @program: JUC-study
 * @description: Stream流计算
 * @author: 兔子
 * @create: 2022-02-13 16:17
 **/

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * 题目要求，一分钟，一行代码解决问题
 * 现在有五个用户，按要求进行筛选
 * 1、ID必须是偶数。
 * 2、年龄必须是大于23岁
 * 3、用户名转为大写字母
 * 4、用户名字母倒着排序
 * 只输出一个用户
 */
public class StreamTest0 {
    public static void main(String[] args) {
        User user = new User(1, "a", 21);
        User user1 = new User(2, "b", 22);
        User user2 = new User(3, "c", 23);
        User user3 = new User(4, "d", 24);
        User user4 = new User(6, "e", 25);
        List<User> users = Arrays.asList(user, user1, user2, user3, user4);
//        转为Stream流
        users.stream()
                .filter(o -> {return o.getId() % 2 == 0;})
                .filter(o -> {return o.getAge() > 23;})
                .peek(o -> {o.setName(o.getName().toUpperCase());})
                .sorted((o1, o2) -> {return o2.getName().compareTo(o1.getName());})
                .limit(1)
                .forEach(System.out::println);
    }
}
