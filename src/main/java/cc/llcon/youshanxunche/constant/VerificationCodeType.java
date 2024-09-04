package cc.llcon.youshanxunche.constant;

import lombok.Getter;
import lombok.Setter;

public enum VerificationCodeType {
    REGISTER(1, "注册验证码", null),
    FORGET(2, "忘记密码验证码", "classpath:templates/forgetmailtemplate.html"),
    MODIFY(3, "修改密码验证码", "classpath:templates/modifymailtemplate.html");

    @Getter
    @Setter
    private int code;
    @Getter
    private String desc;
    @Getter
    private String templates;

    VerificationCodeType(int code, String desc, String templates) {
        this.code = code;
        this.desc = desc;
        this.templates = templates;
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
