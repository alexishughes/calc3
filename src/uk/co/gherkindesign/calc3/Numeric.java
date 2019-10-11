package uk.co.gherkindesign.calc3;

import java.util.regex.Pattern;

public class Numeric extends FormattedExpression {
    public String strDescriptor;
    public Numeric() {};
    public Numeric(String Descriptor) {
        this.strDescriptor = Descriptor;
    }
    public Numeric Parse()
    {
        if(NumericN.ptnValid.matcher(this.strDescriptor).matches()) {
            return new NumericN(this.strDescriptor);
        } else if(NumericZ.ptnValid.matcher(this.strDescriptor).matches()) {
            return new NumericZ(this.strDescriptor);
        } else if(NumericDbl.ptnValid.matcher(this.strDescriptor).matches()) {
            return new NumericDbl(this.strDescriptor);
        } else
            return null; // this should never happen.
    }
}
