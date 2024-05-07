package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.pojo.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    /**
     * 用户登入
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    String register(User user);

    /**
     * 获取用户资讯
     * @param request
     * @return
     */
    User getUserInfo(HttpServletRequest request);

    /**
     * 修改用户资讯
     * @param request
     * @param user
     * @return
     */
    boolean modifyUserInfo(HttpServletRequest request, User user);
}
