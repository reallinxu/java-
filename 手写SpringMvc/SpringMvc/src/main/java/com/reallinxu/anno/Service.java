package com.reallinxu.anno;

import java.lang.annotation.*;

/**
 * @author linxu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
