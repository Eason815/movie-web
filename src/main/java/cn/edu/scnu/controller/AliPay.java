package cn.edu.scnu.controller;

import lombok.Data;

@Data
public class AliPay {
    private String traceNo;
    private double totalAmount;
    private String subject;
    private String alipayTraceNo;
}