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

	//todo 此处尚未处理 DeviceRegister
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
			throw new RuntimeException("查询其他用户设备或其他问题");
		}
	}

	//todo 此处尚未处理 DeviceLogin
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
			log.info("登入成功 {}",device.getId());
			return null;
		}
	}

	//todo 挑战设备描述 2024年4月29日05:29:09
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
			log.warn("有人尝试修改他人装置 或其他错误");
			return false;
		}

		//2.添加信息
		device.setUpdateTime(LocalDateTime.now());
		//3.修改数据库
		return deviceMapper.updateById(device);
	}
}
