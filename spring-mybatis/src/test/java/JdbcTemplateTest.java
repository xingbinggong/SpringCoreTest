import com.xingbg.spring.javasql.school;
import com.xingbg.spring.jdbctemplate.JdbcSchool;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplateTest {

    @Test
    public void testQuery() {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        List<JdbcSchool> schools = jdbcTemplate.query("select * from school", new RowMapper<JdbcSchool>() {
            @Override
            public JdbcSchool mapRow(ResultSet resultSet, int i) throws SQLException {
                JdbcSchool item = new JdbcSchool();
                item.setId(resultSet.getInt("id"));
                item.setName(resultSet.getString("s_name"));
                item.setGrade(resultSet.getInt("s_grade"));
                return item;
            }
        });
        for (JdbcSchool school : schools) {
            System.out.println(school.toString());
        }
        Assert.assertNotNull(schools);
    }

    @Test
    public void testAdd() {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        //使用JdbcTemplate对象的update方法进行增删改
        int i = jdbcTemplate.update("insert into school(s_name,s_grade) values(?,?)", "jdbc template 中", 2);
        System.out.println(i);
    }

    //针对查询一个值：String str = jdbcTemplate.queryForObject(sql, String.class);
    // long forLong = jdbcTemplate.queryForLong(sql);
    // int forInt = jdbcTemplate.queryForInt(sql);

}
