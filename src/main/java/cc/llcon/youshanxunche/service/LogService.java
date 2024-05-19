package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.pojo.ListErrorLog;
import cc.llcon.youshanxunche.pojo.ListOperateLog;
import cc.llcon.youshanxunche.pojo.OperateLogListParam;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {

    List<String> listOperateLogClass();

    List<String> getOperateLogMethodListByClassName(String classname);

    ListOperateLog getOperateLogList(Integer page, Integer pageSize, String classname, String method, LocalDateTime begin, LocalDateTime end);

    List<String> listSelectLogClass();

    List<String> getSelectLogMethodListByClassName(String classname);

    ListOperateLog getSelectLogList(Integer page, Integer pageSize, String className, String methodName, LocalDateTime begin, LocalDateTime end);

    List<String> ErrorLogClassificationList();

    ListErrorLog listErrorLog(OperateLogListParam param);
}
