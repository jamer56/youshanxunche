package cc.llcon.youshanxunche.exception;

import cc.llcon.youshanxunche.mapper.OperateLogMapper;
import cc.llcon.youshanxunche.pojo.ErrorLog;
import cc.llcon.youshanxunche.pojo.Result;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    OperateLogMapper logMapper;

    @ExceptionHandler(Exception.class)
    public Result ex(Exception ex) {
        //记录错误日志
        ErrorLog errorLog = new ErrorLog(null, LocalDateTime.now(), ex.getMessage(), ex.getStackTrace()[0].toString(), Arrays.toString(ex.getStackTrace()));
        logMapper.insertErrorLog(errorLog);

//		log.error(ex.getMessage());
//      String exstack=ex.getStackTrace()[0].toString();
//		log.error(exstack);
//		ex.printStackTrace();
        return Result.error("操作失敗,請聯絡管理員");
    }
}
