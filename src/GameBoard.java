import javax.swing.*;
import java.awt.*;
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
    //private int row = 4;
    //private int col = rows;
    JButton[] arrayOfRandomNumbersForGameBoard;
    //JButton[][] gameBoard = new JButton[row][col];

    GameBoard(){
        mainPanel.setLayout(new BorderLayout());
        topPanel.add(rubrik);
        gamePanel.setLayout(new GridLayout(4,4));
        bottomPanel.add(newGameButton);

        createRandomLabels();

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
            //button.addActionListener(ADDACTION);
            arrayOfRandomNumbersForGameBoard[i] = button;
        }
        emptyPlayPiece.setBackground(Color.white);
        arrayOfRandomNumbersForGameBoard[15] = emptyPlayPiece;
        Collections.shuffle(Arrays.asList(arrayOfRandomNumbersForGameBoard));

        return arrayOfRandomNumbersForGameBoard;
    }


    public static void main(String[] args) {
        GameBoard playGame = new GameBoard();
    }
}
