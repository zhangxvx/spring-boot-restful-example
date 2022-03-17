package com.example.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.business.model.FlowLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-20
 */
@Mapper
public interface FlowLogMapper extends BaseMapper<FlowLog> {

    @Select("SELECT t1.name, t1.mobile, t1.apply_time, t1.apply_id FROM t_flow_log WHERE t1.name = #{name}")
    Page<FlowLog> selectPageList(Page<FlowLog> page, @Param("name") String name);
}
