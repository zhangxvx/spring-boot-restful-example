package com.example.business.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-20
 */
@TableName("t_security_config")
public class SecurityConfig {

    @TableId("source")
    private String source;

    @TableField("token")
    private String token;

    @TableField("secure_key")
    private String secureKey;

    @TableField("sign_key")
    private String signKey;

    @TableField("url_pattern")
    private String urlPattern;

    @TableField("state")
    private Boolean state;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecureKey() {
        return secureKey;
    }

    public void setSecureKey(String secureKey) {
        this.secureKey = secureKey;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SecurityConfig{" +
                "source=" + source +
                ", token=" + token +
                ", secureKey=" + secureKey +
                ", signKey=" + signKey +
                ", urlPattern=" + urlPattern +
                ", state=" + state +
                "}";
    }
}
