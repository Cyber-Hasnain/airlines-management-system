package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JLabel l1,l2,l3,l4;
    JButton bt1,bt2;
    JPasswordField pf;
    JTextField tf;
    JFrame f;

    Login(){
        JFrame f=new JFrame("Login Panel");
        f.setBackground(Color.white);
        f.setLayout(null);

        l1=new JLabel();
        l1.setBounds(0,0,600,350);
        l1.setLayout(null);

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("AMS/icons/Airplane For Login Page.jpg"));
        Image i1 = img.getImage().getScaledInstance(600,350, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(i1);
        l1.setIcon(img2);

        l2=new JLabel("UserName");
        l2.setBounds(120,120,150,30);
        l2.setForeground(Color.BLACK);
        l2.setFont(new Font("Times New Roman",Font.BOLD,20));
        l1.add(l2);
        f.add(l1);

        l3=new JLabel("Login Panel");
        l3.setBounds(190,30,500,50);
        l3.setForeground(Color.BLACK);
        l3.setFont(new Font("Times New Roman",Font.BOLD,30));
        l1.add(l3);

        l4=new JLabel("Password");
        l4.setBounds(120,180,150,30);
        l4.setForeground(Color.BLACK);
        l4.setFont(new Font("Times New Roman",Font.BOLD,20));
        l1.add(l4);

        tf=new JTextField();
        tf.setBounds(300,120,150,30);
        l1.add(tf);

        pf=new JPasswordField();
        pf.setBounds(300,180,150,30);
        l1.add(pf);

        bt1=new JButton("Login");
        bt1.setBounds(120,240,150,40);
        bt1.setBackground(Color.WHITE);
        bt1.setForeground(Color.BLACK);
        bt1.addActionListener(this);
        l1.add(bt1);

        bt2=new JButton("SignUp");
        bt2.setBounds(300,240,150,40);
        bt2.setBackground(Color.WHITE);
        bt2.setForeground(Color.BLACK);
        bt2.addActionListener(this);
        l1.add(bt2);

        f.setVisible(true);
        f.setSize(600,380);
        f.setLocation(300,100);




    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource()==bt1){
            String userName=tf.getText();
            String password=pf.getText();
            try {
                ConnectionClass obj=new ConnectionClass();
                String q= "select * from signup where username='"+userName+"' and password='"+password+"'";
                ResultSet rs = obj.stm.executeQuery(q);
                if (rs.next()){
                    //new HomePage().setVisible(true);
                    f.setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Wrong Username or Password");
                    f.setVisible(false);
                    f.setVisible(true);
                }


            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        if (e.getSource()==bt2){
            this.f.setVisible(false);
            //new SignupMessage();

        }

    }

    public static void main(String[] args){
        new Login();
    }
}
