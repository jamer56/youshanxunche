package cc.llcon.youshanxunche.mapper;

import cc.llcon.youshanxunche.pojo.Pos;
import cc.llcon.youshanxunche.pojo.PosParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PosMapper {
    /**
     * 使用did查詢最後一筆記錄
     * @param dId
     * @return
     */
    Pos getLatestById(String dId);

    /**
     * 查詢 日期區間 特定設備 的定位資訊
     *
     * @param posParam
     * @return
     */
    List<Pos> getListByDIDAndTime(PosParam posParam);

    /**
     * 獲取 日期區間 特定設備 的總記錄數
     * @param posParam
     * @return
     */
    Integer getTotal(PosParam posParam);
}
