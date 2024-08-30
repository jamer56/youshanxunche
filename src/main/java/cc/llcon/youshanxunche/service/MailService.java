package cc.llcon.youshanxunche.service;

import java.util.Collection;

public interface MailService {
    void sendPlainText(Collection<String> receivers, String subject, String content);
}
