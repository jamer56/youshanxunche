package cc.llcon.youshanxunche.pojo.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeDAO {
    Integer id;
    String userId;
    Integer type;
    String email;
    String verificationCode;
    Boolean used;
    LocalDateTime createTime;
}


