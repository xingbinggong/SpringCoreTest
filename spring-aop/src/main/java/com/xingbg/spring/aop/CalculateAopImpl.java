package com.xingbg.spring.aop;

import com.xingbg.spring.proxy.ICalculate;

import java.math.BigDecimal;

public class CalculateAopImpl implements ICalculateAop {

    private BigDecimal result = BigDecimal.ZERO;

    public BigDecimal add(BigDecimal val) {
        result = result.add(val);
        return result;
    }

    public BigDecimal substract(BigDecimal val) {
        result = result.subtract(val);
        return result;
    }

    public BigDecimal getResult() {
        return result;
    }
}
