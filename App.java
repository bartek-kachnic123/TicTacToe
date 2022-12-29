import javax.swing.*;
import java.awt.*;


public class App {
    public static void main(String[] args) {
        new AppGUI();
        
    }
}


class AppGUI extends JFrame {

    AppGUI() {

        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(400, 400);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(120, 50, 250));
    }


    
}