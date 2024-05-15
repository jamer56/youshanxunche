package cc.llcon.youshanxunche.mapper;

import cc.llcon.youshanxunche.pojo.Pos;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PosMapper {

    Pos getLatestById(String dId);
}
