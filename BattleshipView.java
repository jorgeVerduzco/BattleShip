package GameTest;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

public class BattleshipView extends JFrame{
    private final int GRIDSIZE = 10;
    private JLabel userGrid[][];
    public JLabel openentGrid[][];
    private JButton randomButton;   
    private ImageIcon image = new ImageIcon("C:\\Users\\jorge\\Desktop\\COSC330\\GameTest\\canvas.png");
    private JFrame frame = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();

    public BattleshipView()
    {
        
        randomButton = new JButton();
        userGrid = new JLabel[GRIDSIZE][GRIDSIZE];
        openentGrid = new JLabel[GRIDSIZE][GRIDSIZE];
        frame.getContentPane().setPreferredSize(new Dimension(400,600));
        panel1.setPreferredSize(new Dimension(100,200));
        panel2.setPreferredSize(new Dimension(100,200));
        panel1.setLayout(new GridLayout(10,10));
        panel2.setLayout(new GridLayout(10,10));
        //label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //initializes buttons and adds them to panel
        for(int row = 0; row < GRIDSIZE; row++)
        {
            for(int col = 0; col < GRIDSIZE; col++)
            {
                userGrid[row][col] = new JLabel();
                openentGrid[row][col] = new JLabel();
                userGrid[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                openentGrid[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel1.add(userGrid[row][col]);
                panel2.add(openentGrid[row][col]);
                //panel1.add(label);
                //panel2.add(label[row][col]);
            }
        }
        frame.setTitle("Battleship");
        frame.add(panel1,BorderLayout.NORTH);
        frame.add(panel2,BorderLayout.SOUTH);
        //panel2.setVisible(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    class MyPanel extends JPanel{
	ImageIcon image;
	Point imageUpperLeft, prevPoint;
	
	MyPanel(ImageIcon imageIcon){
		image = imageIcon;
		imageUpperLeft = new Point(100,100);
		prevPoint = imageUpperLeft;
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		image.paintIcon(this, g, (int) imageUpperLeft.getX(), (int) imageUpperLeft.getY());
	}
	private class ClickListener extends MouseAdapter{
		public void mousePressed(MouseEvent event) {
			prevPoint = (event).getPoint();
		}	
	}
    private class DragListener extends MouseMotionAdapter{
    	public void mouseDragged(MouseEvent event) {
    		Point currPoint = (event).getPoint();
    		int dx = (int) (currPoint.getX() - prevPoint.getX());
    		int dy = (int) (currPoint.getY() - prevPoint.getY());
    		
    		imageUpperLeft.translate(dx, dy);
    		prevPoint = currPoint;
    		repaint();  		
    	}
	}
}

class MyFrame extends JFrame {

	MyFrame(ImageIcon imageIcon){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		MyPanel myPanel = new MyPanel(imageIcon);
		myPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		myPanel.setSize(imageIcon.getIconHeight(), imageIcon.getIconWidth());
		this.add(myPanel);
		this.setSize(imageIcon.getIconHeight()*2, imageIcon.getIconWidth()*2);
		this.setBackground(Color.CYAN);
		this.setVisible(true);
	}
}

    //function will return row and column of button clicked
    public int[] buttonPosition(JLabel label)
        {
            int position [] = new int[2];

            for(int row = 0; row < 7; row++)
            {
                for(int col = 0; col < 8; col++)
                {
                    if(label==openentGrid[row][col])
                    {
                        position[0] = row;
                        position[1] = col;
                    }
                }
            }
            return position;
        }
}
