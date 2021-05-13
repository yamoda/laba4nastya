package model;

public class MultiplicationExpression extends SequenceExpression {
    public MultiplicationExpression() { super(); }

    public MultiplicationExpression(Expression a, boolean positive) { super(a, positive); }

    public int getType() { return Expression.MULTIPLICATION_NODE; }

    public double getValue() {
        double prod = 1.0;
        for (Term t : terms) {
            if (t.positive) prod *= t.expression.getValue();
            else prod /= t.expression.getValue();
        }

        return prod;
    }

    @Override
    public String getRepresentation() {
        String representation = "(";

        if (terms.get(0).positive) representation += terms.get(0).expression.getRepresentation();
        else representation += "-" + terms.get(0).expression.getRepresentation();

        for (int termIdx=1; termIdx<terms.size(); termIdx++) {
            if (terms.get(termIdx).positive) representation += "*" + terms.get(termIdx).expression.getRepresentation() ;
            else representation += "*(-" + terms.get(termIdx).expression.getRepresentation() +")";
        }

        return representation +")";
    }
}
