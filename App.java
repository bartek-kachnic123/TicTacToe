import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;

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


class AppGUI extends JFrame  {
    static final int BOARD_SIZE;
    JPanel menu;
    JButton newGameButton;
    JButton endButton;
    JButton aboutButton;
    JPanel board;
    BoardButton[] boardButtons;
    String actual_sign;
    int movesCounter;
    Computer computer;
    boolean gameOver;
    static {
        BOARD_SIZE = 3;
    }
    AppGUI() {
        super();
        initalize();
        
        actual_sign = "O";
        computer = new Computer("X", Color.BLUE);
        
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
        gameOver = false;
    }
    void createMenu() {
        menu = new JPanel();
        menu.setPreferredSize(new Dimension(200, 500));

        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        newGameButton = new JButton();
        newGameButton.setPreferredSize(new Dimension(200, 50));
        newGameButton.setText("Nowa gra");
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 30));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // reset buttons
                for (int i=0; i < BOARD_SIZE*BOARD_SIZE; i++)
                {
                    boardButtons[i].setEnabled(true);
                    boardButtons[i].setText(null);
                    boardButtons[i].setBackground(null); // default color
                }

                // reset counter
                movesCounter = 0;

                // new game
                gameOver = false;
            }
        });
        menu.add(newGameButton);

        aboutButton = new JButton();
        aboutButton.setPreferredSize(new Dimension(200, 50));
        aboutButton.setFont(new Font("Arial", Font.PLAIN, 30));
        aboutButton.setText("O grze");
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String MESSAGE = "Gra strategiczna rozgrywana przez dwóch graczy.\nGracze obejmują pola na przemian dążąc do objęcia\ntrzech pól w jednej linii, przy jednoczesnym uniemożliwieniu\n tego samego przeciwnikowi.\n Pole może być objęte przez jednego \ngracza i nie zmienia swego właściciela przez cały przebieg gry.";
                JOptionPane.showMessageDialog(board, MESSAGE, aboutButton.getText(), JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menu.add(aboutButton);

        endButton = new JButton();
        endButton.setPreferredSize(new Dimension(200, 50));
        endButton.setFont(new Font("Arial", Font.PLAIN, 30));
        endButton.setText("Zakoncz");
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        menu.add(endButton);
        
        this.add(menu, BorderLayout.WEST);
        
        
        
    }
    void createBoard() {

        board = new JPanel();
        board.setPreferredSize(new Dimension(500, 500));

        board.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        boardButtons = new BoardButton[BOARD_SIZE*BOARD_SIZE];
        
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; ++i) {
                boardButtons[i] = new BoardButton();
                boardButtons[i].addActionListener(new BoardButtonListener());
                board.add(boardButtons[i]);

        }
        
        this.add(board, BorderLayout.CENTER);
    }
    private class BoardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) return;

            BoardButton button = (BoardButton) e.getSource();
        
            button.setEnabled(false, Color.MAGENTA);
            button.setFocusable(false);

            button.setFont(new Font("Arial", Font.BOLD, button.getHeight()));
            button.setText(actual_sign);
            ++movesCounter;
            if (gameOver()) return;
            computer.makeRandomMove(boardButtons, movesCounter);
            ++movesCounter;

            
            if (gameOver()) return;
    }
    }
    
    
    public boolean gameOver() {

        if (checkWin()) {
            this.gameOver = true;
            return true;
        }

        if (movesCounter > boardButtons.length) {
            this.gameOver = true;
            return true;
        }
        
        return false;
    }
    public boolean checkWin() {
        String XWINNER = "XXX";
        String OWINNER = "OOO";
        String checking = "";

        // check rows
        for (int i=0; i < BOARD_SIZE; i++) {
            for (int j=0; j < BOARD_SIZE; j++) {
                checking += this.boardButtons[j+i*BOARD_SIZE].getText();
            }
            if (checking.equals(XWINNER) || checking.equals(OWINNER)) {
                for (int j=0; j< BOARD_SIZE; j++) {
                    this.boardButtons[j+i*BOARD_SIZE].setBackground(Color.GREEN);
                }
                return true;
            }
            checking = "";
        }
        return false;
    }


    


}

class Computer {
    String sign;
    Color signColor;

    public Computer(String sign, Color signColor) {
        this.sign = sign;
        this.signColor = signColor;

    }

    void makeRandomMove(BoardButton[] buttons, int movesCounter) {
        Random random = new Random();
        int randomNumber;

        do {
            randomNumber = random.nextInt(buttons.length);
            if (movesCounter >= buttons.length) return;
        }while(!buttons[randomNumber].isEnabled());


        buttons[randomNumber].setEnabled(false, this.signColor);
        buttons[randomNumber].setFocusable(false);

        buttons[randomNumber].setForeground(Color.BLUE);
        buttons[randomNumber].setFont(new Font("Arial", Font.BOLD, buttons[randomNumber].getHeight()));
        buttons[randomNumber].setText(sign);

    }


}

class BoardButton extends JButton {
    
    public BoardButton() {
        super();
    }
    void setEnabled(boolean enabled, Color fontColor) {
        super.setEnabled(enabled);
        
        this.setBackground(Color.WHITE);
        this.setUI(new MetalButtonUI() {
                    // override the disabled text color for the button UI
                    protected Color getDisabledTextColor() {
                        return fontColor;
                    }});
    }
}
