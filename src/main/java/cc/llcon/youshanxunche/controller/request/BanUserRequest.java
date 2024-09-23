package cc.llcon.youshanxunche.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanUserRequest {
    private String uid;
    private LocalDateTime banTime;
    private String reason;
}
