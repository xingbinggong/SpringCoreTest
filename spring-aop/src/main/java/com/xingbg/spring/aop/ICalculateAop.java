package com.xingbg.spring.aop;

import java.math.BigDecimal;

public interface ICalculateAop {
    BigDecimal add(BigDecimal val);

    BigDecimal substract(BigDecimal val);

    BigDecimal getResult();
}
