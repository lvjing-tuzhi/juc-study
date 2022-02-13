package com.tuzhi.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-13 16:20
 **/

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
}
