package GameTest;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattleshipController implements ActionListener{
	private BattleshipModel model;
	private BattleshipView view;

	public BattleshipController(BattleshipModel model, BattleshipView view) {
		this.model = model;
		this.view = view;
		//this.model.placeRandom();
		buttonInitializer();
	}

	public void buttonInitializer() {
		for(int row = 0; row < view.openentGrid.length; row++) {
			for(int col = 0; col < view.openentGrid[row].length; col++) {
				//view.userGrid[row][col].addActionListener(this);
				view.openentGrid[row][col].addActionListener(this);
			}
		}
	}
	//public BattleshipModel model = new BattleshipModel();
	//public BattleshipView view = new BattleshipView();
	//public BattleshipController(BattleshipModel model, BattleshipView view)
	//{
		//this.model = model;
		//this.view = view;
	//}

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
			prevPoint = event.getPoint();
		}	
	}
    private class DragListener extends MouseMotionAdapter{
    	public void mouseDragged(MouseEvent event) {
    		Point currPoint = event.getPoint();
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
		this.setBackground(Color.GRAY);
		this.setVisible(true);
	}
}
@Override
public void actionPerformed(ActionEvent e) {
	JButton buttonClicked = (JButton)e.getSource();
	int[] position = view.buttonPosition(buttonClicked);
	int row = position[0];
	int col = position[1];
	boolean hit = model.cellUsed(row, col);

	if(hit == true) {
		model.markOpponentBoard(row, col, hit);
		updateButtonBasedOnModel(buttonClicked, row, col,hit);
	
		if(model.isGameOver()) {
			JOptionPane.showMessageDialog(view, "Game over, you've won");
		}
	}
	else if(hit == false)
	{
		model.markOpponentBoard(row, col, hit);
		updateButtonBasedOnModel(buttonClicked, row, col, hit);
	
		//if(model.checkWin()) {
		//	JOptionPane.showMessageDialog(view, "Game over, you've won");
		//}
	}
}
private void updateButtonBasedOnModel(JButton button, int row, int col,boolean hit) 
{
	if(hit == true)
	{
		button.setBackground(Color.RED);
	}
	else{
		button.setBackground(Color.WHITE);
	}
	
}

}


