package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Goods;
import cn.edu.scnu.entity.Orders;
import cn.edu.scnu.dao.GoodsMapper;
import cn.edu.scnu.dao.OrdersMapper;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@MapperScan("cn.edu.scnu.dao")
public class ApiController {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @GetMapping("/goods")
    public List<Goods> getGoods() {
        return goodsMapper.selectList(null);
    }

    @GetMapping("/orders")
    public List<Orders> getOrders() {
        return ordersMapper.selectList(null);
    }

    @Transactional
    @PostMapping("/buy")
    public boolean buy(@RequestParam Integer goodsId) {
        Goods goods = goodsMapper.selectById(goodsId);
        int store = goods.getStore() - 1;
        if (store < 0) {
            return false;
        }
        Date date = new Date();
        Orders orders = new Orders();
        orders.setGoodsId(goodsId);
        orders.setCreateTime(date);
        orders.setName("购买" + goods.getName() + "订单");
        orders.setOrderId(new SimpleDateFormat("yyyyMMdd").format(date) + System.currentTimeMillis());
        orders.setTotal(goods.getPrice().multiply(BigDecimal.ONE));

        goods.setStore(store);
        return ordersMapper.insert(orders) > 0 && goodsMapper.updateById(goods) > 0;
    }

    @Transactional
    @PostMapping("/cancelOrder")
    public boolean cancelOrder(@RequestParam String orderId) {
        Orders order = ordersMapper.selectById(orderId);
        if (order != null) {
            order.setState("已取消");
            return ordersMapper.updateById(order) > 0;
        }
        return false;
    }

}