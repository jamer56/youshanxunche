package cc.llcon.youshanxunche.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private Integer failCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastLoginTime;
}
