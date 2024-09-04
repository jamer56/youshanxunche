package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.controller.request.VerificationCodeRequest;

public interface VerificationCodeService {
    /**
     * 发送注册验证码
     *
     * @return
     */
    Integer sendRegisterVerificationCode(VerificationCodeRequest request);

    /**
     * 发送验证码
     * @param request
     * @return
     */
    Integer sendVerificationCode(VerificationCodeRequest request);
}
