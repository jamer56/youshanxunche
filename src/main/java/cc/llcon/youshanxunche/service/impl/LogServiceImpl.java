package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.mapper.LogMapper;
import cc.llcon.youshanxunche.pojo.ListOperateLog;
import cc.llcon.youshanxunche.pojo.OperateLog;
import cc.llcon.youshanxunche.service.LogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogMapper logMapper;

    @Override
    public List<String> listOperateLogClass() {
        List<String> list= logMapper.getListOperateLogClass();
        return list;
    }

    @Override
    public List<String> getOperateLogMethodListByClassName(String classname) {
        List<String> list=logMapper.getOperateLogMethodListByClassName(classname);
        return list;
    }

    @Override
    public ListOperateLog getOperateLogList(Integer page, Integer pageSize, String classname, String method, LocalDateTime begin, LocalDateTime end) {
        PageHelper.startPage(page,pageSize);

        List<OperateLog> list =logMapper.getListOperateLog(classname,method,begin,end);
        Page<OperateLog> p = (Page<OperateLog>) list;

        ListOperateLog listOperateLog = new ListOperateLog();
        listOperateLog.setRows(p.getResult());
        listOperateLog.setTotal(p.getTotal());

        return listOperateLog;
    }

    @Override
    public List<String> listSelectLogClass() {
        List<String> list= logMapper.getListSelectLogClass();
        return list;
    }

    @Override
    public List<String> getSelectLogMethodListByClassName(String classname) {
        List<String> list=logMapper.getSelectLogMethodListByClassName(classname);
        return list;
    }

    @Override
    public ListOperateLog getSelectLogList(Integer page, Integer pageSize, String className, String methodName, LocalDateTime begin, LocalDateTime end) {
        PageHelper.startPage(page,pageSize);

        List<OperateLog> list =logMapper.getListSelectLog(className,methodName,begin,end);
        Page<OperateLog> p = (Page<OperateLog>) list;

        ListOperateLog listOperateLog = new ListOperateLog();
        listOperateLog.setRows(p.getResult());
        listOperateLog.setTotal(p.getTotal());

        return listOperateLog;
    }
}
