package cc.llcon.youshanxunche.controller.request;

import cc.llcon.youshanxunche.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest extends User {
    String code;
}
