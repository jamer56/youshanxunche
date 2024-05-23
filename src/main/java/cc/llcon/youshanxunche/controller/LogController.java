package cc.llcon.youshanxunche.controller;

import cc.llcon.youshanxunche.anno.SecurityAuth;
import cc.llcon.youshanxunche.anno.SelectLog;
import cc.llcon.youshanxunche.pojo.*;
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
    @SelectLog
    @GetMapping("/operate/listclass")
    public Result getOperateLogClassList() {
        List<String> list = logService.listOperateLogClass();
        return Result.success(list);
    }

    /**
     * 获取 操作日志 方法名by类名
     *
     * @param classname
     * @return
     */
    @SecurityAuth
    @SelectLog
    @GetMapping("/operate/listmethod/{classname}")
    public Result getOperateLogMethodListbyClassName(@PathVariable String classname) {
        List<String> list = logService.getOperateLogMethodListByClassName(classname);
        return Result.success(list);
    }

    /**
     * 获取操作日志
     */
    @SecurityAuth
    @SelectLog
    @GetMapping("/operate/list")
    public Result listOperateLog(ListLogParam param) {
        log.info(param.toString());

        ListOperateLog operateLogs = logService.getOperateLogList(param.getPage(), param.getPageSize(), param.getClassName(), param.getMethodName(), param.getBegin(), param.getEnd());
        return Result.success(operateLogs);
    }

    /**
     * 獲取 查询日志 类(class) 列表
     */
    @SecurityAuth
    @SelectLog
    @GetMapping("/select/listclass")
    public Result getSelectLogClassList() {
        List<String> list = logService.listSelectLogClass();
        return Result.success(list);
    }

    /**
     * 获取 查询日志 方法名by类名
     *
     * @param classname
     * @return
     */
    @SecurityAuth
    @SelectLog
    @GetMapping("/select/listmethod/{classname}")
    public Result getSelectLogMethodListByClassName(@PathVariable String classname) {
        List<String> list = logService.getSelectLogMethodListByClassName(classname);
        return Result.success(list);
    }

    /**
     * 获取 查询日志
     */
    @SecurityAuth
    @SelectLog
    @GetMapping("/select/list")
    public Result listSelectLog(ListLogParam param) {
        log.info(param.toString());

        ListOperateLog operateLogs = logService.getSelectLogList(param.getPage(), param.getPageSize(), param.getClassName(), param.getMethodName(), param.getBegin(), param.getEnd());
        return Result.success(operateLogs);
    }

    //获取错误日志

    /**
     * 获取 错误日志 分类列表
     * @return
     */
    @SecurityAuth
    @SelectLog
    @GetMapping("/error/listclassification")
    public Result getErrorLogClassificationList() {
        List<String> list = logService.ErrorLogClassificationList();
        return Result.success(list);
    }

    /**
     * 獲取錯誤日誌
     * @param param
     * @return
     */
    @SecurityAuth
    @SelectLog
    @GetMapping("/error/listerrorlog")
    public Result listErrorLog(ListLogParam param) {
        ListErrorLog listErrorLog = logService.listErrorLog(param);
        return Result.success(listErrorLog);
    }

    /**
     * 獲取登入日誌
     * @param param
     * @return
     */
    @SecurityAuth
    @SelectLog
    @GetMapping("/linlog/listlinlog")
    public Result listLoginLog(ListLogParam param) {
        ListLoginLog loginLog = logService.listLoginLog(param);
        return Result.success(loginLog);
    }
}
