package cc.llcon.youshanxunche.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    private String id;
    private String name;
    private String userId;
    private String macAddress;
    private String comment;
    @JsonIgnore
    private String jwt;
    private LocalDateTime createTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updateTime;
    private LocalDateTime lastRecodeTime;
}
