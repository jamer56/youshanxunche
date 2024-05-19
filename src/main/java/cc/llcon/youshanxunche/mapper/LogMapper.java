package cc.llcon.youshanxunche.mapper;

import cc.llcon.youshanxunche.pojo.ErrorLog;
import cc.llcon.youshanxunche.pojo.OperateLog;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LogMapper {
    List<String> getListOperateLogClass();

    List<String> getOperateLogMethodListByClassName(String classname);

    List<OperateLog> getListOperateLog(String className, String methodName, LocalDateTime begin, LocalDateTime end);

    List<String> getListSelectLogClass();

    List<String> getSelectLogMethodListByClassName(String classname);

    List<OperateLog> getListSelectLog(String className, String methodName, LocalDateTime begin, LocalDateTime end);

    List<String> getErrorLogClassificationList();

    List<ErrorLog> getListErrorLog(String classification, LocalDateTime begin, LocalDateTime end);
}
