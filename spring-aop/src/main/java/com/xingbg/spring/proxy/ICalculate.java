package com.xingbg.spring.proxy;

import java.math.BigDecimal;

public interface ICalculate {
    BigDecimal add(BigDecimal val);

    BigDecimal substract(BigDecimal val);

    BigDecimal getResult();
}
