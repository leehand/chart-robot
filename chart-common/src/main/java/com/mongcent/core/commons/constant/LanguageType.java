package com.mongcent.core.commons.constant;

import java.util.Objects;

/**
 * Author: hezishan
 * Date: 2018/10/25.
 * Description:
 **/
public enum LanguageType {

    km("km-KH", "柬文"),
    zh("zh-Hans", "中文"),
    en("en-US", "英文"),
    other("other", "其他");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    LanguageType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static LanguageType getByCode(String code) {
        for (LanguageType languageType : values()) {
            if (Objects.equals(code, languageType.getCode())) {
                return languageType;
            }
        }
        return LanguageType.other;
    }
}
