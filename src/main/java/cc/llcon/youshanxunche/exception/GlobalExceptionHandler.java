package cc.llcon.youshanxunche.exception;

import cc.llcon.youshanxunche.mapper.OperateLogMapper;
import cc.llcon.youshanxunche.pojo.ErrorLog;
import cc.llcon.youshanxunche.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    OperateLogMapper logMapper;

    @ExceptionHandler(Exception.class)
    public Result ex(Exception ex) {
        //记录错误日志
        String classification = null;

        Throwable exCause = ex.getCause();
        if (exCause != null) {
            classification = exCause.getMessage();
        }

        ErrorLog errorLog = new ErrorLog(null, LocalDateTime.now(), classification, ex.getMessage(), ex.getStackTrace()[0].toString(), Arrays.toString(ex.getStackTrace()));

        log.error("記錄錯誤:{}", errorLog);
        logMapper.insertErrorLog(errorLog);

//        String message = ex.getCause().getMessage();
//        log.error(message);
//        ex.getCause().printStackTrace();

//		log.error(ex.getMessage());
//      String exstack=ex.getStackTrace()[0].toString();
//		log.error(exstack);
        ex.printStackTrace();
        return Result.error("操作失敗,請聯絡管理員");
    }
}
