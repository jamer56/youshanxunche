package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListDevice;

import java.util.List;

public interface DeviceService {
	/**
	 * 注册新的装置
	 * @param device
	 * @return
	 */
	Device register(Device device);

	/**
	 * 查询所有的装置
	 * @return
	 */
	ListDevice list();

	/**
	 * 根据id查询设备
	 * @return
	 */
	Device getById(String id);

	/**
	 * 设备登入
	 * @param device
	 * @return
	 */
	Device login(Device device);

	/**
	 * 根据deviceid 获取感测器id列表
	 * @param id
	 * @return
	 */
	List<Integer> listSensor(String id);

	/**
	 * 修改设备信息
	 * @param device
	 */
	Boolean modifyDeviceInfo(Device device);
}
