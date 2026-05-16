package AMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class ViewBookedFlight extends JFrame {
    JTable t;
    String x[]={"Ticket ID","Source","Destination","Class","Price","Flight Code","Flight Name","Journey Date","Journey Time","UserName","Name","Status"};
    String y[][]=new String[20][12];
    int i=0,j=0;
    Font f;

    ViewBookedFlight() {
        super("Flights Status");
        setSize(1300,400);
        setLocation(0,10);
        f=new Font("Arial",Font.BOLD,18);

        try{
            ConnectionClass obj=new ConnectionClass();
            String q="select * from BookedFlight";
            ResultSet rs=obj.stm.executeQuery(q);
            while(rs.next()){
                y[i][j++]=rs.getString("tid");
                y[i][j++]=rs.getString("source");
                y[i][j++]=rs.getString("destination");
                y[i][j++]=rs.getString("class_name");
                y[i][j++]=rs.getString("price");
                y[i][j++]=rs.getString("fcode");
                y[i][j++]=rs.getString("fname");
                y[i][j++]=rs.getString("journey_date");
                y[i][j++]=rs.getString("journey_time");
                y[i][j++]=rs.getString("username");
                y[i][j++]=rs.getString("name");
                y[i][j++]=rs.getString("status");
                i++;
                j=0;
            }
            t=new JTable(y,x);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        t.setFont(f);
        t.setBackground(Color.BLACK);
        t.setForeground(Color.WHITE);
        JScrollPane sp=new JScrollPane(t);
        add(sp);

        }
    public static void main(String[] args) {
        new ViewBookedFlight().setVisible(true);

    }


}
