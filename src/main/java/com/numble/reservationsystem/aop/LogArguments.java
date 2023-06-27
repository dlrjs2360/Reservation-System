package com.numble.reservationsystem.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 어디에 붙는 어노테이션 인지
@Retention(RetentionPolicy.RUNTIME) //
public @interface LogArguments {

}
