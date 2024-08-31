package cc.llcon.youshanxunche.constant;

import lombok.Getter;
import lombok.Setter;

public enum VerificationCodeType {
    REGISTER(1, "注册验证码"),
    FORGET(2, "忘记密码验证码"),
    MODIFY(3, "修改密码验证码");

    @Getter
    @Setter
    private int code;
    private String desc;

    VerificationCodeType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static VerificationCodeType fromValue(int value) {
        for (VerificationCodeType e : VerificationCodeType.values()) {
            if (e.getCode() == value) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
