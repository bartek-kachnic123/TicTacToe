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


class AppGUI extends JFrame {
    static final int BOARD_SIZE = 3;
    MyButton[][] board;
    static {

    }
    AppGUI() {

        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(400, 400);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(120, 50, 250));

        
    }

    void initializeBoard() {
        board = new MyButton[BOARD_SIZE][BOARD_SIZE];
    }


    class MyButton extends JButton implements ActionListener {
        
        MyButton()
        {
            super();
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }
    }
}

