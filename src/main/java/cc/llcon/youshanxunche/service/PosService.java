package cc.llcon.youshanxunche.service;

import cc.llcon.youshanxunche.pojo.ListPos;
import cc.llcon.youshanxunche.pojo.Pos;
import cc.llcon.youshanxunche.pojo.PosParam;

public interface PosService {
    /**
     * 获取最后定位資訊
     *
     * @param uuid
     * @return
     */
    Pos latest(String uuid);

    /**
     * 通过 '时间' 和 'deviceid' 查询 定位資訊
     */
    ListPos list(PosParam posParam);

    /**
     * 新增定位資訊
     *
     * @param pos
     * @return
     */
    String ins(Pos pos);
}
