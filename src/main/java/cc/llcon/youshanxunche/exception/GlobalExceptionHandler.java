package cc.llcon.youshanxunche.exception;

import cc.llcon.youshanxunche.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public Result ex(Exception ex){
		ex.printStackTrace();
		return Result.error("操作失敗,請聯絡管理員");
	}
}
