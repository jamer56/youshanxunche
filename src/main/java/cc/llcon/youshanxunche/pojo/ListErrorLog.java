package cc.llcon.youshanxunche.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListErrorLog {
    private Long total;
    private List<ErrorLog> rows;
}
