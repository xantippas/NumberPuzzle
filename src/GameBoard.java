import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameBoard extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    JLabel rubrik = new JLabel("Number Puzzle Game");
   // JButton[][] gameNumber = new JButton[4][4];


    GameBoard(){
        mainPanel.setLayout(new BorderLayout());
        topPanel.add(rubrik);
        gamePanel.setLayout(new GridLayout(4,4));
        bottomPanel.add(newGameButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
        setSize(420,420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void createRandomLabels(){
        JLabel[] gameNumber = new JLabel[15];
        /*
        for (int i = 0; i < 15; i++) {
            double number = Math.random()*(15-1)+1;
            String numberStr = Double.toString(number);
            int numberInt = Integer.parseInt(numberStr);
            gameNumber[i] = new JLabel(Integer.toString(numberInt));
            System.out.println(gameNumber[i].getText());
        }

         */

        Random random = new Random();
            for (int i = 0; i < 15; i++) {
                int index = random.nextInt(15)+1;
                gameNumber[i] = new JLabel(Integer.toString(index));
                for (int j = 0; j < i; j++) {
                if (Integer.parseInt(gameNumber[j].getText()) != index){
                    gameNumber[i] = new JLabel(Integer.toString(index));
                } else if (Integer.parseInt(gameNumber[j].getText()) == index){

                }
                }
                System.out.println(gameNumber[i].getText());
        }
    }

    public static void main(String[] args) {
        GameBoard playGame = new GameBoard();
        GameBoard.createRandomLabels();

    }
}
