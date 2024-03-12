//package GameTest;


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
		updateViewFromModel();
		updateOpponentViewFromModel();
	}

	public void buttonInitializer() {
		for(int row = 0; row < view.opponentGrid.length; row++) {
			for(int col = 0; col < view.opponentGrid[row].length; col++) {
				view.opponentGrid[row][col].addActionListener(this);
			}
		}
	}
	



	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton buttonClicked = (JButton)e.getSource();
		int[] position = view.buttonPosition(buttonClicked);
		int row = position[0];
		int col = position[1];
		

		boolean hit = model.cellUsed(row, col);
		model.markOpponentBoard(row, col, hit);
		view.updateButtonHit(row, col, hit); // Update the view based on hit or miss

		Ship recentlySunkShip = model.getRecentlySunkShip();
		if (hit) {
			if (recentlySunkShip != null) {
				view.displayMessage(recentlySunkShip.getName() + " has been sunk!");
			} else {
				view.displayMessage("Hit!");
			}
		} else {
			view.displayMessage("Miss!");
		}

		updateViewFromModel();
		updateOpponentViewFromModel();
		// Check if the game is over and display a message if so
		if (model.areAllShipsSunk()) {
			view.displayMessage("Game over!");
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
