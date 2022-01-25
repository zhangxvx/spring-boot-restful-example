package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangxv
 * @since 2022-01-23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
