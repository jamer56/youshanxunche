package cc.llcon.youshanxunche.controller.vo;

import cc.llcon.youshanxunche.pojo.User;
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

    public UserInfoVo(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setGender(user.getGender());
        this.setPermission(user.getPermission());
        this.setFailCount(user.getFailCount());
        this.setLastLoginTime(user.getLastLoginTime());
        this.setCreateTime(user.getCreateTime());
        this.setUpdateTime(user.getUpdateTime());
    }
}
