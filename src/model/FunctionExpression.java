package model;

import java.util.Arrays;
import java.util.List;

public class FunctionExpression implements Expression {
    public static final int SIN = 101;
    public static final int COS = 201;
    public static final int TAN = 301;
    public static final int SQRT = 401;
    public static final int EXP = 501;

    private int function;
    private Expression argument;

    public FunctionExpression(int function, Expression argument) {
        super();
        this.function = function;
        this.argument = argument;
    }

    public int getType() { return function; }

    public List<Expression> getArguments() { return Arrays.asList(argument); }

    public double getValue() {
        return switch (function) {
            case SIN -> Math.sin(argument.getValue());
            case COS -> Math.cos(argument.getValue());
            case TAN -> Math.tan(argument.getValue());
            case SQRT -> Math.sqrt(argument.getValue());
            case EXP -> Math.exp(argument.getValue());
            default -> 0;
        };
    }

    public static int stringToFunction(String str) {
        if (str.equals("sin")) return FunctionExpression.SIN;
        if (str.equals("cos")) return FunctionExpression.COS;
        if (str.equals("tan")) return FunctionExpression.TAN;
        if (str.equals("sqrt")) return FunctionExpression.SQRT;

        return 0;
    }

    public static String getAllFunctions() { return "sin|cos|tan|sqrt"; }

    @Override
    public String getRepresentation() {
        return switch (function) {
            case SIN -> "sin" + argument.getRepresentation();
            case COS -> "cos" + argument.getRepresentation();
            case TAN -> "tan" + argument.getRepresentation();
            case SQRT -> "sqrt" + argument.getRepresentation();
            default ->  argument.getRepresentation();
        };
    }
}
