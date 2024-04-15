package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListDevice;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/devices")
public class DeviceController {
	@Autowired
	DeviceService deviceService;

	/**
	 * 注册设备
	 * @param device
	 * @return
	 */
	@PostMapping
	public Result register(@RequestBody Device device){
		Device d = deviceService.register(device);

		return Result.success(d.getId());
	}

	/**
	 * 查询设备列表
	 * @return
	 */
	@GetMapping
	public Result list(){

		ListDevice deviceList = deviceService.list();

		return Result.success(deviceList);
	}

	/**
	 * 根据id获取感測器列表
	 */
	@GetMapping("/{id}/listSensor")
	public Result listSensor(@PathVariable String id){

		List<Integer> listSensor = deviceService.listSensor(id);

		return Result.success(listSensor);
	}

	/**
	 * 通过id查询设备
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Result getById(@PathVariable String id){
		log.info("根据id查询设备 ID:{}",id);
		Device device = deviceService.getById(id);

		return Result.success(device);
	}

	/**
	 * 修改设备信息
	 */
	@PutMapping
	public Result modifyDeviceInfo(@RequestBody Device device){
		log.info("修改设备信息 参数:{}",device);

		if (deviceService.modifyDeviceInfo(device)) {
			return Result.success();
		}else {
			return Result.error("更新失败");
		}
	}
}
