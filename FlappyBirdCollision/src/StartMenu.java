import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame {
    private JButton startButton;
    private JLabel startMessage;
    private JPanel mainPanel;

    public static void main(String[] args){
        StartMenu frame = new StartMenu();
        frame.setContentPane(frame.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(280, 180);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.requestFocus();
        frame.setVisible(true);
    }

    public StartMenu(){
        // handle start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                App.main(null);
            }
        });
    }
}
