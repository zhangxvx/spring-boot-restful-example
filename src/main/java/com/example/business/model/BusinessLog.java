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
@TableName("t_business_log")
public class BusinessLog {

    @TableId("apply_id")
    private String applyId;

    @TableField("name")
    private String name;

    @TableField("value")
    private String value;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BusinessLog{" +
                "applyId=" + applyId +
                ", name=" + name +
                ", value=" + value +
                "}";
    }
}
