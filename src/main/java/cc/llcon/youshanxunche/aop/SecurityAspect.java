package cc.llcon.youshanxunche.aop;

import cc.llcon.youshanxunche.mapper.UserMapper;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SecurityAspect {

    final HttpServletRequest request;
    final UserMapper userMapper;

    public SecurityAspect(HttpServletRequest request, UserMapper userMapper) {
        this.request = request;
        this.userMapper = userMapper;
    }

    @Around("@annotation(cc.llcon.youshanxunche.anno.SecurityAuth)")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("開始驗證管理員權限");
        String jwt = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseJWT(jwt);
        String uid = (String) claims.get("id");
        Integer permission = (Integer) claims.get("permission");


        if (permission == 1) {
            User user = userMapper.getById(uid);
            user.setPassword(null);
            user.setSalt(null);

            throw new RuntimeException("权限不足 使用者" + user, new RuntimeException("管理员接口越权"));
//            return Result.error("权限不足");
        }
        log.info("管理員權限驗證成功");
        return joinPoint.proceed();
    }
}
