import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    JLabel rubrik = new JLabel("Number Puzzle Game");

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

    public static void main(String[] args) {
        GameBoard playGame = new GameBoard();
    }
}
