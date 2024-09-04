package cc.llcon.youshanxunche.filter;

import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.utils.JwtUtils;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //1.获取url
        String url = req.getRequestURI();
        log.info("请求的url:{}", url);
        log.info("请求的方法:{}", req.getMethod());

        //判断是否为OPTIONS
        if (req.getMethod().equals("OPTIONS")) {
            //直接放行
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //2.判断是否为login请求 或是 register 请求
        if (url.contains("login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (url.contains("register")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
//        if (url.contains("verification")) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }


        //3.获取jwt令牌
        String jwt = req.getHeader("Authorization");

        //4.判断令牌是否存在
        if (!StringUtils.hasLength(jwt)) {
            log.info("token 为空 或不存在");
            Result error = Result.error("NOT_LOGIN");
            String result = JSONObject.toJSONString(error);
            resp.getWriter().write(result);
            return;
        }

        //5.校验令牌
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.error("登入異常", e);
            log.info("解析失败");
            Result error = Result.error("NOT_LOGIN");
            String result = JSONObject.toJSONString(error);
            resp.getWriter().write(result);
            return;
        }

        //6.放行
        log.info("令牌合法,放行");
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
