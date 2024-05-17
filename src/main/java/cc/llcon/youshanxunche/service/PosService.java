package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.pojo.ListPos;
import cc.llcon.youshanxunche.pojo.Pos;
import cc.llcon.youshanxunche.pojo.PosParam;
import jakarta.servlet.http.HttpServletRequest;

public interface PosService {
    /**
     * 获取最后定位資訊
     * @param uuid
     * @param request
     * @return
     */
    Pos latest(String uuid, HttpServletRequest request);
    /**
     * 通过 '时间' 和 'deviceid' 查询 定位資訊
     */
    ListPos list(PosParam posParam, HttpServletRequest request);

    String ins(Pos pos, HttpServletRequest request);
}
