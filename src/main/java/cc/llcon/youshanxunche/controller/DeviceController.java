package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListDevice;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.service.DeviceService;
import jakarta.servlet.http.HttpServletRequest;
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
	 * 查询设备列表
	 */
	@GetMapping
	public Result list(HttpServletRequest request){
		log.info("查询自有设备");
		ListDevice deviceList = deviceService.list(request.getHeader("Authorization"));

		return Result.success(deviceList);
	}


	/**
	 * 通过id查询设备
	 * @param id 设备id
	 * @return 设备资讯
	 */
	@GetMapping("/{id}")
	public Result getById(@PathVariable String id ,HttpServletRequest request){
		log.info("根据id查询设备 ID:{}",id);
		Device device = deviceService.getById(id,request);

		return Result.success(device);
	}


	/**
	 * 修改设备信息
	 * @param device 欲修改的资讯
	 * @param request 用于获取jwt令牌 自动注入
	 * @return
	 */
	@PutMapping
	public Result modifyDeviceInfo(@RequestBody Device device,HttpServletRequest request){
		log.info("修改设备信息 参数:{}",device);

		if (deviceService.modifyDeviceInfo(device,request)) {
			return Result.success();
		}else {
			return Result.error("更新失败");
		}
	}

	@PostMapping
	public Result addDevice(@RequestBody Device device,HttpServletRequest request){
		log.info("添加設備:{}",device.getId());
		String status = deviceService.addDevice(request,device);

		if (status.equals("success")){
			return Result.success();
		}else {
			return Result.error(status);
		}

	}
}
