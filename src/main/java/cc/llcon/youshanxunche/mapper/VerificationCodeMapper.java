package cc.llcon.youshanxunche.mapper;

import cc.llcon.youshanxunche.pojo.DAO.VerificationCodeDAO;
import cc.llcon.youshanxunche.pojo.DTO.CreateVerificationCodeDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VerificationCodeMapper {
    Integer createVerificationCode(CreateVerificationCodeDTO createVerificationCodeDTO);

    VerificationCodeDAO getVerificationCode(String email, Integer code);

    void update_used(VerificationCodeDAO verificationCode);

    VerificationCodeDAO getVerificationCodeByEmail(String email);
}
