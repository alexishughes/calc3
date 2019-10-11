package uk.co.gherkindesign.calc3;

import java.util.regex.Pattern;

    public class NumericZ extends Numeric {
        public static final Pattern ptnValid = Pattern.compile("-?[0-9]");
        public int intValue;
        public NumericZ() {};
        public NumericZ(String Descriptor) {
            this.intValue = Integer.parseInt(Descriptor);
        }
    }
