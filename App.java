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
    static {
        BOARD_SIZE = 3;
    }
    AppGUI() {

        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(400, 400);
        this.getContentPane().setBackground(new Color(120, 50, 250));
        

        createBoard();
        
        this.setVisible(true);

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
        button.setText("X");
        button.setEnabled(false);
        button.setFocusable(false);
        
    }


    


}

