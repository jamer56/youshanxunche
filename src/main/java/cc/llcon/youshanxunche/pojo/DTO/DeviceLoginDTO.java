package cc.llcon.youshanxunche.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceLoginDTO {
    private byte[] id;
    private byte[] macAddress;
}
