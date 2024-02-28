package Game;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.w3c.dom.events.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class BattleshipView extends JFrame{
    public JButton button[][];
    private JLabel label = new JLabel("");
    public JButton button2[][];
    private JFrame frame = new JFrame();
    private ImageIcon image = new ImageIcon("./canvas.png");
    private MyPanel panel1 = new MyPanel(image);
    private JPanel panel2 = new JPanel();

    public BattleshipView()
    {
        button = new JButton[7][8];
        button2 = new JButton[7][8];
        panel1.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        panel1.setLayout(new GridLayout(7,8));
        panel2.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        panel2.setLayout(new GridLayout(7,8));

        //initializes buttons and adds them to panel
        for(int row = 0; row < 7; row++)
        {
            for(int col = 0; col < 8; col++)
            {
                button[row][col] = new JButton();
                button2[row][col] = new JButton();
                panel1.add(button[row][col]);
                panel2.add(button2[row][col]);
            }
        }
        frame.setTitle("Battleship");
        frame.add(panel1,BorderLayout.SOUTH);
        frame.add(panel2,BorderLayout.NORTH);
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
			//prevPoint = ((Object) event).getPoint();
		}	
	}
    private class DragListener extends MouseMotionAdapter{
    	public void mouseDragged(MouseEvent event) {
    		//Point currPoint = ((Object) event).getPoint();
    		//int dx = (int) (currPoint.getX() - prevPoint.getX());
    		//int dy = (int) (currPoint.getY() - prevPoint.getY());
    		
    		//imageUpperLeft.translate(dx, dy);
    		//prevPoint = currPoint;
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
    public int[] buttonPosition(JButton buttn)
        {
            int position [] = new int[2];

            for(int row = 0; row < 3; row++)
            {
                for(int col = 0; col < 3; col++)
                {
                    if(buttn==button[row][col])
                    {
                        position[0] = row;
                        position[1] = col;
                    }
                }
            }
            return position;
        }
}

