package cc.llcon.youshanxunche.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
	private String id;
	private String name;
	private String userId;
	private String macAddress;
	private String comment;
	private String jwt;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private LocalDateTime lastRecodeTime;
}
