package util;

import com.sun.rowset.CachedRowSetImpl;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {

    private static Properties prop = null;

    public JDBCUtils() {
    }

    /**
     * 静态代码块，加载数据库配置文件
     */
    static {
        prop = new Properties();
        try {
            prop.load(JDBCUtils.class.getClassLoader().getResourceAsStream("JdbcConfig.properties"));
//            prop.load(new FileInputStream("/resources/JdbcConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return mysql数据库连接
     * @throws Exception
     */
    private static Connection getConn() throws Exception {
        Class.forName(prop.getProperty("driver"));
        Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("name"),
                prop.getProperty("password"));
        return conn;
    }

    /**
     * 执行sql语句
     * @param sql
     * @param args 动态sql可变参数
     * @return 结果集
     * @throws Exception
     */
    public static CachedRowSetImpl execSQL(String sql, Object... args) throws Exception {
        //获取Connection对象
        Connection conn = JDBCUtils.getConn();
        //建立PreparedStatement对象
        PreparedStatement pstm = conn.prepareStatement(sql);
        //为pstm对象设置SQL参数值
        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i + 1, args[i]);
        }
        //执行sql语句
        pstm.execute();
        //返回结果集，如果执行的SQL语句不返会结果集，则返回null
        ResultSet resultSet = pstm.getResultSet();
        // 如果一定要传递ResultSet，应该使用RowSet，RowSet可以不依赖于Connection和Statement。Java传递的是引用，
        // 所以如果传递ResultSet，你会不知道Statement和Connection何时关闭，不知道ResultSet何时有效。
        CachedRowSetImpl rowSet = new CachedRowSetImpl();
        if (resultSet != null) {
            rowSet.populate(resultSet);
            close(resultSet, pstm, conn);
        }else {
            rowSet = null;
        }
        return rowSet;
    }

    private static void close(ResultSet rs, Statement stat, Connection conn) {

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (stat != null) {
                stat.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
