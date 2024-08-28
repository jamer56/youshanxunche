package cc.llcon.youshanxunche.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 添加设备请求类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDeviceRequest {
    // 设备id
    private String id;
}
