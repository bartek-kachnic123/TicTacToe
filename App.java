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
        

        createBoard();
        
        this.setVisible(true);

    }
    void initalize()
    {
        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(400, 400);

        movesCounter = 0;
    }
    void createBoard() {

        board = new JPanel();
        board.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        buttons = new JButton[BOARD_SIZE*BOARD_SIZE];
        
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; ++i) {
                buttons[i] = new JButton();
                buttons[i].addActionListener(this);
                board.add(buttons[i]);

        }
    
        this.add(board);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        JButton button = (JButton) e.getSource();
        
        button.setEnabled(false);
        button.setFocusable(false);

        button.setFont(new Font("Arial", Font.BOLD, button.getHeight()));
        button.setText(actual_sign);
        
        computer.makeRandomMove(buttons);
        
    }


    


}

class Computer {
    String sign;

    public Computer(String sign) {
        this.sign = sign;

    }

    void makeRandomMove(JButton[] buttons) {
        Random random = new Random();
        int randomNumber;
        System.out.println(buttons[1]);
        do {
            randomNumber = random.nextInt(buttons.length);
        }while(!buttons[randomNumber].isEnabled());

        buttons[randomNumber].setFont(new Font("Arial", Font.BOLD, buttons[randomNumber].getHeight()));

        buttons[randomNumber].setText(sign);
        buttons[randomNumber].setEnabled(false);
        buttons[randomNumber].setFocusable(false);

    }


}
