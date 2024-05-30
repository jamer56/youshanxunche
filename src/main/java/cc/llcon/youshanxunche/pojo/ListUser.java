package cc.llcon.youshanxunche.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUser {
    private Long total;
    private List<User> rows;
}
