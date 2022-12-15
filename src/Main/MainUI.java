package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {
    private JButton button;
    private JPanel panel;
    private JTextField textField;

    private JFrame frame;

    public MainUI() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                if (text.equals("2022")) {
                    MainMenu mainUI = new MainMenu();
                    mainUI.show();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Try again.");
                }
            }
        });
    }

    public void show() {
        frame = new JFrame("App");
        JPanel currentPanel = panel;
        currentPanel.setPreferredSize(new Dimension(640, 480));
        frame.setContentPane(currentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
