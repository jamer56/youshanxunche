package cc.llcon.youshanxunche.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListUserParam extends User{
    private Integer page=1;
    private Integer pageSize =10;
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    private LocalDateTime createTimeBegin;
    @JsonFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    private LocalDateTime createTimeEnd;
    @JsonFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    private LocalDateTime lastLoginTimeBegin;
    @JsonFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    private LocalDateTime lastLoginTimeEnd;
}
