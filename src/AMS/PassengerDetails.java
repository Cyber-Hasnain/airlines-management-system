package AMS;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.io.*;

public class PassengerDetails extends JFrame implements ActionListener {
    JButton bt1, bt2;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf10;
    JFrame f;



    PassengerDetails(){
        f=new JFrame(" Enter Passenger Details");
        f.setBackground(Color.white);
        f.setLayout(null);

        l1=new JLabel();
        l1.setBounds(0,0,950,620);
        l1.setLayout(null);
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("AMS/icons/AddPassenger.jpg"));
        Image i1 = img.getImage().getScaledInstance(950,620, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(i1);
        l1.setIcon(img2);

        l2=new JLabel("UserName");
        l2.setBounds(50,100,150,30);
        l2.setFont(new Font("Times New Roman",Font.BOLD,25));
        l2.setForeground(Color.black);
        l1.add(l2);
        f.add(l1);

        l3=new JLabel("AIR HASSNAIN WELCOMES YOU!");
        l3.setBounds(150,30,750,50);
        l3.setFont(new Font("Times New Roman",Font.BOLD,35));
        l3.setForeground(Color.black);
        l1.add(l3);

        tf1=new JTextField();
        tf1.setBounds(220,100,180,30);
        l1.add(tf1);

        l4=new JLabel("Name");
        l4.setBounds(500,100,150,30);
        l4.setFont(new Font("Times New Roman",Font.BOLD,25));
        l4.setForeground(Color.black);
        l1.add(l4);

        tf2=new JTextField();
        tf2.setBounds(220,160,180,30);
        l1.add(tf2);

        l5=new JLabel("Age");
        l5.setBounds(50,160,100,30);
        l5.setFont(new Font("Times New Roman",Font.BOLD,25));
        l5.setForeground(Color.black);
        l1.add(l5);

        tf3=new JTextField();
        tf3.setBounds(680,160,180,30);
        l1.add(tf3);

        l6=new JLabel("Date Of Birth");
        l6.setBounds(500,160,200,30);
        l6.setFont(new Font("Times New Roman",Font.BOLD,25));
        l6.setForeground(Color.black);
        l1.add(l6);

        tf4=new JTextField();
        tf4.setBounds(680,100,200,30);
        l1.add(tf4);

        l7=new JLabel("Address");
        l7.setBounds(50,220,150,30);
        l7.setFont(new Font("Times New Roman",Font.BOLD,25));
        l7.setForeground(Color.black);
        l1.add(l7);

        tf5=new JTextField();
        tf5.setBounds(220,220,200,30);
        l1.add(tf5);

        l8=new JLabel("Contact Number");
        l8.setBounds(500,220,200,30);
        l8.setFont(new Font("Times New Roman",Font.BOLD,25));
        l8.setForeground(Color.black);
        l1.add(l8);

        tf6=new JTextField();
        tf6.setBounds(680,220,200,30);
        l1.add(tf6);

        l9=new JLabel("Email");
        l9.setBounds(50,280,100,30);
        l9.setFont(new Font("Times New Roman",Font.BOLD,25));
        l9.setForeground(Color.black);
        l1.add(l9);

        tf7=new JTextField();
        tf7.setBounds(220,280,200,30);
        l1.add(tf7);

        l10=new JLabel("Nationality");
        l10.setBounds(500,280,150,30);
        l10.setFont(new Font("Times New Roman",Font.BOLD,25));
        l10.setForeground(Color.black);
        l1.add(l10);

        tf8=new JTextField();
        tf8.setBounds(680,280,200,30);
        l1.add(tf8);

        l11=new JLabel("Gender");
        l11.setBounds(50,340,100,30);
        l11.setFont(new Font("Times New Roman",Font.BOLD,25));
        l11.setForeground(Color.black);
        l1.add(l11);

        tf9=new JTextField();
        tf9.setBounds(220,340,200,30);
        l1.add(tf9);

        l12=new JLabel("Passport No.");
        l12.setBounds(500,340,220,30);
        l12.setFont(new Font("Times New Roman",Font.BOLD,25));
        l12.setForeground(Color.black);
        l1.add(l12);

        tf10=new JTextField();
        tf10.setBounds(680,340,200,30);
        l1.add(tf10);

        bt1=new JButton("Save");
        bt1.setBackground(Color.black);
        bt1.setForeground(Color.white);
        bt1.setBounds(300,500,150,40);
        bt1.addActionListener(this);
        l1.add(bt1);

        bt2=new JButton("Cancel");
        bt2.setBackground(Color.red);
        bt2.setForeground(Color.white);
        bt2.setBounds(500,500,150,40);
        bt2.addActionListener(this);
        l1.add(bt2);

        f.setVisible(true);
        f.setSize(950,620);
        f.setLocation(300,100);

    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==bt1){
            String user=tf1.getText();
            String name=tf2.getText();
            String age=tf3.getText();
            String date=tf4.getText();
            String address=tf5.getText();
            String contact=tf6.getText();
            String email=tf7.getText();
            String nationality=tf8.getText();
            String gender=tf9.getText();
            String passport=tf10.getText();

            try {
                ConnectionClass obj=new ConnectionClass();
                String q = "INSERT INTO Passenger(username, name, age, dob, address, phone, email, nationality, gender, passport) VALUES('"
                        + tf1.getText() + "','"
                        + tf2.getText() + "','"
                        + tf3.getText() + "','"
                        + tf4.getText() + "','"
                        + tf5.getText() + "','"
                        + tf6.getText() + "','"
                        + tf7.getText() + "','"
                        + tf8.getText() + "','"
                        + tf9.getText() + "','"
                        + tf10.getText() + "')";
                obj.stm.executeUpdate(q);
                JOptionPane.showMessageDialog(null,"Passenger Added Successfully");
                f.setVisible(false);
                //new HomePage();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            // FILE HANDLING - Save passenger data to a text file
            try {
                FileWriter fw = new FileWriter("passengers.txt", true); // true = append mode
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("Passenger Record");
                bw.newLine();
                bw.write("Username   : " + user);
                bw.newLine();
                bw.write("Name       : " + name);
                bw.newLine();
                bw.write("Age        : " + age);
                bw.newLine();
                bw.write("DOB        : " + date);
                bw.newLine();
                bw.write("Address    : " + address);
                bw.newLine();
                bw.write("Contact    : " + contact);
                bw.newLine();
                bw.write("Email      : " + email);
                bw.newLine();
                bw.write("Nationality: " + nationality);
                bw.newLine();
                bw.write("Gender     : " + gender);
                bw.newLine();
                bw.write("Passport   : " + passport);
                bw.newLine();
                bw.write("============================");
                bw.newLine();
                bw.newLine();

                bw.close();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }


        }
        if (e.getSource()==bt2){
            JOptionPane.showMessageDialog(null,"Are You Sure?");
            f.setVisible(false);

        }

    }
    public static void main(String[] args){
        new PassengerDetails();
    }
}
