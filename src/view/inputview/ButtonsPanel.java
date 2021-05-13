package view.inputview;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ButtonsPanel extends JPanel {
    ArrayList<JButton> numberButtons;

    JButton leftBracketButton;
    JButton rightBracketButton;
    JButton clearButton;

    JButton plusButton;
    JButton minusButton;
    JButton multiplyButton;
    JButton divideButton;
    JButton remainderButton;

    JButton sqrtButton;
    JButton cosButton;
    JButton sinButton;
    JButton tanButton;
    JRadioButton hideButton;

    JButton equalsButton;
    JButton dotButton;

    public ButtonsPanel() {
        createNumbers();
        createButtons();
        createUI();
    }

    private void createNumbers() {
        numberButtons = new ArrayList<>(10);
        for (int buttonIdx=0; buttonIdx<=9; buttonIdx++)
            numberButtons.add(new JButton(String.valueOf(buttonIdx)));
    }

    private void createButtons() {
        leftBracketButton = new JButton("(");
        rightBracketButton = new JButton(")");
        clearButton = new JButton("AC");

        plusButton = new JButton("+");
        minusButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");
        remainderButton = new JButton("%");

        sqrtButton = new JButton("√");
        sinButton = new JButton("sin");
        cosButton = new JButton("cos");
        tanButton = new JButton("tan");
        hideButton = new JRadioButton("show");

        sqrtButton.setVisible(false);
        sinButton.setVisible(false);
        cosButton.setVisible(false);
        tanButton.setVisible(false);

        equalsButton = new JButton("=");
        dotButton = new JButton(".");
    }

    private void createUI() {
        setLayout(new GridLayout(5, 5));

        add(leftBracketButton);
        add(rightBracketButton);
        add(remainderButton);
        add(clearButton);
        add(sqrtButton);

        add(numberButtons.get(7));
        add(numberButtons.get(8));
        add(numberButtons.get(9));
        add(divideButton);
        add(sinButton);

        add(numberButtons.get(4));
        add(numberButtons.get(5));
        add(numberButtons.get(6));
        add(multiplyButton);
        add(cosButton);

        add(numberButtons.get(1));
        add(numberButtons.get(2));
        add(numberButtons.get(3));
        add(minusButton);
        add(tanButton);

        add(numberButtons.get(0));
        add(dotButton);
        add(equalsButton);
        add(plusButton);
        add(hideButton);
    }

    public ArrayList<JButton> getNumberButtons() { return numberButtons; }

    public JButton getClearButton() { return clearButton; }

    public JButton getRightBracketButton() { return rightBracketButton; }

    public JButton getLeftBracketButton() { return leftBracketButton; }

    public JButton getRemainderButton() { return remainderButton; }

    public JButton getSqrtButton() { return sqrtButton; }

    public JButton getDivideButton() { return divideButton; }

    public JButton getSinButton() { return  sinButton; }

    public JButton getCosButton() { return cosButton; }

    public JButton getMultiplyButton() { return multiplyButton; }

    public JButton getMinusButton() { return minusButton; }

    public JButton getTanButton() { return tanButton; }

    public JButton getEqualsButton() { return equalsButton; }

    public JButton getDotButton() { return dotButton; }

    public JButton getPlusButton() { return plusButton; }

    public JRadioButton getHideButton() { return hideButton; }
}
