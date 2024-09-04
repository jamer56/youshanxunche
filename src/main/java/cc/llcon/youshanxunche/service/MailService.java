package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.constant.VerificationCodeType;

import java.util.Collection;

public interface MailService {
    void sendMail(Collection<String> receivers, String subject, String content);

    void sendRegisterVerificationCode(String receiver, String nickname, String code);

    void sendVerificationCodeByType(String email, String username, String verificationCode, VerificationCodeType codeType);
}
