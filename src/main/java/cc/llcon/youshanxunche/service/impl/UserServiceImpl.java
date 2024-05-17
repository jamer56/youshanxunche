package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.mapper.UserMapper;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.service.UserService;
import cc.llcon.youshanxunche.utils.JwtUtils;

import com.sanctionco.jmail.EmailValidationResult;
import com.sanctionco.jmail.JMail;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
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

    //todo 登入記錄
    /**
     * 用户登入
     * @param user
     * @return
     */
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

            log.debug(u.toString());

            //添加錯誤次數
            u.setFailCount(u.getFailCount() + 1);

//			log.debug("次数加后:{}",u.getFailCount());

            u.setLastLoginTime(LocalDateTime.now());
            userMapper.update(u);

            log.info("{}登入失败 次數{}", user.getUsername(), u.getFailCount());

            return u;
        }
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public String register(User user) {
        //1.验证输入
        //1.1 用户名 姓名
        if (user.getUsername() == null || user.getUsername().isBlank() || user.getName() == null || user.getName().isBlank()) {
            return "用户名或姓名為空";
        }

        //判断用户名是否存在
        User user1 = userMapper.getByUsername(user.getUsername());
        if (user1 != null) {
            return "用户名已存在";
        }


        //1.2 email
        String email = user.getEmail();
        EmailValidationResult validate = JMail.validate(email);

        if (validate.isSuccess()) {
            log.info("email验证通过");
        } else {
            log.warn("email驗證失敗 失败原因:{}", validate.getFailureReason());
            return validate.getFailureReason().toString();
        }
        User user2 = userMapper.getByEmail(user.getEmail());

        //判断email 是否存在
        if (user2 != null) {
            return "郵箱已存在";
        }

        //1.3 密码

        PasswordData passwordData = new PasswordData(user.getUsername(), user.getPassword());
        //建立密码规则
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8, 32));
        CharacterCharacteristicsRule characterCharacteristicsRule = new CharacterCharacteristicsRule(4,
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );
        rules.add(characterCharacteristicsRule);
        rules.add(new UsernameRule());
        rules.add(new WhitespaceRule());
        //驗證
        //創建驗證器
        PasswordValidator passwordValidator = new PasswordValidator(rules);

        RuleResult passwordValidate = passwordValidator.validate(passwordData);

        if (passwordValidate.isValid()) {
            log.info("密碼驗證成功");
        } else {
            log.error(passwordValidate.getDetails().toString());
            return passwordValidate.getDetails().get(0).getErrorCode();
        }

        //2. 完整数据
        user.setId(UUID.randomUUID().toString().replace("-", ""));
        user.setSalt(UUID.randomUUID().toString().replace("-", ""));
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

    /**
     * 獲取用戶資訊
     *
     * @return
     */
    @Override
    public User getUserInfo(HttpServletRequest request) {
        //驗證權限 todo 越權記錄
        //获取用户uid
        String jwt = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseJWT(jwt);
        String uid = (String) claims.get("id");
        //获取用户资料
        User user = userMapper.getById(uid);
        //清除不必要信息
        user.setLastLoginTime(null);
        user.setPassword(null);
        user.setFailCount(null);
        user.setPermission(null);
        user.setSalt(null);
        //回传
        return user;
    }

    /**
     * 修改用户信息
     * @param request
     * @param user
     * @return
     */
    @Override
    public boolean modifyUserInfo(HttpServletRequest request, @NotNull User user) {
        //确认输入
        if (user.getName() == null || user.getName().isBlank()){
            log.info("修改用户信息失败 验证输入失败");
            return false;
        }

        String email = user.getEmail();
        EmailValidationResult validate = JMail.validate(email);

        if (validate.isSuccess()) {
            log.info("email验证通过");
        } else {
            log.warn("修改用户信息失败 email驗證失敗 失败原因:{}", validate.getFailureReason());
            return false;
        }

        //获取uid
        String uid = (String)JwtUtils.parseJWT((request.getHeader("Authorization"))).get("id");
        //添加信息
        user.setId(uid);
        user.setUpdateTime(LocalDateTime.now());
        //修改数据库
        return userMapper.update(user);
    }
}