package com.numble.reservationsystem.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 어디에 붙는 어노테이션 인지
@Retention(RetentionPolicy.RUNTIME) // 언제까지 유지되는 어노테이션인지
public @interface LogExecutionTime {
    // 내용은 없어도 됨
}
