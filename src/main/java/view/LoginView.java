package view;

import pojo.Users;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static view.IndexView.indexView;

/**
 * Created by YuRacle on 2018/5/5.
 */
public class LoginView extends JFrame implements Runnable{

    public static void main(String[] args) throws Exception {
        new Thread(LoginView.instance());
        loginView.open();
    }

    private UserService userService = new UserService();
    private static LoginView loginView;
    //设置一个含有背景图片的面板
    private MyPanel panel;
    //用户名输入栏
    private JLabel name = new JLabel("用户名:");
    private JTextField jtf_name = new JTextField();
    Font lab = new Font("楷体",1,26);
    //密码输入栏
    private JLabel passwd = new JLabel("密码:");
    private JPasswordField jtf_passwd = new JPasswordField();
    Font lat  = new Font("楷体",1,26);
    //注册登录按钮
    private JButton btn_signUp = new JButton("注册");
    private JButton btn_signIn = new JButton("登陆");
    Font btn = new Font("黑体",2,24);

    public static LoginView instance() {
        if (loginView == null) {
            loginView = new LoginView();
        }
        return loginView;
    }

    public LoginView() {
        this.setSize(400,267);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("登陆");
        this.setResizable(false);

        //注册按钮监听
        btn_signUp.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                String signUpName = jtf_name.getText();
                String signUpPasswd = jtf_passwd.getText();
                //创建用户
                Users user = null;

                if (signUpName.equals("")) {
                    JOptionPane.showMessageDialog(null,"注册错误:用户名为空!");
                }else if(signUpPasswd.equals("")) {
                    JOptionPane.showMessageDialog(null, "注册错误:密码为空!");
                }else {
                    try {
                        user = new Users(signUpName,signUpPasswd);
                        Boolean isAdd = userService.signUp(user);
                        if (isAdd) {
                            JOptionPane.showMessageDialog(null,"注册成功! " +
                                    "您的用户名是："+signUpName);
                        }else {
                            JOptionPane.showMessageDialog(null,"注册失败：用户名已存在!");
                        }
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null,"注册失败：用户名已存在!");
                    }

                    jtf_name.setText("");
                    jtf_passwd.setText("");
                }
            }
        });
        //登录按钮监听
        btn_signIn.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {

                boolean loginPower = false;
                String signInName = jtf_name.getText();
                String signInPasswd = jtf_passwd.getText();
                //创建用户
                Users user = null;
                if (signInName.equals("")) {
                    JOptionPane.showMessageDialog(null,"登录错误:用户名为空!");
                }else if(signInPasswd.equals("")) {
                    JOptionPane.showMessageDialog(null,"登录错误:密码为空!");
                }else {
                    try {
                        user = new Users(signInName,signInPasswd);
                        loginPower = userService.signIn(user);
                        //验证成功
                        if (loginPower == true) {
                            JOptionPane.showMessageDialog(null,"登录成功");
                            setVisible(false);
                            //TODO 登录成功后，显现首页窗口
                            new Thread(IndexView.instance());
                            indexView.open();
                        }else {
                            JOptionPane.showMessageDialog(null,"登录失败，用户名或密码错误！");
                            jtf_name.setText("");
                            jtf_passwd.setText("");
                        }
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null,"登录失败，用户名或密码错误！");
                        jtf_name.setText("");
                        jtf_passwd.setText("");
                    }
                }
            }
        });

        init();
        panel = new MyPanel();
        //将所有的组件添加到panel面板中
        panel.add(name);
        panel.add(passwd);
        panel.add(jtf_name);
        panel.add(jtf_passwd);
        panel.add(btn_signUp);
        panel.add(btn_signIn);
        panel.setLayout(null);
        getContentPane().add(panel);
    }

    /**
     * 初始化组件
     */
    public void init() {

        btn_signUp.setBackground(Color.WHITE);
        btn_signIn.setBackground(Color.WHITE);

        name.setSize(200,100);
        name. setLocation(50,20);
        name.setFont(lab);
        passwd.setSize(200,100);
        passwd.setLocation(50,80);
        passwd.setFont(lat);

        jtf_name.setSize(190,30);
        jtf_name.setLocation(160,55);
        jtf_passwd.setSize(190,30);
        jtf_passwd.setLocation(160,115);

        btn_signIn.setSize(120, 40);
        btn_signIn.setLocation(60, 180);
        btn_signIn.setFont(btn);

        btn_signUp.setSize(120, 40);
        btn_signUp.setLocation(230, 180);
        btn_signUp.setFont(btn);
    }

    public void run() {
        LoginView.instance();
    }

    public void open() {
        this.setVisible(true);
    }
    //自定义一个面板类，背景图
    class MyPanel extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            //绘制一张背景图片  2.jpg是图片的路径  自己设定为自己想要添加的图片
            Image image = new ImageIcon(LoginView.class.getClassLoader().getResource("images/dandelion.jpg")).getImage();
            g.drawImage(image, 0, 0, 1920/4,1200/4,this);
        }
    }
}
