package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.controller.request.UserRegisterRequest;
import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.service.DeviceService;
import cc.llcon.youshanxunche.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class RegisterController {
    final UserService userService;
    final DeviceService deviceService;

    public RegisterController(UserService userService, DeviceService deviceService) {
        this.userService = userService;
        this.deviceService = deviceService;
    }

    /**
     * 用戶註冊
     *
     * @param user 註冊信息
     * @return 結果
     */
    @PostMapping("/register")
    Result register(@RequestBody UserRegisterRequest user) {
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

