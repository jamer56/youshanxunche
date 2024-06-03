package cc.llcon.youshanxunche.aop;

import cc.llcon.youshanxunche.mapper.DeviceMapper;
import cc.llcon.youshanxunche.mapper.OperateLogMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class CheckOperateDevice {

    final HttpServletRequest request;
    final DeviceMapper deviceMapper;
    final OperateLogMapper logMapper;

    public CheckOperateDevice(HttpServletRequest request, DeviceMapper deviceMapper, OperateLogMapper logMapper) {
        this.request = request;
        this.deviceMapper = deviceMapper;
        this.logMapper = logMapper;
    }

    /**
     * todo 确认操作设备为自己的
     */
    @Around("@annotation(cc.llcon.youshanxunche.anno.CheckOperateDevice)")
    public Object CheckDevice(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取操作人uid

        //获取操作设备id

        return joinPoint.proceed();
    }
}
