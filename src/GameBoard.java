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
    JButton clickedButton = new JButton();

    private int row = 4;
    private int col = row;

    JButton[] arrayOfRandomNumbersForGameBoard;
    JButton[][] puzzlePieces;

    int clickedButtonRow;
    int clickedButtonColumn;
    int emptyButtonRow;
    int emptyButtonColumn;

    boolean slidePossible;

    boolean gameComplete = false;


    GameBoard() {
        mainPanel.setLayout(new BorderLayout());
        topPanel.add(rubrik);
        gamePanel.setLayout(new GridLayout(4, 4));
        newGameButton.addActionListener(newGameListener);
        bottomPanel.add(newGameButton);

       //createRandomLabelsForGamePieces();
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
            createRandomLabelsForGamePieces();
            createGameBoard();
            gamePanel.revalidate();
        }
    };


    private JButton[] createRandomLabelsForGamePieces() {
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
            createRandomLabelsForGamePieces();
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

    private JButton[][] create2DArrayFromJButtonArray() {
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
        create2DArrayFromJButtonArray();
        for (JButton[] puzzlePiece : puzzlePieces) {
            for (int i = 0; i < puzzlePiece.length; i++) {
                puzzlePiece[i].addActionListener(al);
                gamePanel.add(puzzlePiece[i]);
            }
        }
        gameComplete = false;
        return gamePanel;
    }

    public void findEmptyButtonInArray() {
        for (int i = 0; i < puzzlePieces.length; i++) {
            for (int j = 0; j < puzzlePieces[i].length; j++) {
                if (puzzlePieces[i][j] == emptyPlayPiece) {
                    emptyButtonRow = i;
                    emptyButtonColumn = j;
                }
            }
        }
    }

    public void findButtonInArray() {
        for (int i = 0; i < puzzlePieces.length; i++) {
            for (int j = 0; j < puzzlePieces[i].length; j++) {
                if (puzzlePieces[i][j] == clickedButton) {
                    clickedButtonRow = i;
                    clickedButtonColumn = j;
                }
            }
        }
    }


    public void checkIfSlideIsPossible() {
        if (clickedButtonRow == emptyButtonRow) {
            if (clickedButtonColumn == emptyButtonColumn + 1 || clickedButtonColumn == emptyButtonColumn - 1) {
                slidePossible = true;
            }
        } else if (clickedButtonColumn == emptyButtonColumn) {
            if (clickedButtonRow == emptyButtonRow + 1 ||
                    clickedButtonRow == emptyButtonRow - 1) {
                slidePossible = true;
            }
        } else {
            slidePossible = false;
        }
    }

    private void swapButtons(){
        gamePanel.remove(emptyPlayPiece);
        gamePanel.remove(clickedButton);
        gamePanel.revalidate();
        repaint();

        int indexOfEmptyButton = (emptyButtonRow * 4) + emptyButtonColumn;
        int indexOfClickedButton = (clickedButtonRow * 4) + clickedButtonColumn;

        for (int i =0; i < puzzlePieces.length; i++){
            for (int j =0; j < puzzlePieces[i].length; j++){
                if (emptyButtonRow == i && emptyButtonColumn == j){
                    gamePanel.add(clickedButton, indexOfEmptyButton);
                    gamePanel.revalidate();
                    gamePanel.repaint();
                    puzzlePieces[i][j] = clickedButton;
                }
                if (clickedButtonRow == i && clickedButtonColumn==j){
                    gamePanel.add(emptyPlayPiece, indexOfClickedButton);
                    gamePanel.revalidate();
                    gamePanel.repaint();
                    puzzlePieces[i][j] = emptyPlayPiece;

                }
            }
        }
        slidePossible = false;
    }

    public void checkIfGameComplete() {
        int[] testingOrderOfGamePieces = new int[16];
        int counter = 0;
        for (JButton[] buttons : puzzlePieces) {
            for (JButton button : buttons) {
                if (!button.getText().equals("")) {
                    testingOrderOfGamePieces[counter] = Integer.parseInt(button.getText());
                    counter++;
                } else if (button.getText().equals("")){
                    testingOrderOfGamePieces[counter] = 17;
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


    private void winningGameDemo(){
        arrayOfRandomNumbersForGameBoard = new JButton[16];
        for (int i = 0; i < 15; i++) {
            String value = Integer.toString(i + 1);
            button = new JButton(value);
            arrayOfRandomNumbersForGameBoard[i] = button;
        }
        emptyPlayPiece.setBackground(Color.white);
        arrayOfRandomNumbersForGameBoard[15] = arrayOfRandomNumbersForGameBoard[14];
        arrayOfRandomNumbersForGameBoard[14] = emptyPlayPiece;

        create2DArrayFromJButtonArray();
        createGameBoard();
    }

    public static void main(String[] args) {
        GameBoard playGame = new GameBoard();
    }

}

