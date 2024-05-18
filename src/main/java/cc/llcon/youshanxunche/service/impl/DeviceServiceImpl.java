package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.mapper.DeviceMapper;
import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListDevice;
import cc.llcon.youshanxunche.service.DeviceService;
import cc.llcon.youshanxunche.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	DeviceMapper deviceMapper;

	@Override
	public Device register(Device device) {
		if (device.getMacAddress() ==null){
			return null;
		}

		device.setCreateTime(LocalDateTime.now());
		device.setUpdateTime(LocalDateTime.now());
		device.setId(UUID.randomUUID().toString().replace("-",""));
		log.info("注册设备{}",device);
		deviceMapper.inst(device);

		return device;
	}

	@Override
	public ListDevice list(String jwt) {
		//创建列表
		ListDevice listDevice = new ListDevice();

		//解析jwt 获取使用者id
		Claims claims = JwtUtils.parseJWT(jwt);
		String uid = (String) claims.get("id");

		//根据用户id获取 该用户的设备计数
		listDevice.setTotal(deviceMapper.getTotalByUserId(uid));
		//根据用户id获取 该用户的设备列表
		listDevice.setRows(deviceMapper.listByUserId(uid));

//		log.info(listDevice.getRows().toString());
		return listDevice;
	}

	@Override
	public Device getById(String did, HttpServletRequest request) {
		String jwt = request.getHeader("Authorization");
		Claims claims = JwtUtils.parseJWT(jwt);

		String uid = (String) claims.get("id");

		Device device = deviceMapper.getById(did);

		if (device.getUserId().equals(uid)){
			return device;
		}else {
			//todo 越權記錄
			String rTEM = "查询其他用户设备" +"操作者"+uid+"设备"+device;
			throw new RuntimeException(rTEM,new RuntimeException("使用者接口越權"));
		}
	}

	@Override
	public Device login(Device device) {
		log.info("设备登入请求 设备id:{}",device.getId());
//		log.info("device:{}",device);
		Device d = deviceMapper.getByIdAndMacAddress(device);
//		log.info("d:{}",d);

		if (d !=null){
			//登入成功 返回jwt
			Map<String,Object> claims = new HashMap<>();
			claims.put("id",d.getId());
			claims.put("name",d.getName());

			String jwt = JwtUtils.generateJWT(claims);
			d.setJwt(jwt);
			log.info("登入成功 {}",d.getId());
			return d;
		}else {
			log.info("登入失敗 {}",device.getId());
			return null;
		}
	}

	@Override
	public Boolean modifyDeviceInfo(Device device, HttpServletRequest request) {
		log.info("id:{}",device.getId());
		log.info("name:{}",device.getName());
		//1.确认输入
		if(device.getId()==null||device.getId().isBlank()||device.getName()==null||device.getName().isBlank()){
			throw new RuntimeException("输入不合法");
		}

		//2 验证是否修改自己的设备
		//2.1 获取uid
		String jwt = request.getHeader("Authorization");
		Claims claims = JwtUtils.parseJWT(jwt);
		String uid = (String) claims.get("id");
		//2.2 获取欲修改设备
		Device deviceCheck = deviceMapper.getById(device.getId());
		//2.3 判断欲修改设备是否为该使用者的
		if (!deviceCheck.getUserId().equals(uid)){
			//不相等
			log.warn("尝试修改他人装置");
			//todo 越權記錄
			String rTEM = "尝试修改他人装置" +"操作者"+uid+"设备"+deviceCheck;
			throw new RuntimeException(rTEM,new RuntimeException("使用者接口越權"));
//			return false;
		}

		//3.添加信息
		device.setUpdateTime(LocalDateTime.now());
		//4.修改数据库
		return deviceMapper.updateById(device);
	}

	/**
	 * 用户添加设备
	 *
	 * @param request
	 * @param device
	 * @return
	 */
	@Override
	public String addDevice(HttpServletRequest request, Device device) {
		//获取用户id
		String jwt = request.getHeader("Authorization");
		Claims claims = JwtUtils.parseJWT(jwt);
		String uid = (String) claims.get("id");
		String username = (String) claims.get("username");

		//獲取目標設備
		Device checkDevice = deviceMapper.getById(device.getId());

		//確認目標設備是否存在
		if (checkDevice==null){
			log.info("目標設備不存在");
			return "目標設備不存在";
		}

		//确认目标设备没有使用者
		if (checkDevice.getUserId()!=null){
			log.warn("设备已有主人 设备ID:{} 主人id:{} 操作者id:{}",checkDevice.getId(),checkDevice.getUserId(),uid);
			return "失敗,設備已被添加過";
		}

		//添加信息
			device.setUserId(uid);
			device.setUpdateTime(LocalDateTime.now());
			device.setName(username+"的新設備");

		//寫數據庫
			deviceMapper.updateById(device);

		return "success";
	}
}
