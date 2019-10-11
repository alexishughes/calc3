package uk.co.gherkindesign.calc3;

public class FormattedExpressionPlus extends FormattedExpression {
    public FormattedExpressionPlus(FormattedExpression Lhs, FormattedExpression Rhs)
    {
        forexpLhs = Lhs;
        forexpRhs = Rhs;
    }
    public FormattedExpression forexpLhs;
    public FormattedExpression forexpRhs;

}
