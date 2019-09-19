package com.reallinxu.anno;

import java.lang.annotation.*;

/**
 * @author linxu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String[] value() default {};
}
