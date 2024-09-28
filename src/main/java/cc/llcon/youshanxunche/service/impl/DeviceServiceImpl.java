package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.controller.request.AddDeviceRequest;
import cc.llcon.youshanxunche.controller.request.DeviceLoginRequest;
import cc.llcon.youshanxunche.controller.request.ModifyDeviceInfoRequest;
import cc.llcon.youshanxunche.controller.vo.DeviceLoginVO;
import cc.llcon.youshanxunche.mapper.DeviceMapper;
import cc.llcon.youshanxunche.mapper.PosMapper;
import cc.llcon.youshanxunche.pojo.DTO.DeviceLoginDTO;
import cc.llcon.youshanxunche.pojo.DTO.ModifyDeviceInfoDTO;
import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListDevice;
import cc.llcon.youshanxunche.pojo.ListDeviceParam;
import cc.llcon.youshanxunche.service.DeviceService;
import cc.llcon.youshanxunche.utils.AuthUtils;
import cc.llcon.youshanxunche.utils.JwtUtils;
import cc.llcon.youshanxunche.utils.MacAddressUtils;
import cc.llcon.youshanxunche.utils.UUIDUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {
    final DeviceMapper deviceMapper;
    final HttpServletRequest request;
    final PosMapper posMapper;

    public DeviceServiceImpl(DeviceMapper deviceMapper, HttpServletRequest request, PosMapper posMapper) {
        this.deviceMapper = deviceMapper;
        this.request = request;
        this.posMapper = posMapper;
    }

    @Override
    public Device register(Device device) {
        if (device.getMacAddress() == null) {
            return null;
        }

        device.setCreateTime(LocalDateTime.now());
        device.setUpdateTime(LocalDateTime.now());
        device.setId(UUID.randomUUID().toString().replace("-", ""));
        log.info("注册设备{}", device);
        deviceMapper.inst(device);

        return device;
    }

    @Override
    public ListDevice list() {
        //创建列表
        ListDevice listDevice = new ListDevice();

        //解析jwt 获取使用者id
        String jwt = request.getHeader("Authorization");
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
    public Device getById(String did) {
        String jwt = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseJWT(jwt);
        String uid = (String) claims.get("id");

        Device device = deviceMapper.getById(did);

        if (device.getUserId().equals(uid)) {
            return device;
        } else {
            String rTEM = "查询其他用户设备" + "操作者" + uid + "设备" + device;
            throw new RuntimeException(rTEM, new RuntimeException("使用者接口越權"));
        }
    }

    @Override
    public DeviceLoginVO login(DeviceLoginRequest device) {
        // 记录设备登录请求的日志
        log.info("设备登入请求 设备id:{}", device.getId());

        // 将设备ID转换为字节数组
        byte[] dID = UUIDUtils.UUIDtoBytes(device.getId());
        // 将MAC地址转换为字节数组
        byte[] macAddress = MacAddressUtils.toBytes(device.getMacAddress());

        // 创建设备登录DTO，包含设备ID和MAC地址的字节数组形式
        DeviceLoginDTO deviceLoginDTO = new DeviceLoginDTO(dID, macAddress);

        // 根据设备ID和MAC地址查询设备信息
        Device d = deviceMapper.getByIdAndMacAddress(deviceLoginDTO);

        if (d != null) {
            // 设备存在，生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", d.getId());
            claims.put("name", d.getName());

            // 使用claims生成JWT令牌
            String jwt = JwtUtils.generateJWT(claims);

            // 创建并返回设备登录VO，包含生成的JWT令牌
            DeviceLoginVO deviceLoginVO = new DeviceLoginVO(jwt);
            log.info("登入成功 {}", d.getId());
            return deviceLoginVO;
        } else {
            // 设备不存在，登录失败
            log.info("登入失敗 {}", device.getId());
            return null;
        }
    }

    @Override
    public Boolean modifyDeviceInfo(ModifyDeviceInfoRequest device) {
        log.info("id:{}", device.getId());
        log.info("name:{}", device.getName());
        //1.确认输入
        if (device.getId() == null || device.getId().isBlank() || device.getName() == null || device.getName().isBlank()) {
            throw new IllegalArgumentException("输入不合法");
        }

        //2 验证是否修改自己的设备
        //2.1 获取uid
        String jwt = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseJWT(jwt);
        String uid = (String) claims.get("id");
        //2.2 获取欲修改设备
        Device deviceCheck = deviceMapper.getById(device.getId());
        //2.3 判断欲修改设备是否为该使用者的
        if (!deviceCheck.getUserId().equals(uid)) {
            //不相等
            log.warn("尝试修改他人装置");
            String rTEM = "尝试修改他人装置" + "操作者" + uid + "设备" + deviceCheck;
            throw new RuntimeException(rTEM, new RuntimeException("使用者接口越權"));
        }

        //3.封装修改信息
        ModifyDeviceInfoDTO deviceDTO = new ModifyDeviceInfoDTO(null, null, device.getName(), device.getComment(), null);
        deviceDTO.setId(UUIDUtils.UUIDtoBytes(device.getId()));
        deviceDTO.setUserId(UUIDUtils.UUIDtoBytes(deviceCheck.getUserId()));
        deviceDTO.setUpdateTime(LocalDateTime.now());

        //4.修改数据库
        return deviceMapper.updateById(deviceDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addDevice(AddDeviceRequest device) {
        //获取用户id
        String jwt = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseJWT(jwt);
        String uid = (String) claims.get("id");
        String username = (String) claims.get("username");

        //獲取目標設備
        Device checkDevice = deviceMapper.getById(device.getId());

        //確認目標設備是否存在
        if (checkDevice == null) {
            log.info("目標設備不存在");
            return "目標設備不存在";
        }

        //确认目标设备没有使用者
        if (checkDevice.getUserId() != null) {
            log.warn("设备已有主人 设备ID:{} 主人id:{} 操作者id:{}", checkDevice.getId(), checkDevice.getUserId(), uid);
            return "失敗,設備已被添加過";
        }

        //添加信息
//        AddDeviceDTO deviceDTO = new AddDeviceDTO(UUIDUtils.UUIDtoBytes(device.getId()), UUIDUtils.UUIDtoBytes(uid), null, null, LocalDateTime.now());
//        deviceDTO.setName(username + "的新設備");
//        deviceDTO.setComment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 添加的新裝置");

        ModifyDeviceInfoDTO modifyDeviceInfoDTO = new ModifyDeviceInfoDTO(UUIDUtils.UUIDtoBytes(device.getId()), UUIDUtils.UUIDtoBytes(uid), null, null, LocalDateTime.now());
        modifyDeviceInfoDTO.setName("新設備");
        modifyDeviceInfoDTO.setComment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 添加的新裝置");

        // 清除定位資訊
        posMapper.delByDID(device.getId());

        // 添加設備
        Boolean updateResult = deviceMapper.updateById(modifyDeviceInfoDTO);

        if (updateResult) {
            return "success";
        } else {
            return "db error";
        }
    }

    @Override
    public ListDevice listAllByParam(ListDeviceParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Device> list = deviceMapper.list(param);
        Page<Device> page = (Page<Device>) list;

        ListDevice listDevice = new ListDevice();
        listDevice.setTotal(page.getTotal());
        listDevice.setRows(page.getResult());

        return listDevice;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unLinkDevice(String dId) {
        String uid = AuthUtils.getUID(request);

        Device deviceByID = deviceMapper.getById(dId);
        // 判斷空
        Optional.ofNullable(deviceByID).orElseThrow(() -> new RuntimeException("裝置不存在"));

        // 判斷是否為擁有者
        if (deviceByID.getUserId() == null || !deviceByID.getUserId().equals(uid)) {
            log.warn("尝试解除他人装置 裝置id:{} 擁有者id:{}", dId, deviceByID.getUserId());
            String rTEM = "尝试解除空裝置或他人装置";
            throw new RuntimeException(rTEM, new RuntimeException("使用者接口越權"));
        }

        // 解除 用户---装置 绑定关系
        deviceByID.setUserId(null);
        deviceByID.setUpdateTime(LocalDateTime.now());
        ModifyDeviceInfoDTO modifyDeviceInfoDTO = new ModifyDeviceInfoDTO(deviceByID);
        deviceMapper.updateById(modifyDeviceInfoDTO);

        // 移除 定位資訊
        posMapper.delByDID(dId);

        return true;
    }
}
