package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.service.UserService;
import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.pojo.User;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
//@CrossOrigin("http://127.0.0.1:7000/")
public class LoginController {
    @Autowired
    UserService userService;

    /**
     * 用户登入
     * @param user 使用者对象
     * @return 结果
     */
    @PostMapping(value = "/login", consumes = "*/*")
    public Result login(@RequestBody User user) {
        log.info("用户登入 username:{}", user.getUsername());
        User u = userService.login(user);

        if (u != null) {
            if (u.getJwt() != null) {
                log.info("生成的jwt:{}",u.getJwt());
                return Result.success(u.getJwt());
            }else {
                return Result.error("账号或密码错误",u.getFailCount());
            }
        } else {
            return Result.error("账号或密码错误");
        }
    }

    //todo 设备登入
    /**
     * 设备登入
     * @param device
     * @return
     */
//	@PostMapping(value = "/devices/login")
//	public Result deviceLogin(@RequestBody Device device){
//		log.info("设备登入 username:{}",device.getId());
//		Device d = deviceService.login(device);
//
//		if (d!=null){
////			log.info("生成的jwt:{}",u.getJwt());
//			return Result.success(d.getJwt());
//		}else {
//			return Result.error("错误清联系管理员");
//		}
//	}

}
