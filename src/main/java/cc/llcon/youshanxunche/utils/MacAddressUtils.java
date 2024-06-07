package cc.llcon.youshanxunche.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

@Slf4j
public class MacAddressUtils {

    public static byte[] toBytes(String macAddress) {
        macAddress = macAddress.replace(":", "");
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[8]);

        long l = 0;
        try {
            l = Long.parseLong(macAddress, 16);
            byte[] test = new byte[6];
            byteBuffer.putLong(l);
            byteBuffer.flip();
            byteBuffer.get(2, test, 0, 6);
//        log.debug("{}", test);
            return test;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("macAddress输入不合法", e);
        }
        //        log.debug("{}", l);

    }

}
