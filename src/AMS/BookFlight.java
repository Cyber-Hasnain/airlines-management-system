package AMS;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.Random;

public class BookFlight extends JFrame implements ActionListener {

    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13;
    JButton bt1,bt2;
    JTextField tf1,tf2,tf3,tf4,tf5;
    JPanel p1,p2,p3;
    Font f,f1;
    Choice ch1,ch2,ch3,ch4,ch5,ch6;

    BookFlight(){
        super("Book Your Flight");
        setSize(1100,650);
        setLocation(50,10);

        f=new Font("Times New Roman",Font.BOLD,25);
        f1=new Font("Times New Roman",Font.BOLD,18);

        ch1=new Choice();
        ch2=new Choice();
        ch3=new Choice();
        ch4=new Choice();
        ch5=new Choice();
        ch6=new Choice();

        try{
            ConnectionClass obj=new ConnectionClass();
            String q="select distinct source from Flight";
            ResultSet rest= obj.stm.executeQuery(q);
            while (rest.next()){
                ch1.add(rest.getString("source"));
            }
            rest.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        try{
            ConnectionClass obj=new ConnectionClass();
            String q="select username  from Passenger";
            ResultSet rest= obj.stm.executeQuery(q);
            while (rest.next()){
                ch2.add(rest.getString("username"));
            }
            rest.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

        l1=new JLabel("Book Your Flight");
        l2=new JLabel("Ticket ID");
        l3=new JLabel("Source");
        l4=new JLabel("Destination");
        l5=new JLabel("Class");
        l6=new JLabel("Price");
        l7=new JLabel("Flight Code");
        l8=new JLabel("Flight Name");
        l9=new JLabel("Journey Date");
        l10=new JLabel("Journey Time");
        l11=new JLabel("UserName");
        l13=new JLabel("Name");

        tf1=new JTextField();
        tf2=new JTextField();
        tf3=new JTextField();
        tf4=new JTextField();
        tf5=new JTextField();

        tf1.setEditable(false);
        tf2.setEditable(false);
        tf5.setEditable(false);

        Random rand=new Random();
        tf1.setText(""+Math.abs(rand.nextInt()*10000));
        tf1.setForeground(Color.RED);

        bt1=new JButton("Book Flight");
        bt2=new JButton("Back");
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setForeground(new java.awt.Color(25,27,255));
        l1.setFont(f);
        l2.setFont(f1);
        l3.setFont(f1);
        l4.setFont(f1);
        l5.setFont(f1);
        l6.setFont(f1);
        l7.setFont(f1);
        l8.setFont(f1);
        l9.setFont(f1);
        l10.setFont(f1);
        l11.setFont(f1);
        l13.setFont(f1);
        ch1.setFont(f1);
        ch2.setFont(f1);
        ch3.setFont(f1);
        ch4.setFont(f1);
        ch5.setFont(f1);
        ch6.setFont(f1);

        tf1.setFont(f1);
        tf2.setFont(f1);
        tf3.setFont(f1);
        tf4.setFont(f1);
        tf5.setFont(f1);

        bt1.setFont(f1);
        bt2.setFont(f1);

        l2.setForeground(new java.awt.Color(25,25,112));
        l3.setForeground(new java.awt.Color(25,25,112));
        l4.setForeground(new java.awt.Color(25,25,112));
        l5.setForeground(new java.awt.Color(25,25,112));
        l6.setForeground(new java.awt.Color(25,25,112));
        l7.setForeground(new java.awt.Color(25,25,112));
        l8.setForeground(new java.awt.Color(25,25,112));
        l9.setForeground(new java.awt.Color(25,25,112));
        l10.setForeground(new java.awt.Color(25,25,112));
        l11.setForeground(new java.awt.Color(25,25,112));
        l13.setForeground(new java.awt.Color(25,25,112));

        bt1.setBackground(Color.white);
        bt2.setBackground(Color.white);
        bt1.setForeground(Color.green);
        bt2.setForeground(Color.red);

        p1=new JPanel();
        p1.setLayout(new GridLayout(1,1,10,10));
        p1.add(l1);

        p2=new JPanel();
        p2.setLayout(new GridLayout(12,2,10,10));

        p2.add(l2);
        p2.add(tf1);
        p2.add(l3);
        p2.add(ch1);
        p2.add(l4);
        p2.add(ch6);
        p2.add(l5);
        p2.add(ch3);
        p2.add(l6);
        p2.add(ch4);
        p2.add(l7);
        p2.add(ch5);
        p2.add(l8);
        p2.add(tf2);
        p2.add(l9);
        p2.add(tf3);
        p2.add(l10);
        p2.add(tf4);
        p2.add(l11);
        p2.add(ch2);
        p2.add(l13);
        p2.add(tf5);
        p2.add(bt1);
        p2.add(bt2);

        p3=new JPanel();
        p3.setLayout(new GridLayout(1,1,10,10));

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("AMS/icons/BookFlight.jpg"));
        Image i1 = img.getImage().getScaledInstance(620,470, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(i1);
        l12=new JLabel(img2);
        p3.add(l12);

        setLayout(new BorderLayout(10,10));
        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        add(p3,BorderLayout.WEST);

        ch2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED) return;

                String userName = ch2.getSelectedItem().trim();
                System.out.println("DEBUG: '" + userName + "'");

                try {
                    ConnectionClass obj = new ConnectionClass();
                    String q1 = "select name from Passenger where username='" + userName + "'";
                    ResultSet rs = obj.stm.executeQuery(q1);
                    while (rs.next()) {
                        tf5.setText(rs.getString("name"));
                    }
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        ch1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                ch6.removeAll();

                try {
                    ConnectionClass obj = new ConnectionClass();

                    String source = ch1.getSelectedItem().trim();

                    String q1 = "select distinct destination from Flight where source='" + source + "'";

                    ResultSet rs = obj.stm.executeQuery(q1);

                    while (rs.next()) {
                        ch6.add(rs.getString("destination"));
                    }

                    rs.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        ch6.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                ch3.removeAll();

                try {
                    ConnectionClass obj = new ConnectionClass();

                    String source = ch1.getSelectedItem().trim();
                    String destination = ch6.getSelectedItem().trim();

                    String q1 = "select distinct class_name from Flight where source='"
                            + source + "' and destination='" + destination + "'";

                    ResultSet rs = obj.stm.executeQuery(q1);

                    while (rs.next()) {
                        ch3.add(rs.getString("class_name"));
                    }

                    rs.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        ch3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                ch4.removeAll();

                try {
                    ConnectionClass obj = new ConnectionClass();

                    String source = ch1.getSelectedItem().trim();
                    String destination = ch6.getSelectedItem().trim();
                    String className = ch3.getSelectedItem().trim();

                    String q1 = "select distinct price from Flight " +
                            "where source='" + source + "' " +
                            "and destination='" + destination + "' " +
                            "and class_name='" + className + "'";

                    ResultSet rs = obj.stm.executeQuery(q1);

                    while (rs.next()) {
                        ch4.add(rs.getString("price"));
                    }

                    rs.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        ch4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ch5.removeAll();

                try {
                    ConnectionClass obj = new ConnectionClass();

                    String source = ch1.getSelectedItem();
                    String destination = ch6.getSelectedItem();
                    String className = ch3.getSelectedItem();
                    String price = ch4.getSelectedItem();
                    String q1 = "select distinct f_code from Flight where source='"+source+"' and destination='"+destination+"' and class_name='"+className+"' and price='"+price+"'";
                    ResultSet rs=obj.stm.executeQuery(q1);
                    while(rs.next()){
                        ch5.add(rs.getString("f_code"));

                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

     ch5.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
             try{
                 ConnectionClass obj = new ConnectionClass();
                 String source = ch1.getSelectedItem();
                 String destination = ch6.getSelectedItem();
                 String className = ch3.getSelectedItem();
                 String price = ch4.getSelectedItem();
                 String fcode= ch5.getSelectedItem();
                 String q1="select distinct f_name from Flight where source='"+source+"' and destination='"+destination+"' and class_name='"+className+"' and price='"+price+"' and f_code='"+fcode+"'";
                 ResultSet rs=obj.stm.executeQuery(q1);
                 while(rs.next()){
                     tf2.setText(rs.getString("f_name"));
                 }

             }
             catch (Exception ex){
                 ex.printStackTrace();
             }
         }
     });

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bt1);
        String tid=tf1.getText();
        String source=ch1.getSelectedItem().trim();
        String destination=ch6.getSelectedItem().trim();
        String class_name=ch3.getSelectedItem().trim();
        String price=ch4.getSelectedItem().trim();
        String fcode=ch5.getSelectedItem().trim();
        String fname=tf2.getText();
        String jdate=tf3.getText();
        String jtime=tf4.getText();
        String username=ch2.getSelectedItem();
        String name=tf5.getText();
        String status="Success!";

        try{
            ConnectionClass obj3 = new ConnectionClass();
            String query= "insert into BookedFlight values('"+tid+"','"+source+"','"+destination+"','"+class_name+"','"+price+"','"+fcode+"','"+fname+"','"+jdate+"','"+jtime+"','"+username+"','"+name+"','"+status+"')";
            int aa=obj3.stm.executeUpdate(query);
            if(aa==1){
                JOptionPane.showMessageDialog(null,"Flight Booked Successfully");
            }
            else{
                JOptionPane.showMessageDialog(null,"Please Enter Details Carefully!");
                this.setVisible(false);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        if (e.getSource()==bt2){
            this.setVisible(false);
        }
    }

    public static void main(String[] args){
        new BookFlight().setVisible(true);
    }
}