package com.example.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.business.model.SecurityConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-19
 */
@Mapper
public interface SecurityConfigMapper extends BaseMapper<SecurityConfig> {

    @Select("select * from t_security_config where source=#{source} and state='1'")
    SecurityConfig selectOneBySource(@Param("source") String source);
}
