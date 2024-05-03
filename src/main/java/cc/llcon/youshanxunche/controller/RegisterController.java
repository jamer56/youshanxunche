package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.service.DeviceService;
import cc.llcon.youshanxunche.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class RegisterController {

    /**
     * 用戶註冊接口
     */
    @Autowired
    UserService userService;
    @Autowired
    DeviceService deviceService;

    /**
     * 用戶註冊
     * @param user 註冊信息
     * @return 結果
     */
    @PostMapping("/register")
    Result register(@RequestBody User user) {
        log.info("新用户注册:{}", user.getUsername());
        String msg = userService.register(user);
        log.info("注册回传值:{}", msg);
        if (msg.equals("success")) {
            return Result.success();
        } else {
            return Result.error(msg);
        }
    }

    /**
     * 设备注册
     */
    @PostMapping("/devices/register")
    Result deviceRegister(@RequestBody Device device) {
        Device d = deviceService.register(device);
        if (d == null) {
            return Result.error("mac地址不存在");
        }
        return Result.success(d.getId());
    }

}

