package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.controller.request.DeviceLoginRequest;
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
     * @return
     */
    Device getById(String id);

    /**
     * 设备登入
     *
     * @param device
     * @return
     */
    DeviceLoginVO login(DeviceLoginRequest device);

    /**
     * 修改设备信息
     *
     * @param device 要修改的設備 含有目標配置
     */
    Boolean modifyDeviceInfo(Device device);

    /**
     * 用户添加设备
     *
     * @param device
     * @return
     */
    String addDevice(Device device);

    /**
     * 條件查詢設備
     * 管理員使用
     *
     * @param param
     * @return
     */
    ListDevice listAllByParam(ListDeviceParam param);
}