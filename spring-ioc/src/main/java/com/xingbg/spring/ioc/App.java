package com.xingbg.spring.ioc;

import com.xingbg.spring.ioc.pojo.HelloImpl;
import com.xingbg.spring.ioc.pojo.Student;
import com.xingbg.spring.ioc.service.Hello;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Hello hello = (Hello)context.getBean("hello");
        hello.say();

        Student student = (Student) context.getBean("student");
        System.out.println(student.toString());
    }
}
