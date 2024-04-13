package cc.llcon.youshanxunche.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class User {
    private String id;
    private String username;
    private String name;
    private String email;
    private String password;
    private String salt;
    private String jwt;
    private Integer gender;
    private Integer permission;
    private Integer fail_count;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastLoginTime;
}
