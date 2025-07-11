package cc.llcon.youshanxunche.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLog {
    private Integer id; //ID
    private LocalDateTime time; //操作时间
    private String classification;//分類
    private String message; //message
    private String stack; //StackTop
    private String stackTrace; //StackTrace
}
