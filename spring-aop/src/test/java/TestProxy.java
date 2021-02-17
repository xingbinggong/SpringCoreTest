import com.xingbg.spring.proxy.CalculateImpl;
import com.xingbg.spring.proxy.ICalculate;
import com.xingbg.spring.proxy.ProxyInvocationHandler;
import com.xingbg.spring.proxy.ProxyMethodInterceptor;
import org.junit.Test;

import java.math.BigDecimal;

public class TestProxy {

    /**
     * 使用 JDK的 InvocationHandler+Proxy
     * target必须实现接口
     */
    @Test
    public void test1() {
        ICalculate calculate = new CalculateImpl();
        ProxyInvocationHandler<ICalculate> pih = new ProxyInvocationHandler<ICalculate>();
        pih.setTarget(calculate);
        ICalculate proxy = pih.getProxy();
        proxy.add(new BigDecimal("6"));
        proxy.substract(new BigDecimal("4"));
        System.out.println(proxy.getResult());
    }

    /**
     * 使用spring cglib的 Enhancer + MethodInterceptor
     * target不需要实现接口
     */
    @Test
    public void testCgLibProxy() {
        CalculateImpl calculate = new CalculateImpl();
        ProxyMethodInterceptor<CalculateImpl> pmi=new ProxyMethodInterceptor();
        pmi.setTarget(calculate);
        CalculateImpl proxy = pmi.getProxy();
        proxy.add(new BigDecimal("6"));
        proxy.substract(new BigDecimal("4"));
        System.out.println(proxy.getResult());
    }
}
