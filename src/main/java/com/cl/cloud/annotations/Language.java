package com.cl.cloud.annotations;

import java.lang.annotation.*;

/**
 * indicate the language of a engine support.
 * Created by Ling Cao on 2016/10/12.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Language {

    String value() default "";

}
