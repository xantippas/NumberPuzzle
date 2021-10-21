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
    //private int row = 4;
    //private int col = rows;
    //JButton[] arrayOfRandomNumbersForGameBoard;
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

    private JPanel createRandomLabels(){
            JButton[] array = new JButton[15];
            for (int i =0; i < 15; i++){
                String value = Integer.toString(i+1);
                JButton button55 = new JButton(value);
                array[i] = button55;
            }
            Collections.shuffle(Arrays.asList(array));

            for (JButton button : array) {
                gamePanel.add(button);
            }
            return gamePanel;

            //annat alternativ, man skapar alla 16 platser och sedan en annan metod som skapar 2D array av info i denna
        /*arrayOfRandomNumbersForGameBoard = new JButton[16];
        for (int i =0; i < 16; i++){
            String value = Integer.toString(i+1);
            JButton button = new JButton(value);
            arrayOfRandomNumbersForGameBoard[i] = button;
            button.addActionListener(movePanels);
            if (button.getText().equals("16")){
                button.setText("");
                button.setBackground(Color.white);
            }
        }
        Collections.shuffle(Arrays.asList(arrayOfRandomNumbersForGameBoard));

        return array;*/
    }


    public static void main(String[] args) {
        GameBoard playGame = new GameBoard();
    }
}
