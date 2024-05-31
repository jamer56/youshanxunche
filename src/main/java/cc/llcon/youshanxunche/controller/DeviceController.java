package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.anno.OperateLog;
import cc.llcon.youshanxunche.anno.SecurityAuth;
import cc.llcon.youshanxunche.anno.SelectLog;
import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListDevice;
import cc.llcon.youshanxunche.pojo.ListDeviceParam;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/devices")
public class DeviceController {
	@Autowired
	DeviceService deviceService;

	/**
	 * 查询用戶自己的设备列表
	 */
	@SelectLog
	@GetMapping
	public Result list(){
		log.info("查询自有设备");
		ListDevice deviceList = deviceService.list();

		return Result.success(deviceList);
	}

	/**
	 * 通过id查询设备
	 * @param id 设备id
	 * @return 设备资讯
	 */
	@SelectLog
	@GetMapping("/{id}")
	public Result getById(@PathVariable String id){
		log.info("根据id查询设备 ID:{}",id);
		Device device = deviceService.getById(id);
		return Result.success(device);
	}

	/**
	 * 修改设备信息
	 * @param device 欲修改的资讯
	 * @return
	 */
	@OperateLog
	@PutMapping
	public Result modifyDeviceInfo(@RequestBody Device device){
		log.info("修改设备信息 参数:{}",device);

		if (deviceService.modifyDeviceInfo(device)) {
			return Result.success();
		}else {
			return Result.error("更新失败");
		}
	}

	/**
	 * 在设备管理内添加装置
	 * @param device
	 * @return
	 */
	@OperateLog
	@PostMapping
	public Result addDevice(@RequestBody Device device){
		log.info("添加設備:{}",device.getId());
		String status = deviceService.addDevice(device);

		if (status.equals("success")){
			return Result.success();
		}else {
			return Result.error(status);
		}
	}

	@SecurityAuth
	@SelectLog
	@GetMapping("/list")
	public Result listByParam(ListDeviceParam param){
		ListDevice listDevice = deviceService.listAllByParam(param);
		if (listDevice == null) {
			return Result.error("查無數據");
		}
		return Result.success(listDevice);
	}
}