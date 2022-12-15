package Main;

import javax.swing.*;
import java.awt.event.*;

public class CreateSweetsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JRadioButton candyRadioButton;
    private JRadioButton toyRadioButton;
    private JRadioButton fruitRadioButton;
    private ButtonGroup group;
    private CreateSweetsListener listener;

    public CreateSweetsDialog(CreateSweetsListener listener) {
        this.listener = listener;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        group = new ButtonGroup();
        group.add(fruitRadioButton);
        group.add(toyRadioButton);
        group.add(candyRadioButton);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        String name = textField1.getText();
        String price = textField2.getText();
        if (name != null && !name.isEmpty() && price != null && !price.isEmpty()) {
            listener.onCreate(name, Integer.parseInt(price), getSelectedType());
            dispose();
        } else {

        }
    }

    private String getSelectedType() {
        if (fruitRadioButton.isSelected()) return fruitRadioButton.getText();
        if (toyRadioButton.isSelected()) return toyRadioButton.getText();
        return candyRadioButton.getText();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
