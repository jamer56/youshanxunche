package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.anno.LoginLog;
import cc.llcon.youshanxunche.controller.request.DeviceLoginRequest;
import cc.llcon.youshanxunche.controller.vo.DeviceLoginVO;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.service.DeviceService;
import cc.llcon.youshanxunche.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@RestController
public class LoginController {
    final UserService userService;
    final DeviceService deviceService;

    public LoginController(UserService userService, DeviceService deviceService) {
        this.userService = userService;
        this.deviceService = deviceService;
    }

    /**
     * 用户登入
     *
     * @param user 使用者对象
     * @return 结果
     */
    @LoginLog
    @PostMapping(value = "/login", consumes = "*/*")
    public Result login(@RequestBody User user) {
        log.info("用户登入 username:{}", user.getUsername());
        User u = userService.login(user);

        if (u != null) {
            if (u.getJwt() != null) {
                log.info("生成的jwt:{}", u.getJwt());
                return Result.success(u.getJwt());
            } else {
                if (u.getBan().isAfter(LocalDateTime.now())) {
                    ArrayList<String> data = new ArrayList<>();
                    data.add(u.getBanReason());
                    data.add(u.getBan().toString());
                    return Result.error("賬號被封禁", data);
                }
                return Result.error("账号或密码错误");
            }
        } else {
            return Result.error("账号或密码错误");
        }
    }

    @LoginLog
    @PostMapping(value = "/glylogin", consumes = "*/*")
    public Result adminLogin(@RequestBody User user) {
        log.info("管理员登入 username:{}", user.getUsername());
        User u = userService.login(user);

        if (u != null) {
            if (u.getJwt() != null) {
                log.info("生成的jwt:{}", u.getJwt());
                if (u.getPermission() != 2) {
                    throw new RuntimeException("使用者登入管理员页面 使用者:" + u.getUsername(), new RuntimeException("管理员接口越权"));
                }
                return Result.success(u.getJwt());
            } else {
                if (u.getBan().isAfter(LocalDateTime.now())) {
                    return Result.error("账号被封禁", u.getBanReason());
                }
                return Result.error("账号或密码错误");
            }
        } else {
            return Result.error("账号或密码错误");
        }
    }


    /**
     * 设备登入
     *
     * @param device
     * @return
     */
    @PostMapping(value = "/devices/login")
    public Result deviceLogin(@RequestBody DeviceLoginRequest device) {
        log.info("设备登入 device:{}", device);
        DeviceLoginVO d = deviceService.login(device);

        if (d != null) {
//			log.info("生成的jwt:{}",u.getJwt());
            return Result.success(d.getJwt());
        } else {
            return Result.error("登入失败");
        }
    }

}
