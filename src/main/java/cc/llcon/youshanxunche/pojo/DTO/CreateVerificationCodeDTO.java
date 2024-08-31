package cc.llcon.youshanxunche.pojo.DTO;

import cc.llcon.youshanxunche.constant.VerificationCodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVerificationCodeDTO {
    private String uid;
    private String email;
    private Integer type;
    private Integer code;
    private Boolean used;
    LocalDateTime createTime;
}
