package controller;

import model.ExpressionNode;
import model.ExpressionReader;
import model.Expression;
import model.FunctionExpression;
import view.CalculatorView;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorController {
    Map<Integer, String> namesMap;
    CalculatorView view;

    public CalculatorController(CalculatorView view) {
        this.view = view;

        initView();
        initButtons();
        initMap();
    }

    private void initMap() {
        namesMap = new HashMap<>();

        namesMap.put(Expression.ADDITION_NODE, "+");
        namesMap.put(Expression.MULTIPLICATION_NODE, "*");
        namesMap.put(Expression.REMAINDER_NODE, "%");

        namesMap.put(FunctionExpression.SIN, "sin");
        namesMap.put(FunctionExpression.COS, "cos");
        namesMap.put(FunctionExpression.TAN, "tan");
        namesMap.put(FunctionExpression.SQRT, "sqrt");
    }

    private void initView() {
        view.getMainWindow().setTitle("Calculator");
        view.getMainWindow().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.getMainWindow().setResizable(false);
        view.getMainWindow().setSize(800, 600);
        view.getMainWindow().setVisible(true);
    }

    private void initButtons(){
        ArrayList<JButton> numbers = view.getInputPanel().getButtonsPanel().getNumberButtons();
        for (JButton number : numbers) number.addActionListener(e -> addSymbol(number.getText()));

        view.getInputPanel().getButtonsPanel().getCosButton().addActionListener(e -> addSymbol("cos"));
        view.getInputPanel().getButtonsPanel().getSinButton().addActionListener(e -> addSymbol("sin"));
        view.getInputPanel().getButtonsPanel().getTanButton().addActionListener(e -> addSymbol("tan"));

        view.getInputPanel().getButtonsPanel().getLeftBracketButton().addActionListener(e -> addSymbol("("));
        view.getInputPanel().getButtonsPanel().getRightBracketButton().addActionListener(e -> addSymbol(")"));
        view.getInputPanel().getButtonsPanel().getRemainderButton().addActionListener(e -> addSymbol("%"));
        view.getInputPanel().getButtonsPanel().getSqrtButton().addActionListener(e -> addSymbol("sqrt"));

        view.getInputPanel().getButtonsPanel().getPlusButton().addActionListener(e -> addSymbol("+"));
        view.getInputPanel().getButtonsPanel().getMinusButton().addActionListener(e -> addSymbol("-"));
        view.getInputPanel().getButtonsPanel().getDivideButton().addActionListener(e -> addSymbol("/"));
        view.getInputPanel().getButtonsPanel().getMultiplyButton().addActionListener(e -> addSymbol("*"));
        view.getInputPanel().getButtonsPanel().getDotButton().addActionListener(e -> addSymbol("."));

        view.getInputPanel().getButtonsPanel().getClearButton().addActionListener(e -> clear());
        view.getInputPanel().getButtonsPanel().getEqualsButton().addActionListener(e -> calculate());

        view.getOutputPanel().getFoldButton().addActionListener(e -> fold());
        view.getOutputPanel().getExpandButton().addActionListener(e -> expand());

        view.getInputPanel().getButtonsPanel().getHideButton().addActionListener(e -> hide());
    }

    private void hide() {
        JButton sqrtButton = view.getInputPanel().getButtonsPanel().getSqrtButton();
        JButton sinButton = view.getInputPanel().getButtonsPanel().getSinButton();
        JButton cosButton = view.getInputPanel().getButtonsPanel().getCosButton();
        JButton tanButton = view.getInputPanel().getButtonsPanel().getTanButton();

        sqrtButton.setVisible(!sqrtButton.isVisible());
        sinButton.setVisible(!sinButton.isVisible());
        cosButton.setVisible(!cosButton.isVisible());
        tanButton.setVisible(!tanButton.isVisible());
    }

    private void fold() {
        DefaultTreeModel model = (DefaultTreeModel) view.getOutputPanel().getCalculationTree().getModel();
        if (model == null) return;

        ExpressionNode root = (ExpressionNode) model.getRoot();
        ExpressionNode lowest = findLowest(root);

        if (lowest.getDepth() == 1) {
            root = new ExpressionNode(String.valueOf(root.getExpression().getValue()), root.getExpression());
            model.setRoot(root);
            return;
        }

        for (int lowestChildIdx=0; lowestChildIdx<lowest.getChildCount(); lowestChildIdx++) {
            ExpressionNode child = (ExpressionNode) lowest.getChildAt(lowestChildIdx);
            if (child.getDepth()==1) {
                model.removeNodeFromParent(child);
                model.insertNodeInto(new ExpressionNode(String.valueOf(child.getExpression().getValue()), child.getExpression()), lowest, 0);
                break;
            }
        }
    }

    private void expand() {
        DefaultTreeModel model = (DefaultTreeModel) view.getOutputPanel().getCalculationTree().getModel();
        if (model == null) return;

        ExpressionNode root = (ExpressionNode) model.getRoot();
        ExpressionNode foldedNode = findFolded(root);

        if (foldedNode == null) return;

        if (foldedNode.getParent() == null) {
            ExpressionNode newNode = createNewNode(root);
            model.setRoot(newNode);
            return;
        }

        ExpressionNode parentFoldedNode = (ExpressionNode) foldedNode.getParent();
        ExpressionNode newNode = createNewNode(foldedNode);

        model.removeNodeFromParent(foldedNode);
        model.insertNodeInto(newNode, parentFoldedNode, 0);
    }

    private ExpressionNode createNewNode(ExpressionNode foldedNode) {
        ExpressionNode newNode = new ExpressionNode(namesMap.get(foldedNode.getExpression().getType()), foldedNode.getExpression());

        List<Expression> arguments = foldedNode.getExpression().getArguments();
        for (Expression argument : arguments) newNode.add(new ExpressionNode(String.valueOf(argument.getValue()), argument));

        return newNode;
    }

    private ExpressionNode findFolded(ExpressionNode root) {
        if (root.getExpression().getType() != Expression.CONSTANT_NODE && !namesMap.containsValue(root.getName())) return root;

        int childCount = root.getChildCount();
        ExpressionNode foldedNode = null;

        for(int childIdx=0; childIdx<childCount; childIdx++) {
            ExpressionNode child = (ExpressionNode) root.getChildAt(childIdx);
            foldedNode = findFolded(child);

            if (foldedNode != null) return foldedNode;
        }

        return null;
    }

    private ExpressionNode findLowest(ExpressionNode root) {
        if (root.getDepth()==2) return root;

        int childCount = root.getChildCount();
        for(int childIdx=0; childIdx<childCount; childIdx++) {
            ExpressionNode child = (ExpressionNode) root.getChildAt(childIdx);
            findLowest(child);
        }

        return root;
    }

    private void calculate() {
        clearOutput();

        ExpressionReader expressionParser = new ExpressionReader();
        String rawInput = view.getInputPanel().getCalculatorBar().getText();

        Expression parsedInput = expressionParser.parse(rawInput);
        String result = String.format("%.3f", parsedInput.getValue());

        createTree(parsedInput);
        view.getOutputPanel().getCalculationOutput().setText(result);
    }


    private void createTree(Expression expression) {
        ExpressionNode root;
        if (expression.getType() == Expression.CONSTANT_NODE) root = new ExpressionNode(String.valueOf(expression.getValue()), expression);
        else root = new ExpressionNode(namesMap.get(expression.getType()), expression);

        DefaultTreeModel model = (DefaultTreeModel) view.getOutputPanel().getCalculationTree().getModel();
        if (model == null) model = new DefaultTreeModel(root);
        else model.setRoot(root);

        List<Expression> arguments = expression.getArguments();
        for (Expression argument: arguments) buildTree(argument, root);

        model.reload(root);
        view.getOutputPanel().getCalculationTree().setModel(model);
    }

    private void buildTree(Expression input, ExpressionNode root) {
        if (input.getType() == Expression.CONSTANT_NODE) {
            ExpressionNode expressionVal = new ExpressionNode(String.valueOf(input.getValue()), input);
            root.add(expressionVal);
            return;
        }

        ExpressionNode expressionType = new ExpressionNode(namesMap.get(input.getType()), input);
        root.add(expressionType);

        List<Expression> arguments = input.getArguments();
        for (Expression argument: arguments) buildTree(argument, expressionType);
    }

    private void clearInput() { view.getInputPanel().getCalculatorBar().setText(""); }

    private void clearOutput() {
        view.getOutputPanel().getCalculationOutput().setText(" ");
        DefaultTreeModel model = (DefaultTreeModel) view.getOutputPanel().getCalculationTree().getModel();

        if (model != null) {
            ExpressionNode root = (ExpressionNode) model.getRoot();
            root.removeAllChildren();
            model.reload(root);
        }
    }

    private void clear() {
        clearInput();
        clearOutput();
    }

    private void addSymbol(String symbol) {
        JTextField inputBar = view.getInputPanel().getCalculatorBar();
        inputBar.setText(inputBar.getText() + symbol);
    }
}
