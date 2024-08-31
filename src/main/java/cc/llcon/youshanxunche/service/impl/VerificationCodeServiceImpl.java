package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.constant.VerificationCodeType;
import cc.llcon.youshanxunche.controller.request.VerificationCodeRequest;
import cc.llcon.youshanxunche.mapper.VerificationCodeMapper;
import cc.llcon.youshanxunche.pojo.DTO.CreateVerificationCodeDTO;
import cc.llcon.youshanxunche.service.MailService;
import cc.llcon.youshanxunche.service.VerificationCodeService;
import com.sanctionco.jmail.EmailValidationResult;
import com.sanctionco.jmail.JMail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    final HttpServletRequest httpServletRequest;
    final MailService mailService;
    final VerificationCodeMapper verificationCodeMapper;

    public VerificationCodeServiceImpl(HttpServletRequest httpServletRequest, MailService mailService, VerificationCodeMapper verificationCodeMapper) {
        this.httpServletRequest = httpServletRequest;
        this.mailService = mailService;
        this.verificationCodeMapper = verificationCodeMapper;
    }

    @Override
    public Integer sendVerificationCode(VerificationCodeRequest request) {
        // 判断发送类型
        if (httpServletRequest.getHeader("Authorization") == null) {
//            request.setType(VerificationCodeType.REGISTER);
            request.setType(VerificationCodeType.fromValue(1));

        } else if (httpServletRequest.getHeader("Authorization").isBlank()) {
//            request.setType(VerificationCodeType.REGISTER);
            request.setType(VerificationCodeType.fromValue(1));
        }

        //判断参数是否合法
        if (request.getEmail().isBlank()) {
            throw new RuntimeException("邮箱不能为空");
        } else {
            EmailValidationResult validate = JMail.validate(request.getEmail());
            if (!validate.isSuccess()) {
                throw new RuntimeException("邮箱不合法");
            }
        }
        // 生成驗證碼
        Random random = new Random();
        Integer verificationCodeTmp = random.nextInt(0, 1000000);
        String verificationCode = String.format("%06d", verificationCodeTmp);

        //記錄數據庫
        CreateVerificationCodeDTO verificationCodeDTO = new CreateVerificationCodeDTO(null, request.getEmail(), request.getType().getCode(), verificationCodeTmp, false, LocalDateTime.now());
        verificationCodeMapper.createVerificationCode(verificationCodeDTO);

        // 发送验证码
        switch (request.getType()) {
            case REGISTER:
                // 注册验证码
                mailService.sendRegisterVerificationCode(request.getEmail(), request.getNickname(), verificationCode);
                break;
            case MODIFY:
                // 修改密码验证码
                break;
            case FORGET:
                // 忘记密码验证码
                break;
            default:
                throw new RuntimeException("验证码类型错误");
        }
        return 200;
    }
}
