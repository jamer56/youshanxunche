package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.constant.VerificationCodeType;
import cc.llcon.youshanxunche.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendMail(Collection<String> receivers, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("友善尋車系統 <youshanxunche@llcon.cc>");
            helper.setTo(receivers.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        mailSender.send(message);
    }

    public void sendRegisterVerificationCode(String receiver, String nickname, String code) {
        File mailTemplate = null;
        StringBuilder mailContent = new StringBuilder();

        try {
            mailTemplate = ResourceUtils.getFile("classpath:templates/mailtemplate.html");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            // 讀取信件模版
            Scanner scanner = new Scanner(mailTemplate);
            while (scanner.hasNext()) {
                mailContent.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String mail = mailContent.toString().replace("{{nickname}}", nickname).replace("{{code}}", code);


        Collection<String> receivers = Arrays.asList(receiver);
        this.sendMail(receivers, "友善尋車系統 註冊驗證碼", mail);
    }

    @Override
    public void sendVerificationCodeByType(String email, String username, String verificationCode, VerificationCodeType codeType) {
        File mailTemplate = null;
        StringBuilder mailContent = new StringBuilder();

        try {
            mailTemplate = ResourceUtils.getFile(codeType.getTemplates());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            // 讀取信件模版
            Scanner scanner = new Scanner(mailTemplate);
            while (scanner.hasNext()) {
                mailContent.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String mail = mailContent.toString().replace("{{username}}", username).replace("{{code}}", verificationCode);


        Collection<String> receivers = Arrays.asList(email);
        this.sendMail(receivers, "友善尋車系統 " + codeType.getDesc(), mail);

    }

}
