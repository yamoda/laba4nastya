package model;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperandReader {
    private LinkedList<OperandInfo> operandInfos;
    private LinkedList<Operand> operands;

    private static OperandReader expressionOperandReader = null;

    public OperandReader() {
        super();
        operandInfos = new LinkedList<OperandInfo>();
        operands = new LinkedList<Operand>();
    }

    public static OperandReader getExpressionTokenizer() {
        if (expressionOperandReader == null) expressionOperandReader = createExpressionTokenizer();
        return expressionOperandReader;
    }

    private static OperandReader createExpressionTokenizer() {
        OperandReader operandReader = new OperandReader();

        operandReader.add("[+-]", Operand.PLUSMINUS);
        operandReader.add("[*/]", Operand.MULTDIV);
        operandReader.add("\\^", Operand.RAISED);
        operandReader.add("[%]", Operand.REMAINDER);

        String funcs = FunctionExpression.getAllFunctions();
        operandReader.add("(" + funcs + ")(?!\\w)", Operand.FUNCTION);

        operandReader.add("\\(", Operand.OPEN_BRACKET);
        operandReader.add("\\)", Operand.CLOSE_BRACKET);
        operandReader.add("(?:\\d+\\.?|\\.\\d)\\d*(?:[Ee][-+]?\\d+)?", Operand.NUMBER);

        return operandReader;
    }

    public void add(String regex, int token) { operandInfos.add(new OperandInfo(Pattern.compile("^(" + regex + ")"), token)); }

    public void tokenize(String str) {
        String s = str.trim();
        int totalLength = s.length();

        operands.clear();

        while (!s.equals("")) {
            int remaining = s.length();
            boolean match = false;

            for (OperandInfo info : operandInfos) {
                Matcher m = info.regex.matcher(s);
                if (m.find()) {
                    match = true;
                    String tok = m.group().trim();

                    s = m.replaceFirst("").trim();
                    operands.add(new Operand(info.operand, tok));
                    break;
                }
            }
        }
    }

    public LinkedList<Operand> getTokens() {
        return operands;
    }
}
