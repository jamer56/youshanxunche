package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.mapper.DeviceMapper;
import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListDevice;
import cc.llcon.youshanxunche.service.DeviceService;
import cc.llcon.youshanxunche.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
	public ListDevice list() {

		ListDevice listDevice = new ListDevice();

		listDevice.setTotal(deviceMapper.getTotal());


		listDevice.setRows(deviceMapper.list());

		log.info(listDevice.getRows().toString());
		return listDevice;
	}

	@Override
	public Device getById(String id) {
		Device device = deviceMapper.getById(id);
		return device;
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
			log.info("登入成功 {}",device.getId());
			return null;
		}
	}

	@Override
	public List<Integer> listSensor(String id) {
		List<Integer> sensorList = deviceMapper.listSensor(id);

		return sensorList;
	}

	@Override
	public Boolean modifyDeviceInfo(Device device) {

		log.info("id:{}",device.getId());
		log.info("name:{}",device.getName());
		//1.确认输入
		if(device.getId()==null||device.getId().isBlank()||device.getName()==null||device.getName().isBlank()){
			throw new RuntimeException("输入不合法");
		}
		//2.添加信息
		device.setUpdateTime(LocalDateTime.now());
		//3.修改数据库
		return deviceMapper.updateById(device);
	}
}
