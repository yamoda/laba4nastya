package model;

public class RemainderExpression extends SequenceExpression {
    public RemainderExpression() { super(); }

    public RemainderExpression(Expression a, boolean positive) { super(a, positive); }

    public int getType() { return Expression.REMAINDER_NODE; }

    public double getValue() {
        double prod = terms.getFirst().expression.getValue();
        for (int termIdx=1; termIdx<terms.size(); termIdx++) prod %= terms.get(termIdx).expression.getValue();
        return prod;
    }

    @Override
    public String getRepresentation() {
        String representation = "(";

        representation += terms.get(0).expression.getRepresentation();
        for (int termIdx=1; termIdx<terms.size(); termIdx++) {
            if (terms.get(termIdx).positive) representation += "%" + terms.get(termIdx).expression.getRepresentation() ;
        }

        return representation +")";
    }
}
