package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.entity.Setmeal;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private DishMapper dishMapper;

    @Override
    public BusinessDataVO getBussinessData(LocalDateTime begin, LocalDateTime end) {
        BusinessDataVO businessDataVO = new BusinessDataVO();
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);

//        时间区间内的总订单数
        Integer totalOrderCount = orderMapper.countByMap(map);

//        区间时间内有效订单数
        map.put("status", Orders.COMPLETED);
        Integer validOrderCount = orderMapper.countByMap(map);
        businessDataVO.setValidOrderCount(validOrderCount);

//        订单完成率
        businessDataVO.setOrderCompletionRate(totalOrderCount == 0 ? 0 : validOrderCount.doubleValue() / totalOrderCount);

//        营业额
        Double totalNumber = orderMapper.sumByMap(map).get(0);
        businessDataVO.setTurnover(totalNumber);

        businessDataVO.setUnitPrice(validOrderCount == 0 ? 0 : totalNumber / validOrderCount);

        businessDataVO.setNewUsers(userMapper.countByMap(map));

        return businessDataVO;
    }

    @Override
    public SetmealOverViewVO countSetmeals() {
        SetmealOverViewVO setmealOverViewVO = new SetmealOverViewVO();

        Map map = new HashMap();

        map.put("status", StatusConstant.ENABLE);
        setmealOverViewVO.setSold(setmealMapper.countByMap(map));

        map.put("status", StatusConstant.DISABLE);
        setmealOverViewVO.setDiscontinued(setmealMapper.countByMap(map));

        return setmealOverViewVO;
    }

    @Override
    public DishOverViewVO countDishs() {
        DishOverViewVO dishOverViewVO = new DishOverViewVO();
        Map map = new HashMap();

        map.put("status", StatusConstant.ENABLE);
        dishOverViewVO.setSold(dishMapper.countByMap(map));

        map.put("status", StatusConstant.DISABLE);
        dishOverViewVO.setDiscontinued(dishMapper.countByMap(map));

        return dishOverViewVO;
    }

    @Override
    public OrderOverViewVO countOrders() {
        OrderOverViewVO orderOverViewVO = new OrderOverViewVO();
        Map map = new HashMap();

        map.put("begin", LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        orderOverViewVO.setAllOrders(orderMapper.countByMap(map));

        map.put("status", Orders.CANCELLED);
        orderOverViewVO.setCancelledOrders(orderMapper.countByMap(map));

        map.put("status", Orders.COMPLETED);
        orderOverViewVO.setCompletedOrders(orderMapper.countByMap(map));

        map.put("status", Orders.CONFIRMED);
        orderOverViewVO.setDeliveredOrders(orderMapper.countByMap(map));

        map.put("status", Orders.TO_BE_CONFIRMED);
        orderOverViewVO.setWaitingOrders(orderMapper.countByMap(map));

        return orderOverViewVO;
    }


}
