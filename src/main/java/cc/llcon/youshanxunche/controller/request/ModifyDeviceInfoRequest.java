package cc.llcon.youshanxunche.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 修改设备信息请求体
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyDeviceInfoRequest {
    // 设备ID
    private String id;
    // 设备名称
    private String name;
    // 设备备注
    private String comment;
    // 更新时间
    private LocalDateTime updateTime;
}