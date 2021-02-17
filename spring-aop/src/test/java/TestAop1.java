import com.xingbg.spring.aop.ICalculateAop;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

public class TestAop1 {

    /**
     * 使用spring+aspectj的方式
     */
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application1.xml");
        //动态代理，代理的是接口。
        ICalculateAop bean = context.getBean("calculateAop",ICalculateAop.class);
        bean.add(new BigDecimal("234"));
    }

    /**
     * 使用单纯spring的方式
     */
    @Test
    public void testSimpleSpring() {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop_spring.xml");
        //动态代理，代理的是接口。
        ICalculateAop bean = context.getBean("targetProxy",ICalculateAop.class);
        bean.add(new BigDecimal("234"));
    }
}
