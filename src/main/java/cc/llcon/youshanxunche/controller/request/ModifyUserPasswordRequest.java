package cc.llcon.youshanxunche.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserPasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String verificationCode;
}
