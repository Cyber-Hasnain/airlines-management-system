package AMS;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
public class ViewPassenger extends JFrame {
    JTable t;
    String x[]={"Username","Age","DOB", "Name", "Address", "Phone", "Email", "Nationality"," Gender", "Passport"};
    String y[][]=new String[20][10];
    int i=0,j=0;
    Font f;
    ViewPassenger(){
        super("All Passenger Records");
        setSize(1300,400);
        setLocation(0,10);
        f=new Font("Times New Roman",Font.BOLD,18);

        try{
            ConnectionClass obj=new ConnectionClass();
            String q="select * from passenger";
            ResultSet rest=obj.stm.executeQuery(q);
            while(rest.next()){
                y[i][j++]=rest.getString("Username");
                y[i][j++]=rest.getString("Name");
                y[i][j++]=rest.getString("Age");
                y[i][j++]=rest.getString("DOB");
                y[i][j++]=rest.getString("Address");
                y[i][j++]=rest.getString("Phone");
                y[i][j++]=rest.getString("Email");
                y[i][j++]=rest.getString("Nationality");
                y[i][j++]=rest.getString("Gender");
                y[i][j++]=rest.getString("Passport");
                i++;
                j=0;
            }
            t=new JTable(y,x);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        t.setFont(f);
        t.setBackground(Color.BLACK);
        t.setForeground(Color.WHITE);
        JScrollPane sp=new JScrollPane(t);

        add(sp);

    }
    public static void main(String[] args){
        new ViewPassenger().setVisible(true);
    }
}
