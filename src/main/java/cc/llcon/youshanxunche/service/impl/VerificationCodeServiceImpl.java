package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.constant.VerificationCodeType;
import cc.llcon.youshanxunche.controller.request.VerificationCodeRequest;
import cc.llcon.youshanxunche.mapper.UserMapper;
import cc.llcon.youshanxunche.mapper.VerificationCodeMapper;
import cc.llcon.youshanxunche.pojo.DTO.CreateVerificationCodeDTO;
import cc.llcon.youshanxunche.pojo.User;
import cc.llcon.youshanxunche.service.MailService;
import cc.llcon.youshanxunche.service.VerificationCodeService;
import cc.llcon.youshanxunche.utils.JwtUtils;
import com.sanctionco.jmail.EmailValidationResult;
import com.sanctionco.jmail.JMail;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    final HttpServletRequest httpServletRequest;
    final MailService mailService;
    final VerificationCodeMapper verificationCodeMapper;
    final UserMapper userMapper;

    Random random = new Random();


    public VerificationCodeServiceImpl(HttpServletRequest httpServletRequest, MailService mailService, VerificationCodeMapper verificationCodeMapper, UserMapper userMapper) {
        this.httpServletRequest = httpServletRequest;
        this.mailService = mailService;
        this.verificationCodeMapper = verificationCodeMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Integer sendRegisterVerificationCode(VerificationCodeRequest request) {
        // 设定发送类型
        request.setCodeType(VerificationCodeType.REGISTER);

        // 判断参数是否合法
        if (request.getEmail().isBlank()) {
            throw new RuntimeException("邮箱不能为空");
        } else {
            EmailValidationResult validate = JMail.validate(request.getEmail());
            if (!validate.isSuccess()) {
                throw new RuntimeException("邮箱不合法");
            }
        }
        // 生成驗證碼
        Integer verificationCodeTmp = random.nextInt(0, 1000000);
        String verificationCode = String.format("%06d", verificationCodeTmp);

        if (verificationCodeMapper.getVerificationCodeByEmail(request.getEmail()) != null) {
            //操作频繁
            return 412;
        }

        // 記錄數據庫
        CreateVerificationCodeDTO verificationCodeDTO = new CreateVerificationCodeDTO(null, request.getEmail(), request.getCodeType().getCode(), verificationCodeTmp, false, LocalDateTime.now());
        verificationCodeMapper.createVerificationCode(verificationCodeDTO);

        // 发送验证码
        mailService.sendRegisterVerificationCode(request.getEmail(), request.getNickname(), verificationCode);

        return 200;
    }

    @Override
    public Integer sendVerificationCode(VerificationCodeRequest request) {
        // 设定发送类别
        if (request.getType().equals(1)) {
            return 410;
        }
        try {
            request.setCodeType(VerificationCodeType.fromValue(request.getType()));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException exception) {
            // 无效的类型
            return 410;
        }

        // 生成驗證碼
        Integer verificationCodeTmp = random.nextInt(0, 1000000);
        String verificationCode = String.format("%06d", verificationCodeTmp);

        Claims claims = JwtUtils.parseJWT((httpServletRequest.getHeader("Authorization")));
        String uid = (String) claims.get("id");
        String username = (String) claims.get("username");

        // 查询email
        User userById = userMapper.getById(uid);
        request.setEmail(userById.getEmail());

        if (verificationCodeMapper.getVerificationCodeByEmail(request.getEmail()) != null) {
            //操作频繁
            return 412;
        }

        // 記錄數據庫
        CreateVerificationCodeDTO verificationCodeDTO = new CreateVerificationCodeDTO(uid, request.getEmail(), request.getCodeType().getCode(), verificationCodeTmp, false, LocalDateTime.now());
        verificationCodeMapper.createVerificationCode(verificationCodeDTO);

        // 发送验证码
        mailService.sendVerificationCodeByType(request.getEmail(), username, verificationCode, request.getCodeType());


        return 200;
    }

}
