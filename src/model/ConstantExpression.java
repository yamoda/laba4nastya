package model;

import java.util.LinkedList;
import java.util.List;

public class ConstantExpression implements Expression {
    private double value;

    public ConstantExpression(double value) { this.value = value; }

    public ConstantExpression(String value) { this.value = Double.parseDouble(value); }

    public double getValue() { return value; }

    public int getType() { return Expression.CONSTANT_NODE; }

    public List<Expression> getArguments() { return new LinkedList<>(); }

    public String getRepresentation() { return String.valueOf(value); }
}
