import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class App {
    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppGUI();
            }
        });
        
        
    }
}


class AppGUI extends JFrame implements ActionListener {
    static final int BOARD_SIZE;
    JPanel menu;
    JButton newGameButton;
    JButton endButton;
    JButton aboutButton;
    JPanel board;
    JButton[] buttons;
    String actual_sign;
    int movesCounter;
    Computer computer;
    static {
        BOARD_SIZE = 3;
    }
    AppGUI() {
        initalize();
        
        actual_sign = "O";
        computer = new Computer("X");
        
        createMenu();
        createBoard();
        
        this.pack();
        this.setVisible(true);

    }
    void initalize()
    {
        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.setPreferredSize(new Dimension(700, 500));
        this.setLayout(new BorderLayout());

        movesCounter = 0;
    }
    void createMenu() {
        menu = new JPanel();
        menu.setPreferredSize(new Dimension(200, 500));

        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        newGameButton = new JButton();
        newGameButton.setPreferredSize(new Dimension(200, 50));
        newGameButton.setText("Nowa gra");
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 30));

        menu.add(newGameButton);

        aboutButton = new JButton();
        aboutButton.setPreferredSize(new Dimension(200, 50));
        aboutButton.setFont(new Font("Arial", Font.PLAIN, 30));
        aboutButton.setText("O grze");
        menu.add(aboutButton);

        endButton = new JButton();
        endButton.setPreferredSize(new Dimension(200, 50));
        endButton.setFont(new Font("Arial", Font.PLAIN, 30));
        endButton.setText("Zakoncz");
        menu.add(endButton);
        
        this.add(menu, BorderLayout.WEST);
        
        
        
    }
    void createBoard() {

        board = new JPanel();
        board.setPreferredSize(new Dimension(500, 500));

        board.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        buttons = new JButton[BOARD_SIZE*BOARD_SIZE];
        
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; ++i) {
                buttons[i] = new JButton();
                buttons[i].addActionListener(this);
                board.add(buttons[i]);

        }
        
        this.add(board, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver()) return;

        JButton button = (JButton) e.getSource();
        
        button.setEnabled(false);
        button.setFocusable(false);

        button.setFont(new Font("Arial", Font.BOLD, button.getHeight()));
        button.setText(actual_sign);
        ++movesCounter;
        
        computer.makeRandomMove(buttons, movesCounter);
        ++movesCounter;
        
    }

    public boolean gameOver() {

        if (movesCounter > buttons.length) return true;

        return false;
    }
    public boolean checkWin() {
        return false;
    }


    


}

class Computer {
    String sign;

    public Computer(String sign) {
        this.sign = sign;

    }

    void makeRandomMove(JButton[] buttons, int movesCounter) {
        Random random = new Random();
        int randomNumber;

        do {
            randomNumber = random.nextInt(buttons.length);
            if (movesCounter >= buttons.length) return;
        }while(!buttons[randomNumber].isEnabled());

        buttons[randomNumber].setFont(new Font("Arial", Font.BOLD, buttons[randomNumber].getHeight()));

        buttons[randomNumber].setText(sign);
        buttons[randomNumber].setEnabled(false);
        buttons[randomNumber].setFocusable(false);

    }


}
