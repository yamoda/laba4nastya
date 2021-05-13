package view;

import view.inputview.InputPanel;
import view.outputview.OutputPanel;

import javax.swing.*;
import java.awt.*;

public class CalculatorView {
    private final JFrame mainWindow;
    private final InputPanel inputPanel;
    private final OutputPanel outputPanel;

    public CalculatorView() {
        mainWindow = new JFrame();
        inputPanel = new InputPanel();
        outputPanel = new OutputPanel();

        outputPanel.setPreferredSize(new Dimension(200, 800));

        mainWindow.add(inputPanel, BorderLayout.CENTER);
        mainWindow.add(outputPanel, BorderLayout.WEST);
    }

    public JFrame getMainWindow() { return mainWindow; }

    public InputPanel getInputPanel() { return inputPanel; }

    public OutputPanel getOutputPanel() { return outputPanel; }
}
