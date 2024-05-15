package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.mapper.DeviceMapper;
import cc.llcon.youshanxunche.mapper.PosMapper;
import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.ListPos;
import cc.llcon.youshanxunche.pojo.Pos;
import cc.llcon.youshanxunche.pojo.PosParam;
import cc.llcon.youshanxunche.service.PosService;
import cc.llcon.youshanxunche.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PosServiceImpl implements PosService {

    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    PosMapper posMapper;

    /**
     * 獲取最後定位資訊
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
        if (deviceCheck ==null){
            throw new RuntimeException("查無設備");
        }
        //1.3 判斷
        if (!deviceCheck.getUserId().equals(uid)){
            //存取他人设备
            //todo 记录操作日志
            log.error("越權存取:{} 設備{} 持有人{}" ,uid,deviceCheck.getId(),deviceCheck.getUserId());
            return null;
        }

        //查詢記錄
        Pos pos = posMapper.getLatestById(dId);
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
        if (!checkDevice.getUserId().equals(uid)){
            //todo 越權記錄
            log.error("查詢越權");
            return null;
        }
        //查詢
        ListPos listPos =new ListPos();
        listPos.setPosList(posMapper.getListByDIDAndTime(posParam));
        listPos.setTotal(posMapper.getTotal(posParam));
        //返回
        return listPos;
    }
}





