package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    void update(Orders orders);

    Orders getByNumberAndUserId(String orderNumber, Long userId);

    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    @Select("select * from orders where id= #{id}")
    Orders getById(Long id);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    Integer countStatus(Integer toBeConfirmed);

    List<Double> sumByMap(Map map);

    Double sumAllByMap(Map map);

    Integer countByMap(Map map);

    List<GoodsSalesDTO> getSalesTop(LocalDateTime begin, LocalDateTime end);

}
