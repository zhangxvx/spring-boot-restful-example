package com.example.business.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-20
 */
@TableName("t_flow_log")
public class FlowLog {

    @TableId("apply_id")
    private String applyId;

    @TableField("name")
    private String name;

    @TableField("mobile")
    private String mobile;

    @TableField("id_no")
    private String idNo;

    @TableField("apply_time")
    private LocalDateTime applyTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "FlowLog{" +
                "applyId=" + applyId +
                ", name=" + name +
                ", mobile=" + mobile +
                ", idNo=" + idNo +
                ", applyTime=" + applyTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
