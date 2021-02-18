import com.xingbg.spring.javasql.DatabaseHelper;
import com.xingbg.spring.javasql.school;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JavaSqlTest {

    @Test
    public void testAdd() {
        String sql = "insert into school(s_name,s_grade) values(?,?)";
        DatabaseHelper databaseHelper = new DatabaseHelper();
        Object[] ps ={
                "XX一中",
                1
        };
        boolean b = databaseHelper.executeNonQuery(sql, ps);
    }

    @Test
    public void testQuery() {
        String sql = "select * from school";
        DatabaseHelper databaseHelper = new DatabaseHelper();
        List<school> schools = databaseHelper.executeQuery(sql, null, school.class);
        Assert.assertNotNull(schools);
    }
}
