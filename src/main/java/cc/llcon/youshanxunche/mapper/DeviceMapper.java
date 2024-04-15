package cc.llcon.youshanxunche.mapper;

import cc.llcon.youshanxunche.pojo.Device;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceMapper {

	/**
	 * 新增设备
	 * @param device
	 */
	public void inst(Device device);

	/**
	 * 查询所有设备
	 * @return
	 */
	List<Device> list();

	/**
	 * 取得设备数量
	 * @return
	 */
	Long getTotal();

	/**
	 * 根据id查询设备
	 * @param id
	 * @return
	 */
	Device getById(String id);

	/**
	 * 透过id和macaddress查询设备
	 * @param device
	 * @return
	 */
	Device getByIdAndMacAddress(Device device);

	/**
	 * 修改设备描述和名称
	 * @param device
	 */
	Boolean updateById(Device device);
}
