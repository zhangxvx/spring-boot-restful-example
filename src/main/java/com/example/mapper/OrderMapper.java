package com.example.mapper;

import com.example.model.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-09
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
