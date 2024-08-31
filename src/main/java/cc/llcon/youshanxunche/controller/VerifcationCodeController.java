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

    @PostMapping
    public Result generateVerificationCode(@RequestBody VerificationCodeRequest request) {
        Integer code = verificationCodeService.sendVerificationCode(request);
        if (code.equals(200)) {
            return Result.success();
        } else {
            return Result.error("验证码发送失败");
        }
    }
}
