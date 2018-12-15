package service;

import dao.UsersDao;
import pojo.Users;

public class UserService {

    private UsersDao usersDao = new UsersDao();

    /**
     * 注册用户
     */
    public Boolean signUp(Users users) {

        int i = usersDao.addUser(users);
        if (i == 1) {
            return true;
        }
        return false;
    }

    /**
     * 用户登录
     */
    public Boolean signIn(Users user) {

        Users u = usersDao.findUserByName(user.getUsername());
        if (u.getPassword().equals(user.getPassword())) {
            return true;
        }

        return false;
    }


}
