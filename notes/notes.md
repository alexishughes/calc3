# calc2
###### author: Alexis Hughes; date 2019-10-03T22:47:00Z;
## Brief
The prospect of writing a simple calculator got me really excited about regular expressions (<code>Regex</code>) 
and compilers which use this programming syntax significantly. I found a recursive regex was available in some 
implementations of the function in which you can repeat the whole outer pattern within.

As a first attempt, it would seem, a more explicit way to approach the problem would be a recursive algorithm that 
simply utilised Regex to parse the text inputted.

My first instinct was to work with parentheses - obviously - and to work outwards.

In other words, keep digging into the brackets until you find a simple sum, evaluate it and then replace the contents 
with the answer.

Of course, the precedence of operators can be handled by adding extra parentheses on the strongest 
operators first etc.

### First attempt
Well it didn't complete, it was really just a hack to get used to get a feel for the commands being used.
This is an interesting project. What makes it interesting is that you are using both *inheritance* and recursion. 
And I have never done that before.

Previous algorithm courses always strayed away from using classes as data storage objects because of the
computational expense of this. However, this problem may only include order of 10->100 deep recursions so
we can have luxuries.

### New info
After actually writing out an algorithm on paper and meditating on the problem generally. I felt that a
programmer could probably take the problem a lot further. Even as far as simplifying algebraic functions.

I am already writing plans for this as the structure will be very similar and, inshallah, it will be done 
soon.

### Debunking the "seek and replace" method
There's nothing wrong with looking to "atomic" (from the Greek meaning indivisible) sums, replacing 
them with numbers, and then parsing the result into a <code>float</code>. However, it destroys the 
structure of the sum being done.

What you actually want is a sort of recursive *sum object*.

It would go something like this in pseudo code.

        class Expression {
            String strDescriptor,
            float fltEvaluation,
            enum operator,
            Expression leftExpression,
            Expression rightExpression,
            Expression Solve() {
                if(fltEvaluation != null) {
                    return this;
                }
                else {
                "look for regex 123.354 - in other words,
                a number if found,
                fltEvaluation = float.Parse(descriptor);
                return this;
                
                else {
                "look for regex: (((dsfkdk)sdk)weri) * (sdk) - 
                i.e. any number of brackets which open and
                close the same number of times followed by 
                an operator followed by the same pattern.";
                
                leftExpression.strDescriptor = "lhs"
                rightExpession.strDescriptor = "rhs"
                operator = "*"
                
                leftExpression.Solve();
                rightExpression.Solve();
                
                // evaluation for each must now be set.
                // otherwise they would not have returned.
                // the recursion is defined (although there is no Exception
                // handling
                
                fltEvaluation = leftExpression.fltEvaluation * 
                rightExpression.fltEvaluation;
                
                return this;
                }
            }
        }

What is elegant about this pseudo-code is that the newly returned expression object is in fact
a tree of how to evaluate the sum.

Even though this assignment is only interested in fltEvaluation extensions to the code are very 
possible.

Suppose we want to evaluate fractions, surd numbers (e.g. square roots), or even algebraic expressions,
we have an <code>Expression</code> object that we can work with.

Whilst this is going to be quite involved the basic change can be seen by looking at what this algorithm
is actually doing.

It's not really solving anything, it is merely parsing a string into a tree.

So instead of looking for a <code>float</code> to trigger the *leaf* of the recursion tree and then converting 
all parent objects to *leaves*, we need to define different types.

##### Exercise
The pseudo-code will work but it is a bit sloppy for instance the first if clause - if fltEvaluation is set
return the expression but all the paths that set it already return.

Can you see another mistake?

###### Answer
There is nothing to strip parentheses st each iteration.

### Exploration of next project - fractions
In order to ensure the project *is* limited to fractions we will have to exclude the power term 
for anything but "Z" e.g. 0, +/-1, +/-2, +/-3, ...

Whether it is better to write out multiplications in <code>String</code> format first is an *IDK* but 
it is certainly easier and will keep the code cleaner. If a debug is added, this will make it more lengthy
but perhaps easier to understand for a younger person.

then all you have to do is think of a way of storing fractions or *Rational Numbers* "Q"
The form is always p/q where p is a member of "Z" and q is a member of "N".

The 5 number operators are trivial to work out, but we will need a lowest common denominator (LCD) 
facility at some point.

Personally, I would go fancy and use prime factorization. e.g. 28 = 2^2 * 7^1 or there may be a 
<code>Math</code> function we can use already.

###### Next Section: Implementation

## Implementation of calc2
### Ideas

Brackets seem a really good idea. If we are stripping them off 1 layer at a time, why not put everything
inside them.

There must therefore be a pre-recursion parse and replace system.

so:

    3*(5/8+7)-2^4*.5
    -> powers max priority
    3*(5/8+7)- (2^4) *.5
    -> multiples next
    (3*(5/8+7)) - ((2^4) *.5)
    -> then plus
    (3*(5/(8+7))) - ((2^4) *.5)
    -> then minus
    (  (3*(5/(8+7))) - ((2^4) *.5)  )
    -> then division
    "note here that the brackets are already present when we add 
    brackets for an operator we have to add around the first neutrally bracketted expression
    on either side:
    so we are putting in unnecessary brackets:
    - and indeed we can see from the original sum that they are unnecessary
    - it is left unattempted to evaluate if double brackets around an initial "expression-let" 
    indicates unnecessary brackets in the original string and how to show a warning.
    
    ( ( 3 * ( (5/(8+7) ) ) ) - ((2^4) * .5 ))
    
    finally numbers ... really? yes. this will be useful if, for instance, we wish to replace 0.5 with 5/10
    
    (((3)*(((5)/((8)+(7)))))-(((2)^(4))*(.5)))
    
    ok. now I admit taht does not look good to a young person. There *should* be a way to prettify this 
    expression in terms of display.
    
    Q: would it be adequate to strip number brackets and *still unproven* "unnecessary" brackets.
    
    it would look like this:
    
    ( ( 3 * (5/(8+7) ) - ( (2^4) * 0.5 ) )
    
    finally you could strip the outer bracket (even though it is syntactically more correct.
    
    ( 3 * (5/(8+7) ) - ( (2^4) * 0.5 )
    
Once the full expression is made: evaluation will be trivial.

### New pseudo-code
1. check:
    1. any illegal characters?
    1. do up/down brackets add to zero?
    1. are there any repeated operators +- would be legit and replaced by 1 + -5 -> 1 + (0-5) or 1 - 5
        * which
            * don't know yet
1. If all true, continue, else throw an exception.
    * does this imply a legit sum?
        * actually i think it does - i can't prove it other than to say if 
        the next part is true then it is a sum.
            * break this down as logical proof - "love to!! but i don't have time"
1. Apply brackets around operators in order ^,*,+,-,/,number
    1. The rule is start at operator look right 1 character at a time.
        1. if bracket open look until closed again.
        1. if number look until number complete (i.e an operator)
        1. as soon as expression or number is passed, insert close bracket
    1. do the same working 1 character at a time to the left.
        * as above but with opening bracket.
    * is there an opportunity for recursive regex here?
        * don't know yet.
1. run pseudo-code shown before.
1. output the <code>fltEvaluation</code>
1. finish.

#### next step : write the code. here's the updated pseudo-code.

        class Expression {
            String strDescriptor;
            float fltEvaluation,
            enum operator,
            Expression leftExpression,
            Expression rightExpression,
            Expression Solve() {
                // brackets will be present.
                strip brackets
                if (regex /d+) 
                // i.e. 4
                {
                    Parse to fltEvatuation
                    return this;
                }
                else if (regex (.(.(.).).) operator (.) )
                // i.e. any with neutral up/down brackets on either side of an operator
                // *forgot to mention 2 + 2 + 2 !!!*
                // actually pre-parse must handle this - look for operators from left and generate
                //  ( 2 + 2 ) + 2 ->  ( ( 2 + 2 ) + 2 ) -> ( ( (2) + (2) ) + (2) )
                // must read a bit of computing book regarding this.
                // with that in place, this should be the only possibility.
                leftExpression.descriptor = LHS;
                rightExpression.descriptior = RHS;
                Evaluate Both
                this.fltEvaluation = "do operation on answers";
                return this;
            }

### Update
###### Date: 2019-10-07;
Well, I had always had this mad plan to increase the scope of this exercise to 
fractions, or even algebraic symbols.

I spent quite a lot of time working with operator classes inherited from abstract classes. And,
again as the number of steps will not be massive, we can afford a little reflection. 

So I felt of I could write an Operator interface which was implemented by sub-classes, I could 
actually use reflection to determine the operator Symbol, it's precedence in the hierarchy (if no 
brackets present) and possibly inject pattern to be matched.

Have a look at this class structure for Plus.
         
        public static class BinaryEOE {
                public interface Operator {
                    public default Expression Operate(Expression LHS, Expression RHS) throws Exception {
                        return null;
                    }
                }
                public static class Plus implements Operator {
                    public static final String strDescriptor = "+";
                    public Expression Operate(Expression LHS, Expression RHS) throws Exception {
                        if(LHS.getClass().equals(Number.class) && RHS.getClass().equals(Number.class)) {
                            Number number = new Number(LHS.dblNumericEvaluation + RHS.dblNumericEvaluation);
                            return number;
                        } else {
                            String message =
                                    "Plus Operation only implemented for Number + Number.\n"
                                            + "Was given \" " + LHS.getClass().toString()
                                             + strDescriptor + RHS.getClass().toString() + "\"\n"
                                            + "This is not yet implemented.";
        
                            Exception exception = new Exception(message);
                            throw exception;
                        }
                    }
                }

Clearly the Exceptions need working with and the class it static (I learned that, for some
reason which will no doubt become clear, you cannot create static classes at root level)

We have the method <code>Operate</code> which checks to see if the 2 operands are both 
of the <code>Number</code> class and throws an exception if they are not - this is good.
There is however no way of knowing what part of the sum we are looking at so I will have to 
index operators, operations and braces somewhere along the line.

The <code>Expression_Operator_Expression</code> uses the <code>Plus</code> static class like this.

 package uk.co.gherkindesign.calc2;
 
     public class Expression_Operator_Expression extends Expression {
     
     
         public Expression expLHS;
         public Expression expRHS;
         public Symbols.BinaryEOE.Operator operator;
     
         public Expression_Operator_Expression(String Descriptor) {
             super(Descriptor);
         }
     
         public Expression Evaluate() throws Exception {
             Expression expOutput = operator.Operate(expLHS.Evaluate(), expRHS.Evaluate());
             return expOutput;
         }
         public double NumericEvaluation() {
             return this.dblNumericEvaluation;
         }
     
         public void NumericEvaluate() {
             this.dblNumericEvaluation = Double.parseDouble(this.strDescriptor);
         }
     }

The recursion is obvious (even though not all classes are shown), evaluate both sides (whatever that means).
then combine them with the operator. If one is an <code>Expression_Operator_Expression</code> it will be recursively 
and the recursion will only stop if it reaches a number which can then be added.

Actually, the code may be flawed - it is untested at this commit <code>50b56ecfa15e1ebb0259c3f3b8acbd1942f81d75</code>

The whole model needs an update.

### New thinking
The demarcation between *parsing* the string and *performing operations* has thus far been hazy. 
I've never written a recursive calculator with classes for adding ind subtracting with reflection
before - and all yuo want is the answer each time so you can collapse the sum.

*But*... In the deathless words of Tim Roughgarden (Stanford Algorithms - http://www.coursera.org): *"Can we do better?"*

The answer is yes. What I had thought as a waste of time: instantiating operators because I could not create static 
classes, Casting Expressions to numbers in an explicit way - when all we really wants is the answer to 2 + 2 * 10; is good.

The <code>Expression</code> class should hold a string *and* a recursive object.

It should look something like this:

        class Expression {
            String strDescriptor;
            Expression Parse() {
                if(!"string_neutral_brackets") {
                    throw exception; }
                else if( "atomic" e.g. a number ) {
                    return Number(Parse strDescriptor); // Number extends Expression and can be evaluated.
                } else if( "String_neutral_brackets - Operator - String_neutral_brackets" ) {
                    split the string and create 2 new Expression Objects, ***Parse Them***
                    they have to be either: numbers, E-o-E objects
                    return Expression_Operator_Expression("with appropriate operator instance")
                    
                } else {
                    strip outer brackets;
                    Create New Expression Object and Parse it - return the result.
                }   
                
Because this recursion stops when it reaches a number we *should* get a good tree.

There needs to be a way of displaying this. I shall go with tabs/spaces/and newlines.

The next step is to evaluate - for numbers this will be easy, for fractions, not so easy.

We have to find a way of gathering up terms.

if you have a +- of 2 divisions you can obviously multiply the denominators together and simplify
* is easy too / is easy too so we don't need too many up/down crawls to get a result.

This is a bit flying brick e.g. 1/2 + 1/2 + 1/2 ->
    
    (1/2) + (1/2) +(1/2) ->
    ( (1/2) + (1/2) ) + (1/2) ->
    ( (2 + 2)/4 ) + (1/2) ->
    ( (4/4) + (1/2) ) ->
    ( (8 + 4)/8 ) ->
    12/8 ->
    3/2
    
But it will work.

Introducing Lowest Common denominator earlier in the sum will keep things simple.

I feel strongly at this stage that there should be some sort of array feature for commutable operators.

e.g. 2 + 2 + 2

or 3 * 7 * 8

It's not strictly necessary but it would make things more human readable.

I probably have to make a formal analysis of how to proceed when an EOE consists of various EOE O EOE 
clauses.

#### Breath of fresh air from I. Wakeman.
This code was posted as the answer and I am trying not to read it too much - guess it's okay because I have more or 
less done this.

        package uk.ac.susx.inf.ianw.simpleCalculator;
        
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.StreamTokenizer;
        import java.io.StringReader;
        
        /**
         * @author ianw
         *
         * Simple command line parser
         */
        public class SimpleCalculator {
        	private StreamTokenizer st;
        	
        	public SimpleCalculator() {
        		super();
        		
        	}
        	
        	private void createScanner(String s) {
        		this.st = new StreamTokenizer(new StringReader(s));
        		st.ordinaryChar('(');
        		st.ordinaryChar(')');
        		st.ordinaryChar('+');
        		st.ordinaryChar('-');
        		st.ordinaryChars('0', '9');
        	}
        
        	public int parseSum(String s) throws SyntaxException, IOException {
        		createScanner(s);
        		st.nextToken();
        		int t1 = parseTerm();
        		int value = 0;
        		st.nextToken();
        		if(st.ttype == st.TT_EOF) {
        			return t1;
        		}
        		if(st.ttype == '+') {
        			st.nextToken();
        			value =  t1 + parseTerm();
        		} else if(st.ttype == '-') {
        			st.nextToken();
        			value = t1 - parseTerm();
        		} else {
        			throw new SyntaxException("Expected '+' or '-'");
        		}
        		st.nextToken();
        		if(st.ttype != st.TT_EOF) {
        			throw new SyntaxException("Unexpected characters at the end of the line");
        		}
        		return value;
        	}
        	
        	public int parseTerm() throws SyntaxException, IOException {
        		if(st.ttype == '(') {
        			st.nextToken();
        			int val = parseTerm();
        			st.nextToken();
        			if(st.ttype == ')') {
        				return val;
        			} else throw new SyntaxException("Expected ')'");
        		} else if(st.ttype >= '0' && st.ttype <= '9') {
        			return st.ttype - '0';
        		} else throw new SyntaxException("Expected a single digit number or a '('");
        	}
        	
        	public static void main(String args[]) throws IOException, SyntaxException {
        		SimpleCalculator sc = new SimpleCalculator();
        		// Read the string from standard input
        		String input = "";
        		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        		do {
        			System.out.println("What expression do you want to calculate?");
        			input = br.readLine();
        			System.out.println("The answer is " + sc.parseSum(input));
        		} while(!input.equals(""));
        		System.out.println("Bye");
        	}
        }

Well if I used StringTokenizer that would help a lot.

#### New pseudo-code
###### Date: 2019-10-07T21:37:00Z;
        void Parse(string) {
            get first token;
            if "(" {
                get everything until bracket count is zero.
                Parse(that) -> Expression object (don't know what to do with this);
                // maybe an expression object owns one or more strings
                // it's a bit of a chicken and egg problem
                // if expression owns a string and Parse outputs an expression, 
                // it may have to mutate itself into an inherited class. not sure if this is possible
                // Maybe Expression superclass owns exactly 1 string.
                // On parsing that string it has to produce either a Number object or an EO,OE,EOE object
                // These can posses multiple Expression objects which will eventually be parsed.
                // So recursion work is not done until E superclasses are Parsed into extension classes.
                // So the original object... say it's "2 + 2" Parses to EOE with 2,2,+
                // 2 + (2 * 3) goes to EOE with 2, "2*3", +
                // sometime on "constructing" EOE we have to Parse 2*3 with if E1 = Number leave it, if "E" Parse it.
                // I think we should get EOE with 2,(EOE with 2,3,*),+ as lonf as we can have Numbers and 
                // formatted expressions as well as string expressions treated by the same code.
            };
            if "OE operator" {
                get everything until next operator at zero bracket status
                Parse(that)
                call it an Operator_Expression object with appropriate properties
                ie. OE(Sin,"blah blah") on construct OE(Sin, ?) if ? is Expression parse it -> must go to number or
                formatted expression, swap it out, if number or formatted expression plug it in.
                nb. if formatted expression it probably came from elsewhere.
            }
            if "Number" {
                parse until next EO operator or EOE operator, anything else throw Ex
                if EO make expression
            }

##### Next step: code it!!!

Actually, it might be possible to include operator precedence in the parsing stage.

###### News as at 2019-10-08 StreamTokenizer and StringTokenizer are deprecated.

Oh well, will have to use Regex and possibly Scanner.