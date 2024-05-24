package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.mapper.DeviceMapper;
import cc.llcon.youshanxunche.mapper.PosMapper;
import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListPos;
import cc.llcon.youshanxunche.pojo.Pos;
import cc.llcon.youshanxunche.pojo.PosParam;
import cc.llcon.youshanxunche.service.PosService;
import cc.llcon.youshanxunche.utils.AuthUtils;
import cc.llcon.youshanxunche.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PosServiceImpl implements PosService {

    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    PosMapper posMapper;

    /**
     * 獲取最後定位資訊
     *
     * @param dId
     * @param request
     * @return
     */
    @Override
    public Pos latest(String dId, HttpServletRequest request) {

        //1 验证是否修改自己的设备
        //1.1 获取uid
        String uid = AuthUtils.getUID(request);
        //1.2 获取欲查询的设备
        Device deviceCheck = deviceMapper.getById(dId);
        if (deviceCheck == null) {
            throw new RuntimeException("查無設備");
        }
        //1.3 判斷
        if (!deviceCheck.getUserId().equals(uid)) {
            //存取他人设备
            log.error("越權存取:{} 設備{} 持有人{}", uid, deviceCheck.getId(), deviceCheck.getUserId());

            //todo 越權記錄
            String rTEM = "獲取其他用户最後定位資訊" +"操作者"+uid+"设备"+deviceCheck;
            throw new RuntimeException(rTEM,new RuntimeException("使用者接口越權"));
//            return null;
        }

        //查詢記錄
        Pos pos = posMapper.getLatestById(dId);
        if(pos == null){
            return null;
        }

        //清除不必要數據
        pos.setDeviceId(null);
        return pos;
    }

    /**
     * 通过 '时间' 和 'deviceid' 查询 定位資訊
     */
    @Override
    public ListPos list(PosParam posParam, HttpServletRequest request) {
        //判斷參數
        if (!posParam.getBegin().isBefore(posParam.getEnd())) {
            return null;
        }
        //確認查詢是否越權
        String uid = AuthUtils.getUID(request);
        Device checkDevice = deviceMapper.getById(posParam.getDID());
        if (checkDevice == null) {
            log.error("查無確認設備");
            return null;
        }
        if (!checkDevice.getUserId().equals(uid)) {
            //todo 越權記錄
            log.error("查詢越權");
            String rTEM = "查询其他用户设备" +"操作者"+uid+"设备"+checkDevice;
            throw new RuntimeException(rTEM,new RuntimeException("使用者接口越權"));
        }
        //查詢
        ListPos listPos = new ListPos();
        listPos.setPosList(posMapper.getListByDIDAndTime(posParam));
        listPos.setTotal(posMapper.getTotal(posParam));
        //返回
        return listPos;
    }

    @Override
    public String ins(Pos pos, HttpServletRequest request) {
        //1. 驗證輸入
        //1.1 驗證空
        if (pos.getLatitude() == null ||
                pos.getLatitudeDir() == null ||
                pos.getLongitude() == null ||
                pos.getLongitudeDir() == null ||
                pos.getSats() == null) {
            return "参数不完整";
        }
        //1.2 驗證範圍
        //1.2.1 经度
        if (pos.getLongitude().doubleValue()>180||pos.getLongitude().doubleValue()<-180){
            //经度不合法
            return "经度不合法";
        }
        //1.2.2 纬度
        if (pos.getLatitude().doubleValue()>90||pos.getLatitude().doubleValue()<-90){
            //经度不合法
            return "纬度不合法";
        }
        //1.2.3 经度方向
        if (!(pos.getLongitudeDir().equals("E")||pos.getLongitudeDir().equals("W"))){
            return "经度方向不合法";
        }
        //1.2.4 纬度方向
        if (!(pos.getLatitudeDir().equals("S")||pos.getLatitudeDir().equals("N"))){
            return "纬度方向不合法";
        }
        //2.新增信息
        String jwt = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseJWT(jwt);
        String dID = (String) claims.get("id");

        //2.1 验证设备是否存在
        Device deviceCheck = deviceMapper.getById(dID);
        if (deviceCheck==null){
            log.info("设备不存在");
            return "设备不存在";
        }

        //2.2 新增信息
        pos.setDeviceId(dID);
        pos.setCreateTime(LocalDateTime.now());

        //3. 新增記錄
        Integer insertedRow = posMapper.ins(pos);
        if (insertedRow == 1){
            return "success";
        }
        return "數據新增失败";
    }
}





