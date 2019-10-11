package uk.co.gherkindesign.calc3;

import java.util.regex.Pattern;

public class NumericDbl extends Numeric {
    public static final Pattern ptnValid = Pattern.compile("-?[0-9]\\.[0-9]");
    public double intValue;
    public NumericDbl() {};
    public NumericDbl(String Descriptor) {
        this.intValue = Double.parseDouble(Descriptor);
    }
}