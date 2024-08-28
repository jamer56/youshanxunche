package cc.llcon.youshanxunche.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddDeviceDTO {
    private byte[] id;
    private byte[] userId;
    private String name;
    private String comment;
    private LocalDateTime updateTime;
}
