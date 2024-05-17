package cc.llcon.youshanxunche.controller;


import cc.llcon.youshanxunche.anno.OperateLog;
import cc.llcon.youshanxunche.anno.SelectLog;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.service.UserService;
import cc.llcon.youshanxunche.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 獲取用戶資訊
     */
    @SelectLog
    @GetMapping
    public Result getUserInfo(HttpServletRequest request) {
        User user = userService.getUserInfo(request);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("獲取失敗");
        }
    }

    @OperateLog
    @PutMapping
    public Result modifyUserInfo(@RequestBody User user, HttpServletRequest request) {
        log.info("用户修改信息:{} 修改信息:{}", JwtUtils.parseJWT((request.getHeader("Authorization"))).get("id"),user.toString());
        if (userService.modifyUserInfo(request, user)) {
            return Result.success();
        } else {
            return Result.error("修改失败");
        }


    }
}
