import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GameBoard extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    JLabel rubrik = new JLabel("Number Puzzle Game");

    JButton button;
    JButton emptyPlayPiece = new JButton("");

    private int row = 4;
    private int col = row;

    JButton[] arrayOfRandomNumbersForGameBoard;
    JButton[][] puzzlePieces;

    JButton clickedButton = new JButton();

    int clickedButtonRow;
    int clickedButtonColumn;
    int emptyButtonRow;
    int emptyButtonColumn;

    boolean slidePossible;

    int[] gameButtonsOrder = new int[14];

    boolean gameComplete = true;


    GameBoard() {
        mainPanel.setLayout(new BorderLayout());
        topPanel.add(rubrik);
        gamePanel.setLayout(new GridLayout(4, 4));
        bottomPanel.add(newGameButton);

        createRandomLabels();
        createGameBoard();
        //findEmptyButtonInArray();
       // findButtonInArray();
       // checkIfSlideIsPossible();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (JButton button : arrayOfRandomNumbersForGameBoard) {
                if (e.getSource() == button) {
                    clickedButton = button;
                    findButtonInArray();
                    findEmptyButtonInArray();
                    checkIfSlideIsPossible();

                    if(slidePossible){
                        gamePanel.remove(emptyPlayPiece);
                        gamePanel.remove(clickedButton);
                        gamePanel.revalidate();
                        repaint();
                        /*puzzlePieces[emptyButtonRow][emptyButtonColumn] = clickedButton;
                        puzzlePieces[clickedButtonRow][clickedButtonRow] = emptyPlayPiece;*/

                        int indexOfEmptyButton = (emptyButtonRow * 4) + emptyButtonColumn;
                        int indexOfClickedButton = (clickedButtonRow * 4) + clickedButtonColumn;

                        for (int i =0; i < puzzlePieces.length; i++){
                            for (int j =0; j < puzzlePieces[i].length; j++){
                                if (emptyButtonRow == i && emptyButtonColumn == j){
                                    gamePanel.add(clickedButton, indexOfEmptyButton);
                                    gamePanel.revalidate();
                                    gamePanel.repaint();
                                    //puzzlePieces[emptyButtonRow][emptyButtonColumn] = clickedButton;
                                }
                                if (clickedButtonRow == i && clickedButtonColumn==j){
                                    gamePanel.add(emptyPlayPiece, indexOfClickedButton);
                                    gamePanel.revalidate();
                                    gamePanel.repaint();
                                    //puzzlePieces[clickedButtonRow][clickedButtonRow] = emptyPlayPiece;

                                }
                            }
                        }
                        //gamePanel.add(emptyPlayPiece);
                        //gamePanel.add(clickedButton);

                        gamePanel.revalidate();
                        gamePanel.repaint();

                        /*findButtonInArray();
                        findEmptyButtonInArray();
                        checkIfSlideIsPossible();*/

                    }
                }
            }
        }
    };

    private void gamePlay() {
        createRandomLabels();
        createGameBoard();
        while (!gameComplete) {
            findEmptyButtonInArray();
            checkIfSlideIsPossible();
            //swapButtons();
        }

        JOptionPane.showMessageDialog(null, "Du vann!");
    }

    private JButton[] createRandomLabels() {
        arrayOfRandomNumbersForGameBoard = new JButton[16];
        for (int i = 0; i < 15; i++) {
            String value = Integer.toString(i + 1);
            button = new JButton(value);
            //button.addActionListener(al);
            arrayOfRandomNumbersForGameBoard[i] = button;
        }
        emptyPlayPiece.setBackground(Color.white);
        arrayOfRandomNumbersForGameBoard[15] = emptyPlayPiece;
        Collections.shuffle(Arrays.asList(arrayOfRandomNumbersForGameBoard));

        return arrayOfRandomNumbersForGameBoard;
    }

    private JButton[][] create2DArray() {
        puzzlePieces = new JButton[row][col];
        for (int a = 0; a < arrayOfRandomNumbersForGameBoard.length; a++) {
            for (int i = 0; i < puzzlePieces.length; i++) {
                for (int j = 0; j < puzzlePieces[i].length; j++) {
                    puzzlePieces[i][j] = arrayOfRandomNumbersForGameBoard[a];
                    a++;
                }
            }
        }
        return puzzlePieces;
    }

    private JPanel createGameBoard() {
        create2DArray();
        for (JButton[] puzzlePiece : puzzlePieces) {
            for (int i = 0; i < puzzlePiece.length; i++) {
                puzzlePiece[i].addActionListener(al);
                gamePanel.add(puzzlePiece[i]);
            }
        }
        gameComplete = false;
        return gamePanel;
    }


    private void findEmptyButtonInArray() {
        for (int i = 0; i < puzzlePieces.length; i++) {
            for (int j = 0; j < puzzlePieces[i].length; j++) {
                if (puzzlePieces[i][j] == emptyPlayPiece) {
                    emptyButtonRow = i;
                    emptyButtonColumn = j;
                    System.out.println(i + " " +j);
                  //  Component emptyComponent = gamePanel.getComponentAt(emptyPlayPiece.getLocation());
                }
            }
        }
    }

    private void findButtonInArray() {
        for (int i = 0; i < puzzlePieces.length; i++) {
            for (int j = 0; j < puzzlePieces[i].length; j++) {
                if (puzzlePieces[i][j] == clickedButton) {
                    clickedButtonRow = i;
                    clickedButtonColumn = j;
                    System.out.println(i + " " +j);
                }
            }
        }
    }


    private void checkIfSlideIsPossible() {
        //System.out.println("Buttons should be swapped3");
        if (clickedButtonRow == emptyButtonRow) {
            if (clickedButtonColumn == emptyButtonColumn + 1 || clickedButtonColumn == emptyButtonColumn - 1) {
                System.out.println("Buttons should be swapped1");
                slidePossible = true;
                //swapButtons();
            }
        } else if (clickedButtonColumn == emptyButtonColumn) {
            if (clickedButtonRow == emptyButtonRow + 1 ||
                    clickedButtonRow == emptyButtonRow - 1) {
                slidePossible = true;
                System.out.println("Buttons should be swapped2");
                //swapButtons();
            }
        } else {
            System.out.println("cant be swapped");
            slidePossible = false;
        }

    }

    private void swapButtons() {
        /*
            System.out.println("Buttons should be swapped");
            emptyPlayPiece.setText(clickedButton.getText());
            emptyPlayPiece.setBackground(clickedButton.getBackground());
            clickedButton.setText("");
            clickedButton.setBackground(Color.white);
            gamePanel.revalidate();
            gamePanel.repaint();


         */
        //gamePanel.remove(clickedButton);

        /*puzzlePieces[emptyButtonRow][emptyButtonColumn] = clickedButton;
        puzzlePieces[clickedButtonRow][clickedButtonRow] = emptyPlayPiece;*/

           /* puzzlePieces[emptyButtonRow][emptyButtonColumn] = clickedButton;
            puzzlePieces[clickedButtonRow][clickedButtonRow] = emptyPlayPiece;
            gamePanel.revalidate(); */

    }

    private void checkIfGameComplete() {
        int counter = 0;
        for (JButton[] buttons : puzzlePieces) {
            for (JButton button : buttons) {
                if (!button.getText().equals("")) {
                    gameButtonsOrder[counter] = Integer.parseInt(button.getText());
                    counter++;
                }
            }
        }
        for (int i = 0; i < gameButtonsOrder.length - 1; i++) {
            if (gameButtonsOrder[i] > gameButtonsOrder[i + 1])
                gameComplete = false;
        }
        gameComplete = true;
    }


    public static void main(String[] args) {
        GameBoard playGame = new GameBoard();
    }

}

