import com.xingbg.spring.annotation.MyConfig;
import com.xingbg.spring.annotation.pojo.Persion;
import com.xingbg.spring.annotation.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class MyTest {

    @Test
    public void testPersion() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Persion persion = context.getBean("persion", Persion.class);
        persion.getCat().shout();
        persion.getDog().shout();
    }

    @Test
    public void testCompoent() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //需要给User类增加Component注解，这样即便在beans.xml中不配置也能够在上下文中找到。
        User bean = context.getBean(User.class);
        System.out.println(bean.getName());
    }

    @Test
    public void testAnnotation() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        //需要取消User类的Component注解
        User bean = context.getBean(User.class);
        Map<String, User> beansOfType = context.getBeansOfType(User.class);
        User getUser = context.getBean("getUser", User.class);//方法名就是bean的名字
        Assert.assertEquals(bean,getUser);
        System.out.println(getUser.getName());
    }
}
