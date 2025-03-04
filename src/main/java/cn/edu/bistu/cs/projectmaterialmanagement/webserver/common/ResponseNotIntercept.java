package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseNotIntercept {
    String value() default "";
}
