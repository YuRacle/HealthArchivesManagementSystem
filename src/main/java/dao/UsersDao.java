package dao;

import pojo.Users;
import util.JDBCUtils;

import java.sql.ResultSet;

public class UsersDao {

    /**
     * 添加用户
     * @param user
     * @return 1:成功 0:失败
     */
    public int addUser(Users user) {
        String username = user.getUsername();
        String password = user.getPassword();

        String sql = "insert into users values(?,?,?)";
        if (findUserByName(username) == null) {
            try {
                JDBCUtils.execSQL(sql, null, username, password);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 删除用户
     * @param user
     * @return 1:成功 0:失败
     */
    public int deleteUser(Users user) {

        String sql = "delete from users where username = ?";
        if (findUserByName(user.getUsername()) != null) {
            try {
                JDBCUtils.execSQL(sql, user.getUsername());
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public Users findUserByName(String username) {
        String sql = "select * from users where username = ?";

        Users user = new Users();
        try {
            ResultSet rs = JDBCUtils.execSQL(sql, username);
            if (rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            } else {
                user = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
