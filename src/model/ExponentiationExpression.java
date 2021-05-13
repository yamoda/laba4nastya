package model;

import java.util.Arrays;
import java.util.List;

public class ExponentiationExpression implements Expression {
    private Expression base;
    private Expression exponent;

    public ExponentiationExpression(Expression base, Expression exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    public int getType() { return Expression.EXPONENTIATION_NODE; }

    public double getValue() { return Math.pow(base.getValue(), exponent.getValue()); }

    public List<Expression> getArguments() { return Arrays.asList(exponent); }

    @Override
    public String getRepresentation() { return "e^"; }
}

