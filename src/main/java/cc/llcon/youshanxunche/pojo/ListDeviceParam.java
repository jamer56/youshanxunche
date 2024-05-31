package cc.llcon.youshanxunche.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListDeviceParam extends Device {
    private Integer page = 1;
    private Integer pageSize = 10;
}
