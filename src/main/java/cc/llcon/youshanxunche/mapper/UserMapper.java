package cc.llcon.youshanxunche.mapper;

import cc.llcon.youshanxunche.pojo.ListUserParam;
import cc.llcon.youshanxunche.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
	/**
	 * 更新用户数据
	 *
	 * @param u user
	 * @return
	 */
	boolean update(User u);

	/**
	 * 根据使用者名称查询
	 * @param username 使用者名稱
	 * @return 查詢到的使用者
	 */
	User getByUsername(String username);

	void inst(User user);

	User getByEmail(String email);

	User getById(String uid);

	List<User> listByParam(ListUserParam param);
}
