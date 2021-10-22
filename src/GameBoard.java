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

    private int[] clickedButtonPosition = new int[2];
    private int[] emptyButtonPosition = new int[2];
    int clickedButtonRow;
    int clickedButtonColumn;
    int emptyButtonRow;
    int emptyButtonColumn;

    boolean slidePossible;

    GameBoard(){
        mainPanel.setLayout(new BorderLayout());
        topPanel.add(rubrik);
        gamePanel.setLayout(new GridLayout(4,4));
        bottomPanel.add(newGameButton);

        createRandomLabels();
        create2DArray();
        createGameBoard();
        findEmptyButtonInArray();
        checkIfSlideIsPossible();


        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
        setSize(420,420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private JButton[] createRandomLabels(){
        arrayOfRandomNumbersForGameBoard = new JButton[16];
        for (int i =0; i < 15; i++){
            String value = Integer.toString(i+1);
            button = new JButton(value);
            button.addActionListener(al);
            arrayOfRandomNumbersForGameBoard[i] = button;
        }
        emptyPlayPiece.setBackground(Color.white);
        arrayOfRandomNumbersForGameBoard[15] = emptyPlayPiece;
        Collections.shuffle(Arrays.asList(arrayOfRandomNumbersForGameBoard));

        return arrayOfRandomNumbersForGameBoard;
    }

    private JButton[][] create2DArray(){
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

    private JPanel createGameBoard(){
        create2DArray();
        for (JButton[] puzzlePiece : puzzlePieces) {
            for (int i = 0; i < puzzlePiece.length; i++) {
                gamePanel.add(puzzlePiece[i]);
            }
        }
        return gamePanel;
    }

    ActionListener al = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            for (JButton button: arrayOfRandomNumbersForGameBoard) {
                    if (e.getSource() == button){
                        clickedButton = button;
                        findButtonInArray(clickedButton);
                        System.out.println(clickedButtonColumn + " " + clickedButtonRow);
                    }
            }
            if(e.getSource() == clickedButton){
                findButtonInArray(clickedButton);
            }
        }
    };

    private void findEmptyButtonInArray(){
        for (int i = 0; i < puzzlePieces.length; i++) {
            for (int j = 0; j < puzzlePieces[i].length; j++) {
                if (puzzlePieces[i][j] == emptyPlayPiece){
                    emptyButtonRow = i;
                    emptyButtonColumn = j;
                }
            }
        }
    }

    private void findButtonInArray(JButton button){
        for (int i = 0; i < puzzlePieces.length; i++) {
            for (int j = 0; j < puzzlePieces[i].length; j++) {
                if (puzzlePieces[i][j] == button){
                    clickedButtonRow = i;
                    clickedButtonColumn = j;
                }
            }
        }
    }


    private void checkIfSlideIsPossible(){
            if (clickedButtonRow == emptyButtonRow || clickedButtonColumn == emptyButtonColumn) {
                System.out.println("De är nära varandra");
                if (clickedButtonColumn == emptyButtonColumn + 1 ||
                        clickedButtonColumn == emptyButtonColumn - 1 || clickedButtonRow == emptyButtonRow + 1 ||
                        clickedButtonRow == emptyButtonRow - 1) {
                    slidePossible = true;
                    System.out.println("True");
                }
            } else {
                slidePossible = false;
                System.out.println("False");
            }

    }




    public static void main(String[] args) {
        GameBoard playGame = new GameBoard();
    }
}
