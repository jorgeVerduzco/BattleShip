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
		buttonInitializer();
	}

	public void buttonInitializer() {
		for(int row = 0; row < view.opponentGrid.length; row++) {
			for(int col = 0; col < view.opponentGrid[row].length; col++) {
				view.opponentGrid[row][col].addActionListener(this);
			}
		}
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

	if(!model.cellUsed(row, col)) {
		return;
	} 

	boolean hit = model.markOpponentBoard(row, col);

	if(hit) {
		Ship sunkShip = model.getRecentlySunkShip();
		if(sunkShip != null) {
			view.displayMessage(sunkShip.getName() + " has been sunk");
		} else {
			view.displayMessage("Hit!");
		}
	} else {
		view.displayMessage("Miss!");
	}
	
	updateViewFromModel();
	updateOpponentViewFromModel();

	if(model.isGameOver()) {
		JOptionPane.showMessageDialog(view, "Game over, you've won");
	}
}
 private void updateViewFromModel() {
	char[][] userBoard = model.getUserBoard();
	for(int row = 0; row < userBoard.length; row++) {
		for(int col = 0; col < userBoard[row].length; col++) {
			if(userBoard[row][col] == 'S') {
				view.userGrid[row][col].setIcon(new ImageIcon("C:\\Users\\jorge\\Desktop\\COSC330\\GameTest\\canvas.png"));
			} else if(userBoard[row][col] == 'H') {
				view.userGrid[row][col].setBackground(Color.RED);
			} else if(userBoard[row][col] == 'M') {
				view.userGrid[row][col].setBackground(Color.WHITE);
			} else {
				view.userGrid[row][col].setBackground(Color.BLUE);
			}
		}
	}
 }

 private void updateOpponentViewFromModel() {
	char[][] opponentBoard = model.getOpponentBoard();
	for(int row = 0; row < opponentBoard.length; row++) {
		for(int col = 0; col < opponentBoard.length; col++) {
			if(opponentBoard[row][col] == 'H') {
				view.opponentGrid[row][col].setBackground(Color.RED);
			} else if(opponentBoard[row][col] == 'M') {
				view.opponentGrid[row][col].setBackground(Color.WHITE);
			}
}	
}
 }
}


