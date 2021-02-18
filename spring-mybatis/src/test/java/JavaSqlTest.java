import com.xingbg.spring.javasql.DatabaseHelper;
import com.xingbg.spring.javasql.school;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
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
    public void testUpdate() {
        String sql ="update school set s_name=? where id=?";
        DatabaseHelper databaseHelper = new DatabaseHelper();
        Object[] ps ={
                "XX一中up",
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

    /**
     * 使用连接池多线程查询
     */
    @Test
    public void testBenchQueryWithConnectionPool() {
        String sql = "select * from school";
        System.out.println("开始初始化连接池："+LocalDateTime.now());
        DatabaseHelper databaseHelper = new DatabaseHelper();
        System.out.println("连接池初始化完成："+LocalDateTime.now());
        for (int i=0;i<100;i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<school> schools = databaseHelper.executeQuery(sql, null, school.class);
                    System.out.println(LocalDateTime.now()+"::"+schools.get(0));
                }
            });
            t.start();
        }
        //System.out.println("100次查询完成："+LocalDateTime.now());
        try {
            //这里是因为junit执行完方法就会强制退出，为了执行完线程的方法
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
