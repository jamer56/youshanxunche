package cc.llcon.youshanxunche.aop;

import cc.llcon.youshanxunche.mapper.OperateLogMapper;
import cc.llcon.youshanxunche.pojo.LoginLogPojo;
import cc.llcon.youshanxunche.pojo.OperateLog;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.utils.AuthUtils;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect

public class OperateLogAspect {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(cc.llcon.youshanxunche.anno.OperateLog)")
    public Object recordOperateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //操作人
        String operateUser = AuthUtils.getUID(request);
        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();
        //操作类名
        String className = joinPoint.getTarget().getClass().toString();
        //操作方法名
        String methodName = joinPoint.getSignature().getName();
        //操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);
        //调用原方法
        Long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long end = System.currentTimeMillis();
        //操作方法返回值
        String returnValue = JSONObject.toJSONString(result);
        //操作耗时
        Long costTime = end - begin;

        OperateLog operateLog = new OperateLog(null, operateUser, operateTime, className, methodName, methodParams, returnValue, costTime);
        operateLogMapper.insertOperateLog(operateLog);

        return result;
    }

    @Around("@annotation(cc.llcon.youshanxunche.anno.SelectLog)")
    public Object recordSelecteLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //操作人
        String operateUser = AuthUtils.getUID(request);
        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();
        //操作类名
        String className = joinPoint.getTarget().getClass().toString();
        //操作方法名
        String methodName = joinPoint.getSignature().getName();
        //操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);
        //调用原方法
        Long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long end = System.currentTimeMillis();

        //操作耗时
        Long costTime = end - begin;

        OperateLog operateLog = new OperateLog(null, operateUser, operateTime, className, methodName, methodParams, null, costTime);
        operateLogMapper.insertSelectLog(operateLog);

        return result;
    }

    @Around("@annotation(cc.llcon.youshanxunche.anno.LoginLog)")
    public Object recordLoginLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();
        //操作方法名
//        String methodName = joinPoint.getSignature().getName();
        //调用原方法
        Long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long end = System.currentTimeMillis();
        //操作使用者
        Object[] args = joinPoint.getArgs();
        User user = (User)Arrays.stream(args).toList().get(0);
        String operateUserName = user.getUsername();
        //操作方法返回值
        Result result1 = (Result) result;
        String returnValue=result1.getCode().toString();
        //操作耗时
        Long costTime = end - begin;

        LoginLogPojo loginlog = new LoginLogPojo(null,null,operateUserName,operateTime,returnValue,costTime);
        operateLogMapper.insertLoginLog(loginlog);

        return result;
    }
}
