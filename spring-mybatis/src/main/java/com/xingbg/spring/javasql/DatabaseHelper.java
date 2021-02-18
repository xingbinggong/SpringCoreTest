package com.xingbg.spring.javasql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper {

    private String url="jdbc:mysql://127.0.0.1:3306/spring_test?serverTimezone=UTC&characterEncoding=UTF-8";
    private String user="root";
    private String password="xingbg";

    private ConnectionPool connectionPool;

    public DatabaseHelper() {
        this.connectionPool = new ConnectionPool(3,url,user,password);
    }

    public Connection getConnection() {
        return connectionPool.getConnection();
    }

    public void returnConnection(Connection c) { connectionPool.returnConnection(c); }

    public boolean executeNonQuery(String sql,Object[] parameters) {
        Connection c = null;
        PreparedStatement ps =null;
        try {
            c = getConnection();
            ps = c.prepareStatement(sql);
            if (parameters!=null) {
                for (int i=1;i<=parameters.length;i++) {
                    ps.setObject(i,parameters[i-1]);
                }
            }
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            close(null,ps,c);
        }
    }

    /**
     * 事务执行
     * @param c 请从getConnection方法中获取
     * @param sql
     * @param parameters
     * @return
     */
    public boolean executeNonQuery(Connection c,String sql,Object[] parameters){
        PreparedStatement ps =null;
        try {
            c.setAutoCommit(false);
            ps = c.prepareStatement(sql);
            if (parameters!=null) {
                for (int i=1;i<=parameters.length;i++) {
                    ps.setObject(i,parameters[i-1]);
                }
            }
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            close(null,ps,null);
        }
    }

    public void commit(Connection c) {
        try {
            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            this.returnConnection(c);
        }
    }

    public void rollback(Connection c) {
        try {
            c.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            this.returnConnection(c);
        }
    }

    /**
     * 查询
     * @param sql
     * @param parameters
     * @param tclass 返回模型类型，由于java的泛型擦除无法像C#那样去通过泛型获取类型
     * @param <T>
     * @return
     */
    public <T> List<T> executeQuery(String sql,Object[] parameters,Class<T> tclass) {
        Connection c = null;
        PreparedStatement ps =null;
        ResultSet rs = null;

        try {
            c=getConnection();
            ps = c.prepareStatement(sql);
            if (parameters!=null) {
                for (int i=1;i<=parameters.length;i++) {
                    ps.setObject(i,parameters[i-1]);
                }
            }
            rs = ps.executeQuery();
            return rsToList(rs,tclass);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            close(rs,ps,c);
        }
    }


    private <T> List<T> rsToList(ResultSet rs,Class<T> tclass) throws java.sql.SQLException {
        if (rs == null) {
            return Collections.emptyList();
        }
        //得到结果集（rs）的结构
        ResultSetMetaData rsmd = rs.getMetaData();

        List<T> list = new ArrayList<T>();

        //通过rsmd可以得到该结果集有多少列
        int columnNum = rsmd.getColumnCount();

        //从rs中取出数据，并且封装到ArrayList中
        while (rs.next()) {
            T rowData = null;
            try {
                rowData = tclass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e.getMessage(),e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(),e);
            }
            List<Field> declaredFields = Arrays.asList(tclass.getDeclaredFields());

//            for (Field declaredField : declaredFields) {
//                declaredField.setAccessible(true);
//            }

            for (int i=1;i<=columnNum;i++) {
                final String columnName = rsmd.getColumnName(i);
                Field field = declaredFields.stream().filter(f -> columnName.equals(f.getName())).findFirst().orElse(null);
                if (field!=null) {
                    field.setAccessible(true);
                    try {
                        field.set(rowData,rs.getObject(i));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e.getMessage(),e);
                    }
                }
            }

            list.add(rowData);
        }
        return list;
    }



    public void close(ResultSet rs, Statement ps, Connection ct) {
        //关闭资源
        if(rs!=null) {
            try {
                rs.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps!=null) {
            try {
                ps.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        if(null!=ct) {
            connectionPool.returnConnection(ct);
        }
    }

}
