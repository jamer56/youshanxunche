package cc.llcon.youshanxunche.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtils {


    public static byte[] UUIDtoBytes(String uuid) {
        UUID uuid1;
        try {
            uuid1 = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("uuid輸入不合法", e);
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid1.getMostSignificantBits());
        byteBuffer.putLong(uuid1.getLeastSignificantBits());
        return byteBuffer.array();
    }


}
