package cc.llcon.youshanxunche.pojo.DTO;

import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.utils.UUIDUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyDeviceInfoDTO {
    private byte[] id;
    private byte[] userId;
    private String name;
    private String comment;
    private LocalDateTime updateTime;

    public ModifyDeviceInfoDTO(Device device) {
        this.id = UUIDUtils.UUIDtoBytes(device.getId());
        if (device.getUserId() != null) {
            this.userId = UUIDUtils.UUIDtoBytes(device.getUserId());
        }
        this.name = device.getName();
        this.comment = device.getComment();
        this.updateTime = device.getUpdateTime();
    }
}
