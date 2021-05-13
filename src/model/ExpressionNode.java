package model;

import javax.swing.tree.DefaultMutableTreeNode;

public class ExpressionNode extends DefaultMutableTreeNode {
    String name;
    Expression expression;

    public ExpressionNode(String name, Expression expression) {
        super(name);
        this.name = name;
        this.expression = expression;
    }

    public Expression getExpression() { return expression; }

    public String getName() { return name; }
}
