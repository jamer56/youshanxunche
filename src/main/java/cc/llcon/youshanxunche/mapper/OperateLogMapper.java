package cc.llcon.youshanxunche.mapper;

import cc.llcon.youshanxunche.pojo.ErrorLog;
import cc.llcon.youshanxunche.pojo.OperateLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateLogMapper {

    //插入操作日志数据
    void insertOperateLog(OperateLog log);

    void insertSelectLog(OperateLog operateLog);

    void insertLoginLog(OperateLog operateLog);

    void insertErrorLog(ErrorLog errorLog);
}
