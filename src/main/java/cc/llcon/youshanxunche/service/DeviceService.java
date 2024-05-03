package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListDevice;
import jakarta.servlet.http.HttpServletRequest;

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
	ListDevice list(String jwt);

	/**
	 * 根据id查询设备
	 * @return
	 */
	Device getById(String id, HttpServletRequest request);

	/**
	 * 设备登入
	 * @param device
	 * @return
	 */
	Device login(Device device);


	/**
	 * 修改设备信息
	 *
	 * @param device
	 * @param request
	 */
	Boolean modifyDeviceInfo(Device device, HttpServletRequest request);

	/**
	 * 用户添加设备
	 *
	 * @param request
	 * @param device
	 * @return
	 */
	String addDevice(HttpServletRequest request, Device device);
}
