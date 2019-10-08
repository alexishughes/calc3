package uk.co.gherkindesign.calc3;

import java.io.IOException;
import java.io.StringReader;
import java.io.StreamTokenizer;

public class Expression {
    public String strDescriptor;
    private StreamTokenizer st;
    private void createScanner(String ExpressionDescriptor) {

        // placeholder code
        this.st = new StreamTokenizer(new StringReader(strDescriptor));
        st.ordinaryChar('(');
        st.ordinaryChar(')');
        st.ordinaryChar('+');
        st.ordinaryChar('-');

    }
    public FormattedExpression Parse() throws IOException {
        st.nextToken();
        if(st.ttype == StreamTokenizer.TT_NUMBER)
        {
            return new NumberExpression(st.sval);
        } else {
            return new FormattedExpression();
        }

    }
}
