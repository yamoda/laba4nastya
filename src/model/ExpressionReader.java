package model;

import java.util.LinkedList;

public class ExpressionReader {
    LinkedList<Operand> operands;
    Operand lookahead;

    public Expression parse(String expression) {
        OperandReader operandReader = OperandReader.getExpressionTokenizer();
        operandReader.tokenize(expression);
        operands = operandReader.getTokens();

        return this.parse(operands);
    }

    public Expression parse(LinkedList<Operand> operands) {
        this.operands = new LinkedList<>(operands);
        lookahead = this.operands.getFirst();

        Expression expr = expression();
        return expr;
    }

    private void nextToken() {
        operands.pop();

        if (operands.isEmpty()) lookahead = new Operand(Operand.EPSILON, "");
        else lookahead = operands.getFirst();
    }

    private Expression expression() {
        Expression expr = signedTerm();
        return sumOp(expr);
    }

    private Expression sumOp(Expression expr) {
        if (lookahead.operand == Operand.PLUSMINUS) {
            AdditionExpression sum;

            if (expr.getType() == Expression.ADDITION_NODE) sum = (AdditionExpression)expr;
            else sum = new AdditionExpression(expr, true);

            boolean positive = lookahead.sequence.equals("+");
            nextToken();

            Expression t = term();
            sum.add(t, positive);

            return sumOp(sum);
        }

        return expr;
    }

    private Expression signedTerm() {
        if (lookahead.operand == Operand.PLUSMINUS) {
            boolean positive = lookahead.sequence.equals("+");
            nextToken();

            Expression t = term();
            if (positive) return t;
            else return new AdditionExpression(t, false);
        }

        return term();
    }

    private Expression term() {
        Expression f = factor();
        return termOp(f);
    }

    private Expression termOp(Expression expression) {
        if (lookahead.operand == Operand.MULTDIV) {
            MultiplicationExpression prod;

            if (expression.getType() == Expression.MULTIPLICATION_NODE) prod = (MultiplicationExpression)expression;
            else prod = new MultiplicationExpression(expression, true);

            boolean positive = lookahead.sequence.equals("*");
            nextToken();

            Expression f = signedFactor();
            prod.add(f, positive);

            return termOp(prod);
        }

        if (lookahead.operand == Operand.REMAINDER) {
            RemainderExpression prod;

            if (expression.getType() == Expression.MULTIPLICATION_NODE) prod = (RemainderExpression)expression;
            else prod = new RemainderExpression(expression, true);

            boolean positive = lookahead.sequence.equals("%");
            nextToken();

            Expression f = signedFactor();
            prod.add(f, positive);

            return termOp(prod);
        }

        return expression;
    }

    private Expression signedFactor() {
        if (lookahead.operand == Operand.PLUSMINUS) {
            boolean positive = lookahead.sequence.equals("+");

            nextToken();
            Expression t = factor();

            if (positive) return t;
            else return new AdditionExpression(t, false);
        }

        return factor();
    }

    private Expression factor() {
        Expression a = argument();
        return factorOp(a);
    }

    private Expression factorOp(Expression expression) {
        if (lookahead.operand == Operand.RAISED) {
            nextToken();
            Expression exponent = signedFactor();

            return new ExponentiationExpression(expression, exponent);
        }

        return expression;
    }

    private Expression argument() {
        if (lookahead.operand == Operand.FUNCTION) {
            int function = FunctionExpression.stringToFunction(lookahead.sequence);
            nextToken();

            Expression expr = argument();
            return new FunctionExpression(function, expr);
        }
        else if (lookahead.operand == Operand.OPEN_BRACKET) {
            nextToken();
            Expression expr = expression();

            nextToken();
            return expr;
        }

        return value();
    }

    private Expression value() {
        if (lookahead.operand == Operand.NUMBER) {
            Expression expr = new ConstantExpression(lookahead.sequence);
            nextToken();
            return expr;
        }

         return null;
    }
}
