package com.ican.aspect;

import com.alibaba.fastjson2.JSON;
import com.ican.entity.ExceptionLog;
import com.ican.manager.AsyncManager;
import com.ican.manager.factory.AsyncFactory;
import com.ican.utils.IpUtils;
import com.ican.utils.UserAgentUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * AOP记录异常日志
 *
 * @author ican
 */
@Aspect
@Component
public class ExceptionLogAspect {

    /**
     * 设置操作异常日志切入点，扫描所有controller包下的操作
     */
    @Pointcut("execution(* com.ican.controller..*.*(..))")
    public void exceptionLogPointCut() {
    }

    /**
     * 异常通知，只有连接点异常后才会执行
     *
     * @param joinPoint 切面方法的信息
     * @param e         异常
     */
    @AfterThrowing(pointcut = "exceptionLogPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 获取操作
        Api api = (Api) signature.getDeclaringType().getAnnotation(Api.class);
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        ExceptionLog exceptionLog = new ExceptionLog();
        // 异常模块
        exceptionLog.setModule(api.tags()[0]);
        // 请求URI
        exceptionLog.setUri(request.getRequestURI());
        // 异常名称
        exceptionLog.setName(e.getClass().getName());
        // 操作描述
        exceptionLog.setDescription(apiOperation.value());
        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;
        // 异常方法名称
        exceptionLog.setErrorMethod(methodName);
        // 异常信息
        exceptionLog.setMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
        // 请求参数
        if (joinPoint.getArgs()[0] instanceof MultipartFile) {
            exceptionLog.setParams(((MultipartFile) joinPoint.getArgs()[0]).getOriginalFilename());
        } else {
            exceptionLog.setParams(JSON.toJSONString(joinPoint.getArgs()));
        }
        // 请求方式
        exceptionLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        // 操作ip和操作地址
        String ip = IpUtils.getIpAddress(request);
        exceptionLog.setIpAddress(ip);
        exceptionLog.setIpSource(IpUtils.getIpSource(ip));
        // 操作系统和浏览器
        Map<String, String> userAgentMap = UserAgentUtils.parseOsAndBrowser(request.getHeader("User-Agent"));
        exceptionLog.setOs(userAgentMap.get("os"));
        exceptionLog.setBrowser(userAgentMap.get("browser"));
        // 保存到数据库
        AsyncManager.getInstance().execute(AsyncFactory.recordException(exceptionLog));
    }

    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stet : elements) {
            stringBuilder.append(stet).append("\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n" + stringBuilder;
    }

}
