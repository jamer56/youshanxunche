package cc.llcon.youshanxunche.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 列出所有设备 返回结果的封装类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListDevice {
	private Long total;
	private List<Device> rows;
}
