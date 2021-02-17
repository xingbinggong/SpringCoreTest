package com.xingbg.spring.ioc.pojo;

import com.xingbg.spring.ioc.service.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;

public class HelloImpl implements Hello {

    public HelloImpl(String para) {
        this.msg = para;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public Student getStudent() {
        return student;
    }

    @Autowired(required = false)
    @Qualifier("xxx")
    public void setStudent(@Nullable Student student) {
        this.student = student;
    }


    private Student student;





    public void say() {
        System.out.println(msg);
    }

    public void setMsg(String msg) {
        this.msg=msg;
    }
}
