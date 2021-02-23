import com.xingbg.spring.mybatis1.MybatisHelper;
import com.xingbg.spring.mybatis1.SchoolMapper;
import com.xingbg.spring.mybatis1.pojo.MySchool;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MybatisTest {

    @Test
    public void test1() {
        //写在()里面的流对象对应的类都实现了自动关闭接口AutoCloseable；
        try (SqlSession session = MybatisHelper.getSqlSessionFactory().openSession()) {
            SchoolMapper mapper = session.getMapper(SchoolMapper.class);
            List<MySchool> allSchools = mapper.getAllSchools();
            for (MySchool school : allSchools) {
                System.out.println(school);
            };
        } finally {
        }

    }
}
