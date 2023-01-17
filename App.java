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
                new TicTacToe();
            }
        });
    }
}
class TicTacToe extends JFrame  {
    static final int BOARD_SIZE;
    JPanel menu;
    JButton newGameButton;
    JButton endButton;
    JButton aboutButton;
    ButtonGroup groupX_or_O;
    ButtonGroup groupLevel;
    JPanel board;
    BoardButton[] boardButtons;
    String player_sign;
    int movesCounter;
    Computer computer;
    boolean gameOver;
    static {
        BOARD_SIZE = 3;
    }
    
    TicTacToe() {
        super();
        initalize();
    
        createMenu();
        createBoard();
        
        player_sign = groupX_or_O.getSelection().getActionCommand();
        computer = new Computer(player_sign.equals("X") ? "O" : "X", Color.BLUE);
        
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
                for (int i=0; i < boardButtons.length; i++)
                {
                    boardButtons[i].setEnabled(true);
                    boardButtons[i].setText("");
                    boardButtons[i].setBackground(null); // default color
                }

                // reset counter
                movesCounter = 0;

                // new game
                gameOver = false;

                // set sign X or O
                player_sign = groupX_or_O.getSelection().getActionCommand();
                computer.setSign(player_sign.equals("O") ? "X":"O");

                // set diff level
                computer.setLevel(groupLevel.getSelection().getActionCommand());

                // if computer starts
                if (player_sign.equals("O")) {
                    computer.makeMove(boardButtons, movesCounter);
                    ++movesCounter;
                    
                }

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

        JLabel labelX_or_O = new JLabel("Wybierz X lub O");
        menu.add(labelX_or_O);

        JRadioButton radioButtonO = new JRadioButton("O");
        radioButtonO.setActionCommand("O");

        JRadioButton radioButtonX = new JRadioButton("X");
        radioButtonX.setActionCommand("X");

        groupX_or_O = new ButtonGroup();
        groupX_or_O.add(radioButtonO);
        groupX_or_O.add(radioButtonX);
        radioButtonX.setSelected(true);

        menu.add(radioButtonX);
        menu.add(radioButtonO);

        JLabel labelLevel = new JLabel("Wybierz poziom trudności");
        menu.add(labelLevel);

        JRadioButton radioLevelNormal = new JRadioButton("Normalny");
        radioLevelNormal.setActionCommand("Normal");
        
        JRadioButton radioLevelImpossible = new JRadioButton("Niemożliwy");
        radioLevelImpossible.setActionCommand("Impossible");

        groupLevel = new ButtonGroup();
        groupLevel.add(radioLevelNormal);
        groupLevel.add(radioLevelImpossible);
        radioLevelNormal.setSelected(true);

        menu.add(radioLevelNormal);
        menu.add(radioLevelImpossible);

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
        menu.add(endButton, BorderLayout.SOUTH);

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
            button.setText(player_sign);
            ++movesCounter;

            if (movesCounter >= 5) // min moves to win
                if (gameOver()) return;

            computer.makeMove(boardButtons, movesCounter);
            ++movesCounter;

            if (movesCounter >= 5)
                gameOver();
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
                checking += boardButtons[j+i*BOARD_SIZE].getText();
            }
            if (checking.equals(XWINNER) || checking.equals(OWINNER)) {
                for (int j=0; j< BOARD_SIZE; j++) {
                    boardButtons[j+i*BOARD_SIZE].setBackground(Color.GREEN);
                }
                return true;
            }
            checking = "";
        }
        
        // check cols
        for (int i=0; i < BOARD_SIZE; i++) {
            for (int j=0; j < BOARD_SIZE; j++) {
                checking += boardButtons[i + j*BOARD_SIZE].getText();
            }

            if (checking.equals(XWINNER) || checking.equals(OWINNER)) {
                for (int j=0; j< BOARD_SIZE; j++) {
                    boardButtons[i+j*BOARD_SIZE].setBackground(Color.GREEN);
                }
                return true;
            }
            checking = "";
        }

        // check diagonals
        // 1
        int midPosition = (int) Math.floor(boardButtons.length / 2);
        checking += boardButtons[0].getText() + boardButtons[midPosition].getText()
                    + boardButtons[boardButtons.length - 1].getText();
        if (checking.equals(XWINNER) || checking.equals(OWINNER)) {
                
            boardButtons[0].setBackground(Color.GREEN);
            boardButtons[midPosition].setBackground(Color.GREEN);
            boardButtons[boardButtons.length - 1].setBackground(Color.GREEN);
                
            return true;
        }
        // 2
        checking = "";
        checking += boardButtons[BOARD_SIZE - 1].getText() + boardButtons[midPosition].getText()
                    + boardButtons[boardButtons.length - BOARD_SIZE].getText();
        if (checking.equals(XWINNER) || checking.equals(OWINNER)) {

            boardButtons[BOARD_SIZE - 1].setBackground(Color.GREEN);
            boardButtons[midPosition].setBackground(Color.GREEN);
            boardButtons[boardButtons.length - BOARD_SIZE].setBackground(Color.GREEN);

            return true;
        }
        return false;
    }

}

class Computer {
    String sign;
    Color signColor;
    String diffLevel;

    public Computer(String sign, Color signColor) {
        this.sign = sign;
        this.signColor = signColor;
        this.diffLevel = "Normal";
    }

    void makeMove(BoardButton[] buttons, int movesCounter) {
        if (diffLevel.equals("Normal"))
            makeRandomMove(buttons, movesCounter);
        else if (diffLevel.equals("Impossible"))
            makeBestMove(buttons, movesCounter);
    }
    void makeRandomMove(BoardButton[] buttons, int movesCounter) {
        Random random = new Random();
        int randomNumber;

        do {
            randomNumber = random.nextInt(buttons.length);
            if (movesCounter >= buttons.length) return;
        }while(!buttons[randomNumber].isEnabled());

        disableButton(buttons[randomNumber]);
    }

    void makeBestMove(BoardButton[] buttons, int movesCounter) { 

        String checking = "";
        int board_size = (int) Math.sqrt(buttons.length);
        int indexButton=0;
        String playerSign = (sign == "O") ?  "X" : "O";

        // first move will be random
        if (movesCounter == 0) {
            makeRandomMove(buttons, movesCounter);
            return;
        }

        for (int i = 0; i < board_size; ++i) {

            // Missing one to win in row
            for (int j = 0; j < board_size; ++j) {
                    checking += buttons[j+ i * board_size].getText();
                    if (buttons[j+i*board_size].getText().isEmpty())
                        indexButton = j+i*board_size;
            }
            if (checking.equals(sign+sign)) { 
                buttons[indexButton].setText(sign);
                disableButton(buttons[indexButton]);
                return;
            }
            checking = "";
            
            // Missing one to win in column
            for (int j=0; j < board_size; j++) {
                checking += buttons[i + j*board_size].getText();
                if (buttons[i + j*board_size].getText().isEmpty())
                    indexButton = i + j*board_size;
            }

            if (checking.equals(sign+sign)) {
                buttons[indexButton].setText(sign);
                disableButton(buttons[indexButton]);
                return;
            }
            checking = "";
            
            
        }
        for (int i = 0; i < board_size; ++i) {
            // block oponent row
            for (int j = 0; j < board_size; ++j) {
                checking += buttons[j+ i * board_size].getText();
                if (buttons[j+i*board_size].getText().isEmpty())
                    indexButton = j+i*board_size;
            }
            if (checking.equals(playerSign+playerSign)) { 
                buttons[indexButton].setText(sign);
                disableButton(buttons[indexButton]);
                return;
                }
            checking = "";

            
              // block col oponnent
            for (int j=0; j < board_size; j++) {
                checking += buttons[i + j*board_size].getText();
                if (buttons[i + j*board_size].getText().isEmpty())
                    indexButton = i + j*board_size;
                }

            if (checking.equals(playerSign+playerSign)) {
                buttons[indexButton].setText(sign);
                disableButton(buttons[indexButton]);
                return;
            }
            checking = "";

        }
        // mark mid if you can
        int mid = (int) Math.floor(buttons.length / 2);
        if (buttons[mid].isEnabled()) {
            buttons[mid].setText(sign);
            disableButton(buttons[mid]);
            return;
        }
        // check corners:
        if (buttons[0].getText().equals(playerSign) && buttons[buttons.length-1].isEnabled()) {
            buttons[buttons.length-1].setText(sign);
            disableButton(buttons[buttons.length-1]);
            return;
        }

        if (buttons[0].isEnabled() && buttons[buttons.length-1].getText().equals(playerSign)) {
            buttons[0].setText(sign);
            disableButton(buttons[0]);
            return;
        }
        
        if (buttons[board_size-1].getText().equals(playerSign) && buttons[buttons.length-board_size].isEnabled()) {
            buttons[buttons.length-board_size].setText(sign);
            disableButton(buttons[buttons.length-board_size]);
            return;
        }

        if (buttons[board_size-1].isEnabled() && buttons[buttons.length-board_size].getText().equals(playerSign)) {
            buttons[board_size-1].setText(sign);
            disableButton(buttons[board_size-1]);
            return;
        }

        if (buttons[0].isEnabled()) {
            buttons[0].setText(sign);
            disableButton(buttons[0]);
            return;
        }
        if (buttons[buttons.length-1].isEnabled()) {
            buttons[buttons.length-1].setText(sign);
            disableButton(buttons[buttons.length-1]);
            return;
        }
        if (buttons[board_size-1].isEnabled()) {
            buttons[board_size-1].setText(sign);
            disableButton(buttons[board_size-1]);
            return;
        }
        if (buttons[buttons.length-board_size].isEnabled()) {
            buttons[buttons.length-board_size].setText(sign);
            disableButton(buttons[buttons.length-board_size]);
            return;
        }

        makeRandomMove(buttons, movesCounter);
    }

    private void disableButton(BoardButton button) {

        button.setEnabled(false, this.signColor);
        button.setFocusable(false);

        button.setForeground(Color.BLUE);
        button.setFont(new Font("Arial", Font.BOLD, button.getHeight()));
        button.setText(sign);
    }

    void setSign(String sign) {
        this.sign = sign;
    }

    void setLevel(String diffLevel) {
        this.diffLevel = diffLevel;
    }

}

class BoardButton extends JButton {
    
    public BoardButton() {
        super();
        this.setText("");
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
