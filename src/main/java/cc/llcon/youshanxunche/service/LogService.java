package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.pojo.ListOperateLog;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {

    List<String> listOperateLogClass();

    List<String> getOperateLogMethodListByClassName(String classname);

    ListOperateLog getOperateLogList(Integer page, Integer pageSize, String classname, String method, LocalDateTime begin, LocalDateTime end);
}
