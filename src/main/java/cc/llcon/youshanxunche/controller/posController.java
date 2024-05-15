package cc.llcon.youshanxunche.controller;


import cc.llcon.youshanxunche.pojo.ListPos;
import cc.llcon.youshanxunche.pojo.Pos;
import cc.llcon.youshanxunche.pojo.PosParam;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.service.PosService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/pos")
@RestController
@Slf4j
public class posController {

    @Autowired
    PosService posService;

    /**
     * 獲取最後定位資訊
     * @param uuid
     * @param request
     * @return
     */
    @GetMapping("/{uuid}/latest")
    public Result latest(@PathVariable String uuid, HttpServletRequest request) {
        Pos pos = posService.latest(uuid, request);
        if (pos == null) {
            return Result.error("无记录");
        }
        return Result.success(pos);
    }
    /**
     * 通过 '时间' 和 'deviceid' 查询 定位資訊
     */
    @GetMapping("/{dID}")
    public Result list(@PathVariable() String dID, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") LocalDateTime begin, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") LocalDateTime end,HttpServletRequest request) {
        //包裝參數
        PosParam posParam = new PosParam(dID,begin,end);

        ListPos listPos = posService.list(posParam,request);
        if (listPos == null){
            return Result.error("查詢失敗");
        }
        return Result.success(listPos);
    }
}

