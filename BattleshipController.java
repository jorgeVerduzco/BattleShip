package BattleshipTest;

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
import java.util.List;
import javax.swing.JButton;
import java.io.IOException;

public class BattleshipController implements ActionListener{
	private BattleshipModel model;
	private BattleshipView view;

	public BattleshipController(BattleshipModel model, BattleshipView view) {
		this.model = model;
		this.view = view;
		buttonInitializer();
		updateViewFromModel();
		updateOpponentViewFromModel();
	}

	public void buttonInitializer() {
		for(int row = 0; row < view.opponentGrid.length; row++) {
			for(int col = 0; col < view.opponentGrid[row].length; col++) {
                JButton button = view.opponentGrid[row][col];
                button.addActionListener(this);
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

   char cellState = model.getOpponentBoard()[row][col];
   if(cellState == ' ' || cellState == 'S') {
    boolean hit = model.cellUsed(row, col);

    if(hit) {
        view.displayMessage("Hit!");
    } else {
        view.displayMessage("Miss");
    }

    List<Ship> sunkShips = model.getAndClearRecentlySunkShips();
    for(Ship sunkShip : sunkShips) {
        view.displayMessage(sunkShip.getName() + " has been sunk");
    }
    if(model.areAllShipsSunk()) {
        view.displayMessage("Game over, you won");
    }
   }else {
    view.displayMessage("Position already hit");
   }

   refreshGameBoard();
}

private void refreshGameBoard() {
    for(int row = 0; row < model.getOpponentBoard().length; row++) {
        for(int col = 0; col < model.getOpponentBoard()[row].length; col++) {
            char cell = model.getOpponentBoard()[row][col];
            JButton button = view.opponentGrid[row][col];
            if(cell == 'H') {
                button.setBackground(Color.RED);
            }else if(cell == 'M') {
                button.setBackground(Color.WHITE);
            }
        }
    }
}
private void updateViewFromModel() {
   char[][] opponentBoard = model.getOpponentBoard();
   for(int row = 0; row < opponentBoard.length; row++) {
    for(int col = 0; col < opponentBoard[row].length; col++) {
        JButton cellButton = view.opponentGrid[row][col];
        switch(opponentBoard[row][col]) {
            case 'H' :
            cellButton.setBackground(Color.RED);
            break;
            case 'M' :
            cellButton.setBackground(Color.WHITE);
            break;
            case 'S' :
            case ' ' :
            default: 
            cellButton.setBackground(Color.BLUE);
            break;
        }
    }
}
    model.getAndClearRecentlySunkShips().forEach(sunkShip -> view.displayMessage(sunkShip.getName() + " has been sunk")
    );
    if(model.areAllShipsSunk()) {
        view.displayMessage("All ships are sunk! You won!");
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