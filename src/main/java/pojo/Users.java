package pojo;

import util.MD5;

public class Users {

    private String username;
    private String password;


    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = MD5.MD5Encode(password);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
