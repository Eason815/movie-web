package cn.edu.scnu.controller;


import cn.edu.scnu.config.AliPayConfig;
import cn.edu.scnu.entity.Orders;
import cn.edu.scnu.dao.OrdersMapper;
import cn.edu.scnu.entity.TbUser;
import cn.edu.scnu.mapper.UserMapper;
import cn.edu.scnu.service.OrdersService;
import cn.edu.scnu.service.UserService;
import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// vvnsue7040@sandbox.com
// 111111
@Controller
@RequestMapping("/alipay")
public class AliPayController {

    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA2";

    @Resource
    private AliPayConfig aliPayConfig;

    @Resource
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/pay")
    @ResponseBody
    public void pay(AliPay aliPay, HttpServletResponse httpResponse) throws Exception {

        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.set("out_trade_no", aliPay.getTraceNo());
        bizContent.set("total_amount", aliPay.getTotalAmount());
        bizContent.set("subject", aliPay.getSubject());
        bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());

        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @PostMapping("/notify")
    @ResponseBody
    public String payNotify(HttpServletRequest request) throws Exception {
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            String outTradeNo = params.get("out_trade_no");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), CHARSET);

            if (checkSignature) {
                QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("order_id", outTradeNo);
                Orders orders = ordersMapper.selectOne(queryWrapper);

                if (orders != null) {
                    orders.setAlipayNo(alipayTradeNo);
                    orders.setPayTime(new Date());
                    orders.setState("已支付");
                    ordersMapper.updateById(orders);


                    TbUser user = userMapper.selectById(orders.getUserId());
                    if (user != null && orders.getGoodsId() == 1) {
                        System.out.println("用户" + user.getUsername() + "购买会员成功");
                        user.setIsVip(1);
                        userService.updateById(user);
                    }
                }
            }
        }
        return "success";
    }


}