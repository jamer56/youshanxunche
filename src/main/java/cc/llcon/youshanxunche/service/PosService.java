package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.pojo.Pos;
import jakarta.servlet.http.HttpServletRequest;

public interface PosService {
    /**
     * 获取最后定位資訊
     * @param uuid
     * @param request
     * @return
     */
    Pos latest(String uuid, HttpServletRequest request);
}
