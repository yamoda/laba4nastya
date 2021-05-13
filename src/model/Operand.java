package model;

public class Operand {
    public static final int EPSILON = 0;
    public static final int PLUSMINUS = 1;
    public static final int MULTDIV = 2;
    public static final int RAISED = 3;
    public static final int FUNCTION = 4;
    public static final int OPEN_BRACKET = 5;
    public static final int CLOSE_BRACKET = 6;
    public static final int NUMBER = 7;
    public static final int REMAINDER = 8;

    public final int operand;
    public final String sequence;

    public Operand(int operand, String sequence) {
        super();
        this.operand = operand;
        this.sequence = sequence;
    }
}
