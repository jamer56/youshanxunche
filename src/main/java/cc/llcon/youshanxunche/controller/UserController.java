package cc.llcon.youshanxunche.controller;


import cc.llcon.youshanxunche.anno.OperateLog;
import cc.llcon.youshanxunche.anno.SecurityAuth;
import cc.llcon.youshanxunche.anno.SelectLog;
import cc.llcon.youshanxunche.pojo.ListUser;
import cc.llcon.youshanxunche.pojo.ListUserParam;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.service.UserService;
import com.alibaba.fastjson2.JSON;
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
    public Result getUserInfo() {
        User user = userService.getUserInfo();
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("獲取失敗");
        }
    }

    @OperateLog
    @PutMapping
    public Result modifyUserInfo(@RequestBody User user) {
        log.info("修改用戶信息信息:{}",user.toString());
        if (userService.modifyUserInfo(user)) {
            return Result.success();
        } else {
            return Result.error("修改失败");
        }


    }

    /**
     * 條件查詢 所有用戶接口
     * 管理員接口
     * @return 查詢到的用戶列表
     */
    @SecurityAuth
    @SelectLog
    @GetMapping("/list")
    public Result list(ListUserParam param){
        log.info("管理員查詢所有用戶 參數:{}", JSON.toJSONString(param));

        ListUser list = userService.list(param);

        if (list == null) {
            return Result.error("查無數據");
        }
        return Result.success(list);
    }
}
