package cc.llcon.youshanxunche.service.impl;

import cc.llcon.youshanxunche.mapper.LogMapper;
import cc.llcon.youshanxunche.mapper.UserMapper;
import cc.llcon.youshanxunche.pojo.*;
import cc.llcon.youshanxunche.service.LogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class LogServiceImpl implements LogService {
    final LogMapper logMapper;
    final UserMapper userMapper;

    public LogServiceImpl(LogMapper logMapper, UserMapper userMapper) {
        this.logMapper = logMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<String> listOperateLogClass() {
        List<String> list = logMapper.getListOperateLogClass();
        return list;
    }

    @Override
    public List<String> getOperateLogMethodListByClassName(String classname) {
        List<String> list = logMapper.getOperateLogMethodListByClassName(classname);
        return list;
    }

    @Override
    public ListOperateLog getOperateLogList(ListLogParam param) {
        //判断是否有传入operator参数

        User user = null;

        if (param.getOperator() != null && !param.getOperator().isBlank()) {
            user = userMapper.getByUsername(param.getOperator());
            if (user == null) {
                try {
                    user = userMapper.getById(param.getOperator());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (user == null) {
                return null;
            } else {
                param.setOperator(user.getId());
            }
        }

        PageHelper.startPage(param.getPage(), param.getPageSize());

        List<OperateLog> list = logMapper.getListOperateLog(param);

        Page<OperateLog> p = (Page<OperateLog>) list;

        ListOperateLog listOperateLog = new ListOperateLog();
        listOperateLog.setRows(p.getResult());
        listOperateLog.setTotal(p.getTotal());

        return listOperateLog;
    }

    @Override
    public List<String> listSelectLogClass() {
        List<String> list = logMapper.getListSelectLogClass();
        return list;
    }

    @Override
    public List<String> getSelectLogMethodListByClassName(String classname) {
        List<String> list = logMapper.getSelectLogMethodListByClassName(classname);
        return list;
    }

    @Override
    public ListOperateLog getSelectLogList(Integer page, Integer pageSize, String className, String methodName, LocalDateTime begin, LocalDateTime end) {
        PageHelper.startPage(page, pageSize);

        List<OperateLog> list = logMapper.getListSelectLog(className, methodName, begin, end);
        Page<OperateLog> p = (Page<OperateLog>) list;

        ListOperateLog listOperateLog = new ListOperateLog();
        listOperateLog.setRows(p.getResult());
        listOperateLog.setTotal(p.getTotal());

        return listOperateLog;
    }

    @Override
    public List<String> ErrorLogClassificationList() {
        List<String> list = logMapper.getErrorLogClassificationList();
        return list;
    }

    @Override
    public ListErrorLog listErrorLog(ListLogParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());

        List<ErrorLog> list = logMapper.getListErrorLog(param.getClassification(), param.getBegin(), param.getEnd());
        Page<ErrorLog> p = (Page<ErrorLog>) list;

        ListErrorLog listOperateLog = new ListErrorLog();
        listOperateLog.setRows(p.getResult());
        listOperateLog.setTotal(p.getTotal());

        return listOperateLog;

    }

    @Override
    public ListLoginLog listLoginLog(ListLogParam param) {

        //判断传入值是否为uuid
        String userName = param.getUserName();
        try {
            param.setUserName(userMapper.getById(userName).getUsername());
        } catch (Exception e) {
            //忽略
        }
        PageHelper.startPage(param.getPage(), param.getPageSize());

        List<LoginLogPojo> list = logMapper.getLoginLog(param);
        Page<LoginLogPojo> p = (Page<LoginLogPojo>) list;
        ListLoginLog loginLog = new ListLoginLog();
        loginLog.setTotal(p.getTotal());
        loginLog.setRows(p.getResult());
        return loginLog;
    }
}
