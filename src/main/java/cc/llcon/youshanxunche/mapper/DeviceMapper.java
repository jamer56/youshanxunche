package cc.llcon.youshanxunche.mapper;

import cc.llcon.youshanxunche.pojo.Device;
import cc.llcon.youshanxunche.pojo.DeviceLoginDTO;
import cc.llcon.youshanxunche.pojo.ListDeviceParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceMapper {

    /**
     * 新增设备
     *
     * @param device
     */
    void inst(Device device);

    /**
     * 查询所有设备
     *
     * @return
     */
    List<Device> list(ListDeviceParam user);

    /**
     * 根据使用者id 获取设备列表
     *
     * @param uid 使用者id
     * @return 该使用者的设备列表
     */
    List<Device> listByUserId(String uid);

    /**
     * 取得设备数量
     *
     * @return
     */
    Long getTotal();

    /**
     * 获取该使用者的设备总数
     *
     * @param uid userId
     * @return 使用者设备总数
     */
    Long getTotalByUserId(String uid);

    /**
     * 根据id查询设备
     *
     * @param id
     * @return
     */
    Device getById(String id);

    /**
     * 透过id和macaddress查询设备
     *
     * @param device
     * @return
     */
    Device getByIdAndMacAddress(DeviceLoginDTO device);

    /**
     * 修改设备描述和名称
     *
     * @param device
     */
    Boolean updateById(Device device);
}
