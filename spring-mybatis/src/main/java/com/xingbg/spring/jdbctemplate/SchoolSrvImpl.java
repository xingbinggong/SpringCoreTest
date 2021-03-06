package com.xingbg.spring.jdbctemplate;

import com.xingbg.spring.jdbctemplate.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Component("schoolSrv")
public class SchoolSrvImpl implements SchoolService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private TransactionTemplate transactionTemplate;

    @Autowired
    @Qualifier("transactionTemplate")
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public void save() {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");

        String sqlAdd = "insert into school(s_name,s_grade) values(?,?)";
        String sqlUpdate ="update school set s_name=? where s_name=?";

        jdbcTemplate.update(sqlAdd,"springTrans",2);


        jdbcTemplate.update(sqlUpdate,"springTransition","springTrans");
//模拟断电
       //int i = 1/0;

    }

    public void save2() {

        transactionTemplate.execute((s)->{
                String sqlAdd = "insert into school(s_name,s_grade) values(?,?)";
                String sqlUpdate = "update school set s_name=? where s_name=?";

                jdbcTemplate.update(sqlAdd, "springTrans", 2);
            //int i = 1/0;
                jdbcTemplate.update(sqlUpdate, "springTransition", "springTrans");
                return true;
        });

    }
}
