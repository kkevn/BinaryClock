/*****************************************************
* Author: Kevin Kowalski
* Version: 1.2.1
* Latest Build Date: 06/09/2016
******************************************************
* Create a binary clock
******************************************************/

package binaryclock;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import static java.awt.Window.Type.UTILITY;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

public class BinaryClock extends JFrame {

    private static final int maxWidth = 600, maxHeight = 400;
    
    int hour = Calendar.getInstance().get(Calendar.HOUR);
    private static String hours = pad("" + Calendar.getInstance().get(Calendar.HOUR), 2);
        private static JLabel l_hours = new JLabel(hours);
            private static JLabel[][] grid_h = new JLabel[4][2];
    
    int minute = Calendar.getInstance().get(Calendar.MINUTE);
    private static String minutes = pad("" + Calendar.getInstance().get(Calendar.MINUTE), 2);
        private static JLabel l_minutes = new JLabel(minutes);
            private static JLabel[][] grid_m = new JLabel[4][2];
    
    int second = Calendar.getInstance().get(Calendar.SECOND);
    private static String seconds = pad("" + Calendar.getInstance().get(Calendar.SECOND), 2);
        private static JLabel l_seconds = new JLabel(seconds);
            private static JLabel[][] grid_s = new JLabel[4][2];
        
    public static String time = "[" + hours + ":" + minutes + ":" + seconds + "]";
    
    /*About*/
    private static String title = "Binary Clock";
    static String appName = title + " - Made in Java - " + time;
    
    /*Borders*/
    private static Border b_basic = BorderFactory.createLineBorder(Color.BLACK, 2);
    private static Border b_raised = BorderFactory.createRaisedSoftBevelBorder();
    private static Border b_lowered = BorderFactory.createSoftBevelBorder(1, Color.DARK_GRAY, Color.BLACK);
    private static Border b_none = BorderFactory.createEmptyBorder();
    
    /*Fonts*/
    private static Font f_ampm = new Font("Agency FB", Font.BOLD, 72);
    
    public static String pad(String input, int amount) {
        String padded = input;
        while (padded.length() != amount)
                padded = "0" + padded;
        return padded;
    }
    
    public static String toBinary(int input) {
        String result = "";
        
        int quotient = input;
        int remainder;
        
        while (quotient != 0) {
            remainder = quotient % 2;
            quotient /= 2;
            result += remainder;
        }
        return new StringBuilder(result).reverse().toString();
    }
    
    public static int parse(String num) {
        if (num != null && num.length() > 0) {
            try {
                return Integer.parseInt(num);
            } 
            catch(Exception e) {
                return -1;   //if this fucks up then change to 0
            }
        }
        else return 0;
    }
    
        //-------------
        // 0,0 || 0,1    8 = 2^3
        //-------------
        // 1,0 || 1,1    4 = 2^2
        //-------------
        // 2,0 || 2,1    2 = 2^1
        //-------------
        // 3,0 || 3,1    1 = 2^0
        //-------------
    public static void setHours(JLabel[][] grid, String hour) {
        String tens = "00", ones = "00";
        
        int am_pm = Calendar.getInstance().get(Calendar.AM_PM);
        
        grid[0][0].setFont(f_ampm);
        grid[0][0].setHorizontalAlignment(SwingConstants.CENTER);
        //grid[0][0].setVerticalAlignment(SwingConstants.CENTER);
        
        if (am_pm == Calendar.PM) {
            grid[0][0].setText("PM");
            
            if (hour.equals("0"))
                hour = "12";
        }
        else
            grid[0][0].setText("AM");
        
        if (Integer.parseInt(hour) > 9) {
            tens = hour.substring(0, 1);
            ones = hour.substring(1);
        }
        else {
            tens = "0";
            ones = hour.substring(0, 1);
        }
        
        clearGrid(grid);
        for (int i = 0; i < 4; i++) {
            if (pad(toBinary(parse(tens)), 4).charAt(i) == '1') {
                grid[i][0].setBackground(Color.RED);
            }  
        }
        for (int i = 0; i < 4; i++) {
            if (pad(toBinary(parse(ones)), 4).charAt(i) == '1') {
                grid[i][1].setBackground(Color.RED);
            }  
        }
        grid[0][0].setBackground(Color.WHITE);
        grid[1][0].setBackground(Color.WHITE);
        //grid[2][0].setBackground(Color.WHITE);
    }
    
    public static void setMinutes(JLabel[][] grid, String minute) {
        String tens = "00", ones = "00";
        
        if (Integer.parseInt(minute) > 9) {
            tens = minute.substring(0, 1);
            ones = minute.substring(1);
        }
        else {
            tens = "0";
            ones = minute.substring(0, 1);
        }
        
        clearGrid(grid);
        for (int i = 0; i < 4; i++) {
            if (pad(toBinary(parse(tens)), 4).charAt(i) == '1') {
                grid[i][0].setBackground(Color.GREEN);
            }
        }
        for (int i = 0; i < 4; i++) {
            if (pad(toBinary(parse(ones)), 4).charAt(i) == '1') {
                grid[i][1].setBackground(Color.GREEN);
            }
        }
        grid[0][0].setBackground(Color.WHITE);
    }
    
    public static void setSeconds(JLabel[][] grid, String second) {
        String tens = "00", ones = "00";
        
        if (Integer.parseInt(second) > 9) {
            tens = second.substring(0, 1);
            ones = second.substring(1);
        }
        else {
            tens = "0";
            ones = second.substring(0, 1);
        }
        
        clearGrid(grid);
        for (int i = 0; i < 4; i++) {
            if (pad(toBinary(parse(tens)), 4).charAt(i) == '1') {
                grid[i][0].setBackground(Color.BLUE);
            }  
        }
        for (int i = 0; i < 4; i++) {
            if (pad(toBinary(parse(ones)), 4).charAt(i) == '1') {
                grid[i][1].setBackground(Color.BLUE);
            }   
        }
        grid[0][0].setBackground(Color.WHITE);
    }
    
    public static void fillGrid(JLabel[][] grid, JPanel panel) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new JLabel();
                grid[i][j].setBorder(b_raised);
                grid[i][j].setBackground(Color.LIGHT_GRAY);
                grid[i][j].setOpaque(true);
                panel.add(grid[i][j]);
            }
        }
    }
    
    public static void clearGrid(JLabel[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].setBackground(Color.LIGHT_GRAY);
                grid[i][j].setBorder(b_raised);
            }
        }
    }
    
    public static void checkGrid(JLabel[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getBackground() == Color.LIGHT_GRAY)
                    update();
            }
        }
    }
    
    public static void update() {
        l_hours.setText(pad("" + Calendar.getInstance().get(Calendar.HOUR), 2));
        l_minutes.setText(pad("" + Calendar.getInstance().get(Calendar.MINUTE), 2));
        l_seconds.setText(pad("" + Calendar.getInstance().get(Calendar.SECOND), 2));
        
        setHours(grid_h, "" + Calendar.getInstance().get(Calendar.HOUR));
        setMinutes(grid_m, "" + Calendar.getInstance().get(Calendar.MINUTE));
        setSeconds(grid_s, "" + Calendar.getInstance().get(Calendar.SECOND));
    }
    
    public BinaryClock() {
        //container pane
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(0, 3));
        pane.setBackground(Color.LIGHT_GRAY);
        
        JPanel p_hours = new JPanel();
            p_hours.setLayout(new GridLayout(4, 2));
            p_hours.setBorder(b_basic);
                 fillGrid(grid_h, p_hours);
                    pane.add(p_hours);
        JPanel p_mins = new JPanel();
            p_mins.setLayout(new GridLayout(4, 2));
            p_mins.setBorder(b_basic);
                fillGrid(grid_m, p_mins);
                    pane.add(p_mins);
        JPanel p_secs = new JPanel();
            p_secs.setLayout(new GridLayout(4, 2));
            p_secs.setBorder(b_basic);
                fillGrid(grid_s, p_secs);
                    pane.add(p_secs);
        
        Insets insets = pane.getInsets();
            int i_left = insets.left;
            int i_right = insets.right;
                int i_horiz = i_left + i_right;
            int i_top = insets.top;
            int i_bottom = insets.bottom;
                int i_vert = i_top + i_bottom;
                
        /*[binary clock] JFrame Settings*/
            setTitle(appName);
            //setUndecorated(true);
            setSize(i_horiz + maxWidth, i_vert + maxHeight);
            setResizable(false);
            setLocationRelativeTo(null);;
            setType(UTILITY);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        /*Set Look&Feel*/
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                // Set System L&F
            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                // handle exception
            }

        /*Class Declarations*/
            BinaryClock binaryclock = new BinaryClock();
            //new BinaryClock();
            ActionListener al = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    update();
                    //System.out.print(Calendar.getInstance().get(Calendar.SECOND) + "-");
                }
            };
            
            Timer timer = new Timer(1000, al);
            timer.start();
    }
}
