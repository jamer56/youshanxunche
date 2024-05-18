package cc.llcon.youshanxunche.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOperateLog {
    private Long total;
    private List<OperateLog> rows;
}
