package uk.co.gherkindesign.calc3;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;


public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        String s = "122+456.0 Hello I am a monkey Sin(X)+10";
        StreamTokenizer st = new StreamTokenizer(new StringReader(s));
        st.ordinaryChar('(');
        st.ordinaryChar('+');
        st.ordinaryChar('.');
        st.parseNumbers();
        while ((st.nextToken() != StreamTokenizer.TT_EOF)) {
            if(st.ttype == '(') {
                System.out.println("(");
            } else if (st.ttype == ')') {
                System.out.println(")");
            } else if (st.ttype == '.') {
               System.out.println("dot");
            } else if (st.ttype == '+') {
                System.out.println("PLUS");
            }
            System.out.println(st.sval + "\t" + st.nval);
        }

    }
}
