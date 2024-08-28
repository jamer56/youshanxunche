package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.controller.request.AddDeviceRequest;
import cc.llcon.youshanxunche.controller.request.DeviceLoginRequest;
import cc.llcon.youshanxunche.controller.request.ModifyDeviceInfoRequest;
import cc.llcon.youshanxunche.controller.vo.DeviceLoginVO;
import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListDevice;
import cc.llcon.youshanxunche.pojo.ListDeviceParam;

public interface DeviceService {
    /**
     * 注册新的装置
     *
     * @param device
     * @return
     */
    Device register(Device device);

    /**
     * 查询所有的装置
     *
     * @return
     */
    ListDevice list();

    /**
     * 根据id查询设备
     *
     * @param id 设备的唯一标识符。此参数不应为null
     * @return 对应于给定ID的设备对象。如果找不到，则可能返回null
     */
    Device getById(String id);

    /**
     * 处理设备登录请求。
     * 根据设备ID和MAC地址查询设备信息，如果设备存在，则生成并返回JWT令牌，用于设备身份验证。
     *
     * @param device 登录请求中的设备信息，包含设备ID和MAC地址。
     * @return 如果设备存在并登录成功，返回包含JWT令牌的DeviceLoginVO对象；如果设备不存在，返回null。
     */
    DeviceLoginVO login(DeviceLoginRequest device);

    /**
     * 修改设备信息
     *
     * @param device 要修改的設備 含有目標配置
     */
    Boolean modifyDeviceInfo(ModifyDeviceInfoRequest device);

    /**
     * 用户添加设备
     *
     * @param device
     * @return
     */
    String addDevice(AddDeviceRequest device);

    /**
     * 條件查詢設備
     * 管理員使用
     *
     * @param param
     * @return
     */
    ListDevice listAllByParam(ListDeviceParam param);
}