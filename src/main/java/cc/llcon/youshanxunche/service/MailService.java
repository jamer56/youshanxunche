package cc.llcon.youshanxunche.service;

import java.util.Collection;

public interface MailService {
    void sendMail(Collection<String> receivers, String subject, String content);

    void sendRegisterVerificationCode(String receiver, String nickname, String code);
}
