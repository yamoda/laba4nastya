package model;

import java.util.regex.Pattern;

public class OperandInfo {
    public final Pattern regex;
    public final int operand;

    public OperandInfo(Pattern regex, int operand) {
        super();
        this.regex = regex;
        this.operand = operand;
    }
}