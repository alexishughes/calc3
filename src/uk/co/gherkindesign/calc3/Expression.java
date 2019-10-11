package uk.co.gherkindesign.calc3;

import java.io.IOException;
import java.util.regex.*;
import java.io.StreamTokenizer;

public class Expression {
    public Expression(String Descriptor) {
        this.strDescriptor = Descriptor;
    }

    public String strDescriptor;
    public Pattern ptnNumeric = Pattern.compile("\\-?[0-9]+\\.?[0-9]*");
    public Pattern ptnPlus = Pattern.compile("\\+.+");
    public Pattern ptnSin = Pattern.compile("Sin.+");
    public Pattern ptnFactorial = Pattern.compile(".+!");
    public boolean blnNeutralBracketOperatorFound;
    public FormattedExpression Parse() {
        int intLength = strDescriptor.length();
        if (ptnNumeric.matcher(strDescriptor).matches()) {
            return new Numeric(strDescriptor);
        } else if (ptnSin.matcher(strDescriptor).matches()) {
            return null;
        } else {
            int intPos = 0;
            int intBracketLevel = 0;
            while (intPos < intLength) {
                if (strDescriptor.charAt(intPos) == '(') {
                    intBracketLevel++;
                    intPos++;
                    continue;
                } else if (strDescriptor.charAt(intPos) == ')') {
                    intBracketLevel--;
                    intPos++;
                    continue;
                } else if (intBracketLevel == 0) {
                    if (ptnPlus.matcher(strDescriptor.substring(intPos)).matches()) {
                        Expression expLhs = new Expression(strDescriptor.substring(0, intPos - 1));
                        Expression expRhs = new Expression(strDescriptor.substring(intPos + 1)); // this could be +3 e.g. Sin
                        FormattedExpression forexpLhs = expLhs.Parse();
                        FormattedExpression forexpRhs = expRhs.Parse();
                        return new FormattedExpressionPlus(forexpLhs, forexpRhs);
                    } else continue;
                } else continue;
            }
            if(!blnNeutralBracketOperatorFound) {
                this.strDescriptor = strDescriptor.substring(1, intLength -1);
                return Parse();
            }
            else return null;
        }
    }
}
