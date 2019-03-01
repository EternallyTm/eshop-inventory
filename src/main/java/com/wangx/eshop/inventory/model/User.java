package com.wangx.eshop.inventory.model;

/**
 * 测试实体类
 * @author wangx
 * @date 2018/03/01
 */
public class User {

    /**
     * 测试姓名
     */
    private String name;

    /**
     * 测试年龄
     */
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
