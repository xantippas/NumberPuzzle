import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

public class GameBoard extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    JLabel title = new JLabel("Number Puzzle Game");

    JButton button;
    JButton emptyPlayPiece = new JButton("");
    JButton clickedButton = new JButton();

    int row = 4;
    int col = row;

    JButton[] arrayOfRandomNumbersForGameBoard;
    JButton[][] gamePieces;

    int clickedButtonRow;
    int clickedButtonColumn;
    int emptyButtonRow;
    int emptyButtonColumn;

    boolean swapPossible;

    boolean gameComplete = false;

    GameBoard() {
        mainPanel.setLayout(new BorderLayout());
        topPanel.add(title);
        gamePanel.setLayout(new GridLayout(4, 4));
        newGameButton.addActionListener(newGameListener);
        bottomPanel.add(newGameButton);

        //createShuffledArrayOfJButtons();
        //createGameBoard();
        winningGameDemo();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JButton[] createShuffledArrayOfJButtons() {
        arrayOfRandomNumbersForGameBoard = new JButton[16];
        for (int i = 0; i < 15; i++) {
            String value = Integer.toString(i + 1);
            button = new JButton(value);
            arrayOfRandomNumbersForGameBoard[i] = button;
        }

        emptyPlayPiece.setBackground(Color.white);
        arrayOfRandomNumbersForGameBoard[15] = emptyPlayPiece;
        Collections.shuffle(Arrays.asList(arrayOfRandomNumbersForGameBoard));

        if (!isSolvableBoard()){
            createShuffledArrayOfJButtons();
        }

        return arrayOfRandomNumbersForGameBoard;
    }

    //https://ssaurel.medium.com/developing-a-15-puzzle-game-of-fifteen-in-java-dfe1359cc6e3
    public boolean isSolvableBoard(){
        int[] checkIfSolvableArray = new int[16];

        for (int i =0; i < arrayOfRandomNumbersForGameBoard.length; i++){
            if (!arrayOfRandomNumbersForGameBoard[i].getText().equals("")){
                checkIfSolvableArray[i] = Integer.parseInt(arrayOfRandomNumbersForGameBoard[i].getText());
            }
        }

        int countInversions = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < i; j++) {
                if (checkIfSolvableArray[j] > checkIfSolvableArray[i])
                    countInversions++;
            }
        }

        return countInversions % 2 == 0;
    }

    private JButton[][] create2DArrayFromShuffledJButtonArray() {
        gamePieces = new JButton[row][col];

        for (int a = 0; a < arrayOfRandomNumbersForGameBoard.length; a++) {
            for (int i = 0; i < gamePieces.length; i++) {
                for (int j = 0; j < gamePieces[i].length; j++) {
                    gamePieces[i][j] = arrayOfRandomNumbersForGameBoard[a];
                    a++;
                }
            }
        }

        return gamePieces;
    }

    private JPanel createGameBoard() {
        create2DArrayFromShuffledJButtonArray();

        for (JButton[] puzzlePiece : gamePieces) {
            for (int i = 0; i < puzzlePiece.length; i++) {
                puzzlePiece[i].addActionListener(al);
                gamePanel.add(puzzlePiece[i]);
            }
        }
        gameComplete = false;

        return gamePanel;
    }

    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (JButton button : arrayOfRandomNumbersForGameBoard) {
                if (e.getSource() == button) {
                    clickedButton = button;
                    findClickedButtonInArray();
                    findEmptyButtonInArray();
                    checkIfSwapIsPossible();

                    if(swapPossible){
                        swapButtons();
                        checkIfGameComplete();

                        if (gameComplete){
                            JOptionPane.showMessageDialog(null, "Grattis! Du har vunnit!","Winner", JOptionPane.PLAIN_MESSAGE);
                        }

                    }

                }
            }
        }
    };

    ActionListener newGameListener = e -> {
        if (e.getSource()==newGameButton){
            gamePanel.removeAll();
            createShuffledArrayOfJButtons();
            createGameBoard();
            gamePanel.revalidate();
        }
    };

    public void findEmptyButtonInArray() {
        for (int i = 0; i < gamePieces.length; i++) {
            for (int j = 0; j < gamePieces[i].length; j++) {
                if (gamePieces[i][j] == emptyPlayPiece) {
                    emptyButtonRow = i;
                    emptyButtonColumn = j;
                }
            }
        }
    }

    public void findClickedButtonInArray() {
        for (int i = 0; i < gamePieces.length; i++) {
            for (int j = 0; j < gamePieces[i].length; j++) {
                if (gamePieces[i][j] == clickedButton) {
                    clickedButtonRow = i;
                    clickedButtonColumn = j;
                }
            }
        }
    }

    public void checkIfSwapIsPossible() {
        if (clickedButtonRow == emptyButtonRow) {
            if (clickedButtonColumn == emptyButtonColumn + 1 || clickedButtonColumn == emptyButtonColumn - 1) {
                swapPossible = true;
            }
        } else if (clickedButtonColumn == emptyButtonColumn) {
            if (clickedButtonRow == emptyButtonRow + 1 ||
                    clickedButtonRow == emptyButtonRow - 1) {
                swapPossible = true;
            }
        } else {
            swapPossible = false;
        }
    }

    private void swapButtons(){
        gamePanel.remove(emptyPlayPiece);
        gamePanel.remove(clickedButton);
        gamePanel.revalidate();
        repaint();

        int indexOfEmptyButton = (emptyButtonRow * 4) + emptyButtonColumn;
        int indexOfClickedButton = (clickedButtonRow * 4) + clickedButtonColumn;

        for (int i =0; i < gamePieces.length; i++){
            for (int j =0; j < gamePieces[i].length; j++){
                if (emptyButtonRow == i && emptyButtonColumn == j){
                    gamePanel.add(clickedButton, indexOfEmptyButton);
                    gamePanel.revalidate();
                    gamePanel.repaint();
                    gamePieces[i][j] = clickedButton;
                }
                if (clickedButtonRow == i && clickedButtonColumn==j){
                    gamePanel.add(emptyPlayPiece, indexOfClickedButton);
                    gamePanel.revalidate();
                    gamePanel.repaint();
                    gamePieces[i][j] = emptyPlayPiece;
                }
            }
        }
        swapPossible = false;
    }

    public void checkIfGameComplete() {
        int[] testingOrderOfGamePieces = new int[16];
        int counter = 0;

        for (JButton[] buttons : gamePieces) {
            for (JButton button : buttons) {
                if (!button.getText().equals("")) {
                    testingOrderOfGamePieces[counter] = Integer.parseInt(button.getText());
                    counter++;
                } else if (button.getText().equals("")){
                    testingOrderOfGamePieces[counter] = 16;
                    counter++;
                }
            }
        }

        for (int i = 1; i < testingOrderOfGamePieces.length; i++){
            {
                if (testingOrderOfGamePieces[i - 1] > testingOrderOfGamePieces[i])
                {
                    gameComplete = false;
                    break;
                }
            }
            gameComplete = true;
        }

    }

    private void winningGameDemo() {
        arrayOfRandomNumbersForGameBoard = new JButton[16];
        for (int i = 0; i < 15; i++) {
            String value = Integer.toString(i + 1);
            button = new JButton(value);
            arrayOfRandomNumbersForGameBoard[i] = button;
        }

        emptyPlayPiece.setBackground(Color.white);
        arrayOfRandomNumbersForGameBoard[15] = arrayOfRandomNumbersForGameBoard[14];
        arrayOfRandomNumbersForGameBoard[14] = emptyPlayPiece;

        create2DArrayFromShuffledJButtonArray();
        createGameBoard();
    }

    public static void main(String[] args) {
        GameBoard playGame = new GameBoard();
    }

}


