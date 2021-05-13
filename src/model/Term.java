package model;

import model.Expression;

public class Term {
    public boolean positive;
    public Expression expression;

    public Term(boolean positive, Expression expression) {
        super();
        this.positive = positive;
        this.expression = expression;
    }

    public Expression getExpression() { return expression; }
}
