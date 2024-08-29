package cc.llcon.youshanxunche.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    private String id;
    private String username;
    private String name;
    private String email;
    private Integer gender;
    private Integer permission;
    private Integer failCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastLoginTime;
}
