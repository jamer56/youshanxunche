package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.controller.request.ModifyUserPasswordRequest;
import cc.llcon.youshanxunche.controller.request.UserRegisterRequest;
import cc.llcon.youshanxunche.pojo.ListUser;
import cc.llcon.youshanxunche.pojo.ListUserParam;
import cc.llcon.youshanxunche.pojo.User;

public interface UserService {
    /**
     * 用户登入
     *
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    String register(UserRegisterRequest user);

    /**
     * 获取用户资讯
     *
     * @return
     */
    User getUserInfo();

    /**
     * 修改用户资讯
     *
     * @param user
     * @return
     */
    boolean modifyUserInfo(User user);

    ListUser list(ListUserParam param);

    User userInfo(String uid);

    /**
     * 修改用户密码
     *
     * @param passwordRequest 修改密码请求
     * @return 1修改成功，0修改失败
     * @since 0.21.0:
     */
    Integer modifyPassword(ModifyUserPasswordRequest passwordRequest);
}
