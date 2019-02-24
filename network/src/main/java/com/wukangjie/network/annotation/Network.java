package com.wukangjie.network.annotation;

import com.wukangjie.network.NetworkType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Network {
    NetworkType target() default NetworkType.AUTO;
}
