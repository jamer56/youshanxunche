package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.mapper.UserMapper;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.service.UserService;
import cc.llcon.youshanxunche.utils.JwtUtils;

import com.sanctionco.jmail.EmailValidationResult;
import com.sanctionco.jmail.JMail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

	@Override
	public String register(User user) {
		//1.验证输入
		//1.1 用户名 姓名
		if (user.getUsername()==null||user.getUsername().isBlank()||user.getName()==null||user.getName().isBlank()){
			return "用户名或姓名為空";
		}

			//判断用户名是否存在
		User user1 = userMapper.getByUsername(user.getUsername());
		if (user1!=null){
			return "用户名已存在";
		}


		//1.2 email
		String email = user.getEmail();
		EmailValidationResult validate = JMail.validate(email);

		if (validate.isSuccess()){
			log.info("email验证通过");
		}else {
			log.warn("email驗證失敗 失败原因:{}",validate.getFailureReason());
			return validate.getFailureReason().toString();
		}
			User user2 = userMapper.getByEmail(user.getEmail());

			//判断email 是否存在
		if (user2!=null){
			return "郵箱已存在";
		}

		//1.3 密码

		PasswordData passwordData = new PasswordData(user.getUsername(),user.getPassword());
			//建立密码规则
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(new LengthRule(8,32));
		CharacterCharacteristicsRule characterCharacteristicsRule =new CharacterCharacteristicsRule(4,
				new CharacterRule(EnglishCharacterData.UpperCase,1),
				new CharacterRule(EnglishCharacterData.LowerCase,1),
				new CharacterRule(EnglishCharacterData.Digit,1),
				new CharacterRule(EnglishCharacterData.Special,1)
		);
		rules.add(characterCharacteristicsRule);
		rules.add(new UsernameRule());
		rules.add(new  WhitespaceRule());
			//驗證
			//創建驗證器
		PasswordValidator passwordValidator = new PasswordValidator(rules);

		RuleResult passwordValidate = passwordValidator.validate(passwordData);

		if (passwordValidate.isValid()){
			log.info("密碼驗證成功");
		}else {
			log.error(passwordValidate.getDetails().toString());
			return passwordValidate.getDetails().get(0).getErrorCode().toString();
		}

		//2. 完整数据
		user.setId(UUID.randomUUID().toString().replace("-",""));
		user.setSalt(UUID.randomUUID().toString().replace("-",""));
		String password = user.getPassword();
		//2.2 密碼
			//加盐
		password = password.concat(user.getSalt());
			//杂凑
		password = DigestUtils.sha256Hex(password);
			//回寫
		user.setPassword(password);

		user.setCreateTime(LocalDateTime.now());
		user.setUpdateTime(LocalDateTime.now());
		user.setLastLoginTime(LocalDateTime.now());
		//3. 写数据库

		userMapper.inst(user);


		//4. 返回状态
		return "success";
	}
}
