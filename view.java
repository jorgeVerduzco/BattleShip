import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class view {
    public static BattleshipModel serverModel = new BattleshipModel();
    public static JButton[][] shipPlacements, oppPlacements;
    public static JFrame gameWindow;
    public static int shipPartsRemaining = 17;
    public static int enemyShipPartsRemaining = 17;
    
    public static void main (String[] args) {
        shipPlacements = new JButton[10][10];
        oppPlacements = new JButton[10][10];
        gameWindow = new JFrame("Battleship");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton randomlyPlace = new JButton("Randomly Place Ships");

        JPanel startWindow = new JPanel();
        startWindow.add(randomlyPlace);
        gameWindow.add(startWindow);

        gameWindow.pack();
        gameWindow.setMinimumSize(new Dimension(800, 800));
        gameWindow.setMaximumSize(new Dimension(800, 800));
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setResizable(false);
        gameWindow.setVisible(true);

        randomlyPlace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                serverModel.placeShipsRandomlyOnUserBoard();
                serverModel.printUserBoard();

                JFrame game = new JFrame("Game");
                game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                game.setLayout(new BorderLayout());

                JPanel newPanel = new JPanel();

                JPanel userBoardPanel = makeBoard(serverModel.getUserBoard(), shipPlacements);
                JPanel opponentBoardPanel = makeBoard(serverModel.getOpponentBoard(), oppPlacements);
                newPanel.add(userBoardPanel);
                newPanel.add(opponentBoardPanel);
                game.add(newPanel, BorderLayout.CENTER);
                game.pack();
                game.setLocationRelativeTo(null);
                game.setVisible(true);
                startWindow.setVisible(false);
            }
        });

        serverModel.server.runServer();
    }

    public static JPanel makeBoard(char[][] gameBoard, JButton[][] placements) {
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(10, 10));
        board.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        board.setPreferredSize(new Dimension(400, 400));
        

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40 , 40));
                button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                if (Character.isUpperCase(gameBoard[i][j])) {
                    button.setBackground(Color.BLACK);
                    button.setOpaque(true);
                    button.setBorderPainted(true);
                } else {
                    button.setBackground(Color.CYAN);
                    button.setOpaque(true);
                    button.setBorderPainted(true);
                }
            

            final int row = i;
            final int col = j;
            button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        serverModel.sendShot(row, col);
                        serverModel.receiveShot();
                        button.removeActionListener(this);

                        shipPartsRemaining = 17;
                        enemyShipPartsRemaining = 17;
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (serverModel.getOpponentBoard()[i][j] == 'X') {
                                    oppPlacements[i][j].setBackground(Color.RED);
                                    enemyShipPartsRemaining--;
                                }

                                if (serverModel.getUserBoard()[i][j] == 'X') {
                                    shipPlacements[i][j].setBackground(Color.RED);
                                    shipPartsRemaining--;
                                    System.out.printf("%d\n", shipPartsRemaining);
                                }

                                if (serverModel.getOpponentBoard()[i][j] == '*') {
                                    oppPlacements[i][j].setBackground(Color.WHITE);
                                }

                                if (serverModel.getUserBoard()[i][j] == '*') {
                                    shipPlacements[i][j].setBackground(Color.WHITE);
                                }

                                if (enemyShipPartsRemaining == 0) {
                                    System.out.println("You win!");
                                    // Timer timer = new Timer(5000, new ActionListener() {
                                    //     @Override
                                    //     public void actionPerformed(ActionEvent evt) {
                                    //         System.exit(0);
                                    //     }
                                    // });
                                    // timer.setRepeats(false);
                                    // timer.start();
    
                                    // System.exit(0);
                                } else if (shipPartsRemaining == 0) {
                                    System.out.println("You lose!");
                                    // Timer timer = new Timer(5000, new ActionListener() {
                                    //     @Override
                                    //     public void actionPerformed(ActionEvent evt) {
                                    //         System.exit(0);
                                    //     }
                                    // });
                                    // timer.setRepeats(false);
                                    // timer.start();
    
                                    // System.exit(0);
                                }
                            }
                        }
                    }
                });

                board.add(button);
                placements[i][j] = button;
            }
        }
        return board;
    }
}
