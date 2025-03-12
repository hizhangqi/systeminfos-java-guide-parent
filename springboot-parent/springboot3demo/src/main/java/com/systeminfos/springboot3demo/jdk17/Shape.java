package com.systeminfos.springboot3demo.jdk17;

/**
 * 封闭类和接口（Sealed Classes） [JEP 409]
 * <p>
 * 封闭类允许你定义一个类的子类范围，使得设计更加明确。通过 sealed、non-sealed 和 permits 关键字，可以控制哪些类能够继承父类。
 * 提高了代码的安全性和可读性。
 * 配合 switch 表达式，可以更好地实现模式匹配。
 */
public sealed class Shape permits Circle, Rectangle {
}