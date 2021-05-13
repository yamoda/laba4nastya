package model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SequenceExpression implements Expression {
    protected LinkedList<Term> terms;

    public SequenceExpression() {
        this.terms = new LinkedList<Term>();
    }

    public SequenceExpression(Expression a, boolean positive) {
        this.terms = new LinkedList<Term>();
        this.terms.add(new Term(positive, a));
    }

    public void add(Expression a, boolean positive) {
        this.terms.add(new Term(positive, a));
    }

    public List<Expression> getArguments() {
        return terms.stream()
                    .map(Term::getExpression)
                    .collect(Collectors.toList());
    }

    public String getRepresentation() { return ""; }
}
