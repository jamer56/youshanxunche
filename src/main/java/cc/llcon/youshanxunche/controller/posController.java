package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.anno.OperateLog;
import cc.llcon.youshanxunche.anno.SelectLog;
import cc.llcon.youshanxunche.pojo.ListPos;
import cc.llcon.youshanxunche.pojo.Pos;
import cc.llcon.youshanxunche.pojo.PosParam;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.service.PosService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/pos")
@RestController
@Slf4j
public class posController {

    @Autowired
    PosService posService;

    /**
     * 獲取最後定位資訊
     *
     * @param uuid
     * @param request
     * @return
     */
    @SelectLog
    @GetMapping("/{uuid}/latest")
    public Result latest(@PathVariable String uuid, HttpServletRequest request) {
        Pos pos = posService.latest(uuid, request);
        if (pos == null) {
            return Result.error("查無記錄");
        }
        return Result.success(pos);
    }

    /**
     * 通过 '时间' 和 'deviceid' 查询 定位資訊
     */
    @SelectLog
    @GetMapping("/{dID}")
    public Result list(@PathVariable() String dID, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") LocalDateTime begin, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") LocalDateTime end, HttpServletRequest request) {
        //包裝參數
        PosParam posParam = new PosParam(dID, begin, end);

        ListPos listPos = posService.list(posParam, request);
        if (listPos == null || listPos.getTotal() == 0) {
            return Result.error("查無結果");
        }
        return Result.success(listPos);
    }

    /**
     * 新增记录
     * @param pos
     * @param request
     * @return
     */
//    @OperateLog
    @PostMapping
    public Result ins(@RequestBody Pos pos, HttpServletRequest request) {
//        log.info("新增数据:{}",pos);
        log.info("新增定位数据");

        String result = posService.ins(pos, request);
        if (result.equals("success")) {
            return Result.success();
        }
        return Result.error(result);
    }
}

