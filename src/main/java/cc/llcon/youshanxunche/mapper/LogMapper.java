package cc.llcon.youshanxunche.mapper;

import cc.llcon.youshanxunche.pojo.OperateLog;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LogMapper {
    List<String> getListOperateLogClass();

    List<String> getOperateLogMethodListByClassName(String classname);

    List<OperateLog> getListOperateLog(String classname, String method, LocalDateTime begin, LocalDateTime end);
}
