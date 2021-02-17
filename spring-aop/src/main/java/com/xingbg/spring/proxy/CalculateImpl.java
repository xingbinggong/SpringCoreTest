package com.xingbg.spring.proxy;

import java.math.BigDecimal;

public class CalculateImpl implements ICalculate {

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
