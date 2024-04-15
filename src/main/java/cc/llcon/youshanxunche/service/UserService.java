package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.pojo.User;



public interface UserService {
	/**
	 * 用户登入
	 * @param user
	 * @return
	 */
	User login(User user);
}
