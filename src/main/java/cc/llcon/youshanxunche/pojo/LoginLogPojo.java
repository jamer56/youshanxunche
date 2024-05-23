package cc.llcon.youshanxunche.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLogPojo {
    private Integer id; //ID
    private String operateUserId; //操作人ID
    private String operateUserName; //操作人username
    private LocalDateTime operateTime; //操作时间
    private String returnValue; //登入操作返回值
    private Long costTime; //操作耗时
}
