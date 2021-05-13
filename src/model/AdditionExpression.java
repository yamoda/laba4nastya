package model;

public class AdditionExpression extends SequenceExpression {
    public AdditionExpression() { super(); }

    public AdditionExpression(Expression a, boolean positive) { super(a, positive); }

    public int getType() { return Expression.ADDITION_NODE; }

    public double getValue() {
        double sum = 0.0;
        for (Term t : terms) {
            if (t.positive) sum += t.expression.getValue();
            else sum -= t.expression.getValue();
        }
        return sum;
    }

    @Override
    public String getRepresentation() {
        String representation = "(";

        if (terms.get(0).positive) representation += terms.get(0).expression.getRepresentation();
        else representation += "-" + terms.get(0).expression.getRepresentation();

        for (int termIdx=1; termIdx<terms.size(); termIdx++) {
            if (terms.get(termIdx).positive) representation += "+" + terms.get(termIdx).expression.getRepresentation();
            else representation += "-" + terms.get(termIdx).expression.getRepresentation();
        }

        return representation +")";
    }
}
