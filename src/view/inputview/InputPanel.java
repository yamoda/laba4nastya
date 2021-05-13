package view.inputview;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel{
    JTextField calculatorBar;
    ButtonsPanel buttonsPanel;

    public InputPanel() {
        setLayout(new BorderLayout());

        calculatorBar = new JTextField();
        buttonsPanel = new ButtonsPanel();

        add(new JScrollPane(calculatorBar, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.NORTH);
        calculatorBar.setFont(new Font("Times Roman", Font.PLAIN, 50));

        add(buttonsPanel, BorderLayout.CENTER);
    }

    public JTextField getCalculatorBar() { return calculatorBar; }

    public ButtonsPanel getButtonsPanel() { return buttonsPanel; }
}
