package cc.llcon.youshanxunche.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtils {

    /**
     * 将UUID字符串转换为字节数组。
     * <p>
     * 此方法接受一个UUID字符串，验证其有效性，并将其转换为一个16字节的数组。
     * UUID（Universally Unique Identifier）是一种用于唯一标识的标准化格式，
     * 这个方法提供了将这种标识符格式转换为字节序列的手段，以便于存储或传输。
     *
     * @param uuid 一个字符串表示的UUID。必须符合UUID的格式要求。
     * @return 一个16字节长度的数组，代表了给定UUID的二进制形式。
     * @throws IllegalArgumentException 如果给定的字符串不是一个有效的UUID，则抛出此异常。
     */
    public static byte[] UUIDtoBytes(String uuid) {
        UUID uuid1;
        // 尝试将字符串转换为UUID对象。如果字符串格式不正确，将抛出IllegalArgumentException。

        try {
            // 判斷是否有分隔符
            if (uuid.contains("-")) {
                uuid1 = UUID.fromString(uuid);
            } else if (uuid.length() == 32) {
                StringBuilder sb = new StringBuilder(uuid);
                sb.insert(8, '-');
                sb.insert(13, '-');
                sb.insert(18, '-');
                sb.insert(23, '-');

                uuid1 = UUID.fromString(sb.toString());
            } else {
                throw new IllegalArgumentException("Invalid UUID string length.");
            }
        } catch (IllegalArgumentException e) {
            // 捕获到异常时，重新抛出一个新的IllegalArgumentException，包含更详细的错误信息。
            throw new IllegalArgumentException("uuid輸入不合法", e);
        }
        // 使用ByteBuffer来处理字节操作。ByteBuffer是一个用于处理字节序列的类，
        // 它提供了一种灵活的方式来操作字节数据，包括读写各种数据类型。
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        // 将UUID的MSB和最LSB分别放入ByteBuffer中。
        // 这里的putLong方法用于将长整型值放入ByteBuffer的当前位置。
        byteBuffer.putLong(uuid1.getMostSignificantBits());
        byteBuffer.putLong(uuid1.getLeastSignificantBits());
        // 返回ByteBuffer中的字节数组。array方法用于获取ByteBuffer底层的字节数组。
        return byteBuffer.array();
    }
}
