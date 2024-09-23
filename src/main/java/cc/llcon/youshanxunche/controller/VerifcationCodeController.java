package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.controller.request.VerificationCodeRequest;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.service.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/verification")
public class VerifcationCodeController {

    private final VerificationCodeService verificationCodeService;

    public VerifcationCodeController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    @PostMapping("/register")
    public Result generateRegisterVerificationCode(@RequestBody VerificationCodeRequest request) {
        Integer code = verificationCodeService.sendRegisterVerificationCode(request);
        if (code.equals(200)) {
            return Result.success();
        } else if (code.equals(412)) {
            return Result.error("发送过于频繁,请稍后再试");
        } else if (code.equals(413)) {
            return Result.error("用户已存在");
        } else {
            return Result.error("验证码发送失败");
        }
    }

    @PostMapping
    public Result generateVerificationCode(@RequestBody VerificationCodeRequest request) {
        Integer code = verificationCodeService.sendVerificationCode(request);
        if (code.equals(200)) {
            return Result.success();
        } else if (code.equals(412)) {
            return Result.error(code, "发送过于频繁,请稍后再试", null);
        } else if (code.equals(430)) {
            return Result.error("NOT_LOGIN");
        } else {
            return Result.error(code, "验证码发送失败", null);
        }
    }
}
