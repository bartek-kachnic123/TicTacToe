import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
    JButton[][] buttons;
    boolean player;
    boolean computer;
    String actual_sign;
    static {
        BOARD_SIZE = 3;
    }
    AppGUI() {
        initalize();
        
        player = true;
        computer = false;
        actual_sign = player ? "O" : "X";
        

        createBoard();
        
        this.setVisible(true);

    }
    void initalize()
    {
        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(400, 400);
    }
    void createBoard() {

        board = new JPanel();
        board.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button = new JButton();
                button.addActionListener(this);
                board.add(button);
            }
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
        
        
    }


    


}

