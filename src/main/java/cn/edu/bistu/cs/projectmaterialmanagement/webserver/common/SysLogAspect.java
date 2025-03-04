package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;

@Aspect
@Component
public class SysLogAspect {

    private final ILogService logService;


    public SysLogAspect(ILogService logService) {
        this.logService = logService;

    }

    /**
     * 设置操作日志切入点   在注解的位置切入代码
     */
    @Pointcut("@annotation(cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.SysLogAnnotation)")
    public void operationLogPointCut() {
    }

    @AfterReturning(returning  /**
     * 记录操作日志
     * @param joinPoint 方法的执行点
     * @param result  方法返回值
     * @throws Throwable
     */ = "result", value = "operationLogPointCut()")
    public void saveOperLog(JoinPoint joinPoint,
                            Object result)
            throws Throwable {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {


            //将返回值转换成map集合
            //Map<String, String> map = (Map<String, String>) result;

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            //获取切入点所在的方法
            Method method = signature.getMethod();

            //获取操作
            SysLogAnnotation annotation = method.getAnnotation(SysLogAnnotation.class);

            Log sysLog = new Log();
            if (annotation != null && annotation.opType() == ILogService.SYS_LOG_OP_TYPE_LOGIN) {
                //登录
                //   SysUserLoginResult sysUserLoginResult = (SysUserLoginResult) result;
                //   logService.add(sysUserLoginResult.getSysUserId(), "login", annotation.opType(), IPUtils.getClientIpAddr(request));
            } else {

                //  logService.add("123", annotation.opType(), IPUtils.getClientIpAddr(request));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
