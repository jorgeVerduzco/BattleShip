//package GameTest;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;



public class BattleshipView extends JFrame{
    private final int GRIDSIZE = 10;
    public JLabel userGrid[][];
    public JButton opponentGrid[][];
    public JButton randomButton;   
    private ImageIcon image = new ImageIcon("C:\\Users\\jorge\\Desktop\\COSC330\\GameTest\\canvas.png");
    private JFrame frame = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();

    public BattleshipView()
    {
        
        randomButton = new JButton();
        userGrid = new JLabel[GRIDSIZE][GRIDSIZE];
        opponentGrid = new JButton[GRIDSIZE][GRIDSIZE];
        frame.getContentPane().setPreferredSize(new Dimension(400,600));
        panel1.setPreferredSize(new Dimension(100,250));
        panel2.setPreferredSize(new Dimension(100,250));
        panel3.setPreferredSize(new Dimension(100,100));
        panel1.setLayout(new GridLayout(10,10));
        panel2.setLayout(new GridLayout(10,10));
        //label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //initializes buttons and adds them to panel
        for(int row = 0; row < GRIDSIZE; row++)
        {
            for(int col = 0; col < GRIDSIZE; col++)
            {
                userGrid[row][col] = new JLabel();
                opponentGrid[row][col] = new JButton();
                userGrid[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                opponentGrid[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel1.add(userGrid[row][col]);
                panel2.add(opponentGrid[row][col]);
                //panel1.add(label);
                //panel2.add(label[row][col]);
            }
        }
        frame.setTitle("Battleship");
        frame.add(panel1,BorderLayout.SOUTH);
        frame.add(panel2,BorderLayout.NORTH);
        frame.add(panel3,BorderLayout.CENTER);
        //panel2.setVisible(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
     public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    

    
    public void updateButtonHit(int row,int col, boolean hit)
    {
        if(hit == true)
        {
            opponentGrid[row][col].setBackground(Color.RED);
        }

        else
        {
            opponentGrid[row][col].setBackground(Color.WHITE);
        }

    }
    


    //function will return row and column of button clicked
    public int[] buttonPosition(JButton button)
        {
            int position [] = new int[2];

            for(int row = 0; row < GRIDSIZE; row++)
            {
                for(int col = 0; col < GRIDSIZE; col++)
                {
                    if(button==opponentGrid[row][col])
                    {
                        position[0] = row;
                        position[1] = col;
                    }
                }
            }
            return position;
        }
}
