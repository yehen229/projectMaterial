package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common;

import java.lang.annotation.*;

@Target({ElementType.METHOD})//注解放置的目标位置即方法级别
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented
public @interface SysLogAnnotation {
    
    int opType() default 0;//操作类型

    String opModule() default "";//操作模块


}
