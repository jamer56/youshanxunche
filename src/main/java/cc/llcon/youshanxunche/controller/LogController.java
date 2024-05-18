package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.anno.SecurityAuth;
import cc.llcon.youshanxunche.pojo.ListOperateLog;
import cc.llcon.youshanxunche.pojo.OperateLogListParam;
import cc.llcon.youshanxunche.pojo.Result;
import cc.llcon.youshanxunche.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogService logService;

    /**
     * 獲取 操作日志 类(class) 列表
     */
    @SecurityAuth
    @GetMapping("/operate/listclass ")
    public Result getOperateLogClassList() {
        List<String> list = logService.listOperateLogClass();
        return Result.success(list);
    }

    /**
     * 获取方法名by类名
     *
     * @param classname
     * @return
     */
    @SecurityAuth
    @GetMapping("/operate/listmethod/{classname}")
    public Result getOperateLogMethodListbyClassName(@PathVariable String classname) {
        List<String> list = logService.getOperateLogMethodListByClassName(classname);
        return Result.success(list);
    }

    /**
     * 获取操作日志
     */

    @SecurityAuth
    @GetMapping("/operate/list")
    public Result listOperateLog(OperateLogListParam param) {
        log.info(param.toString());

        ListOperateLog operateLogs = logService.getOperateLogList(param.getPage(), param.getPagesize(), param.getClassName(), param.getMethodName(), param.getBegin(), param.getEnd());
        return Result.success(operateLogs);
    }


/**
 * 获取查询日志
 */


/**
 * 获取错误日志
 */
}
