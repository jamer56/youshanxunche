package cc.llcon.youshanxunche.controller.request;

import cc.llcon.youshanxunche.constant.VerificationCodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeRequest {
    private String email;
    private String nickname;
    private Integer type;
    private VerificationCodeType codeType;
}
