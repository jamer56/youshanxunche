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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SecurityAspect {

    @Autowired
    HttpServletRequest request;
    @Autowired
    UserMapper userMapper;

    @Around("@annotation(cc.llcon.youshanxunche.anno.SecurityAuth)")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        String jwt = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseJWT(jwt);
        String uid = (String) claims.get("id");
        Integer permission = (Integer) claims.get("permission");


        if (permission ==1){
            User user = userMapper.getById(uid);
            user.setPassword(null);
            user.setSalt(null);

            throw new RuntimeException("权限不足 使用者"+user,new RuntimeException("管理员接口越权"));
//            return Result.error("权限不足");
        }
        return joinPoint.proceed();
    }
}
