package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.mapper.UserMapper;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.service.UserService;
import cc.llcon.youshanxunche.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Override
	public User login(User user) {
		log.info("用户登入{}", user);
		//查询数据库
		User u = userMapper.getByUsername(user.getUsername());
		//判断用户是否存在
		if (u == null) {
			return null;
		}

		//加盐杂凑
		String password = user.getPassword();
//		log.debug("加盐前:{}", password);
		//加盐
		password = password.concat(u.getSalt());
//		log.debug("加盐完:{}", password);
		//杂凑
		password = DigestUtils.sha256Hex(password);
//		log.debug("杂凑:{}:", password);

		//验证
		if (password.equals(u.getPassword())) {
			//密码正确
			//更新最后更新时间 清除失敗次數
			u.setLastLoginTime(LocalDateTime.now());
			u.setFailCount(0);

			//暫時記錄錯誤次數
			Integer temp = u.getFailCount();
			userMapper.update(u);

			//更新完寫回失敗次數
			u.setFailCount(temp);

			//抹除敏感信息
			user.setPassword("");

			//生成jwt令牌
			Map<String, Object> claims = new HashMap<>();
			claims.put("id", u.getId());
			claims.put("username", u.getUsername());
			claims.put("permission", u.getPermission());

			String jwt = JwtUtils.generateJWT(claims);
			u.setJwt(jwt);

			log.info("登入成功{}", u);
			return u;
		} else {
			//脫敏
			user.setPassword("");

			if (u==null){
				log.info("{}登入失败",user.getUsername());
				return null;
			}
			log.debug(u.toString());

			//添加錯誤次數
			u.setFailCount(u.getFailCount()+1);

//			log.debug("次数加后:{}",u.getFailCount());

			u.setLastLoginTime(LocalDateTime.now());
			userMapper.update(u);

			log.info("{}登入失败 次數{}", user.getUsername(),u.getFailCount());

			return u;
		}
	}
}
