package AMS;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class HomePage extends JFrame implements ActionListener{
    Font f,f1,f2;
    JLabel l1,l2;

    HomePage(){
        super("AirLines HomePage");
        setLocation(0,0);
        setSize(1550,801);

        f=new Font("Arial",Font.BOLD,20);
        f2=new Font("Arial",Font.BOLD,35);
        f1=new Font("Arial",Font.BOLD,19);

        ImageIcon ic=new ImageIcon(ClassLoader.getSystemResource("AMS/Icons/Homepage.png"));
        Image img=ic.getImage().getScaledInstance(1550,801,Image.SCALE_SMOOTH);
        ImageIcon ic1=new ImageIcon(img);
        l1=new JLabel(ic1);

        JMenuBar m1=new JMenuBar();
        JMenu men1=new JMenu("Passenger Profile");
        JMenuItem mi1=new JMenuItem("Add Passenger");
        JMenuItem mi2=new JMenuItem("View Passenger");
        men1.add(mi1);
        men1.add(mi2);
        m1.add(men1);

        JMenu men2=new JMenu("Manage Passenger");
        JMenuItem mi3=new JMenuItem("Update Passenger");
        men2.add(mi3);
        m1.add(men2);

        JMenu men3=new JMenu("Flights");
        JMenuItem mi4=new JMenuItem("Book Flight");
        JMenuItem mi5=new JMenuItem("View Booked Flights");
        men3.add(mi4);
        men3.add(mi5);
        m1.add(men3);

        JMenu men4=new JMenu("Flight Details");
        JMenuItem mi6=new JMenuItem("Journey Details");
        JMenuItem mi7=new JMenuItem("Flight Zone");
        men4.add(mi6);
        men4.add(mi7);
        m1.add(men4);

        JMenu men5=new JMenu("Cancellation");
        JMenuItem mi8=new JMenuItem("Cancel Flight");
        JMenuItem mi9=new JMenuItem("View Cancelled Flights");
        men5.add(mi8);
        men5.add(mi9);
        m1.add(men5);

        JMenu men6=new JMenu("Billing Details");
        JMenuItem mi10=new JMenuItem("View Billing Details");
        men6.add(mi10);
        m1.add(men6);

        JMenu men7=new JMenu("Logout");
        JMenuItem mi11=new JMenuItem("Log Out");
        men7.add(mi11);
        m1.add(men7);

        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);
        mi7.addActionListener(this);
        mi8.addActionListener(this);
        mi9.addActionListener(this);
        mi10.addActionListener(this);
        mi11.addActionListener(this);

        setJMenuBar(m1);
        add(l1);

    }
    public void actionPerformed(ActionEvent e){
        String cmd=e.getActionCommand();
        if(cmd.equals("Add Passenger")){
            new PassengerDetails().setVisible(true);
        }
        else if(cmd.equals("View Passenger")){
            new ViewPassenger().setVisible(true);
        }
//        else if(cmd.equals("Update Passenger")){
//            new UpdatePassenger().setVisible(true);
//        }
        else if(cmd.equals("Book Flight")){
            new BookFlight().setVisible(true);
        }
        else if(cmd.equals("View Booked Flights")){
            new ViewBookedFlight().setVisible(true);
        }
//        else if(cmd.equals("Journey Details")){
//            new FlightJourney().setVisible(true);
//        }
//        else if(cmd.equals("Flight Zone")){
//            new FlightZone().setVisible(true);
//        }
//        else if(cmd.equals("Cancel Flight")){
//            new CancelFlight.setVisible(true);
//        }
//        else if(cmd.equals("View Cancelled Flights")){
//            new ViewCancelledFlight().setVisible(true);
//        }
//        else if(cmd.equals("View Billing Details")){
//            new CheckBill.setVisible(true);
//        }
        else if(cmd.equals("Logout")){
            System.exit(0);
        }
    }
    public static void main(String[] args){
        new HomePage().setVisible(true);
    }
}
