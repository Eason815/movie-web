package cn.edu.scnu.service;

import cn.edu.scnu.entity.Orders;
import cn.edu.scnu.dao.OrdersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    public void updateState(String tradeNo, String state, String payTime, String alipayNo) {
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("no", tradeNo)
                .set("state", state)
                .set("pay_time", payTime)
                .set("alipay_no", alipayNo);
        ordersMapper.update(null, updateWrapper);
    }

    public Orders findOrderByOrderId(String outTradeNo) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", outTradeNo);  // 假设订单号的字段名是 order_id
        return ordersMapper.selectOne(queryWrapper);
    }
}
