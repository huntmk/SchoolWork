/**
   This program evaluates (interprets) an Abstract Syntax Tree (AST)
   that is defined by the following grammar.

       Prog ::= Exp
              | '(' 'prog' (Fun | Exp)* Exp ')'

        Fun ::= '(' 'fun' VARIABLE Lambda ')'   // a function declaration

     Lambda ::= '(' 'lambda' VARIABLE* Exp ')'  // formal parameters followed by function body

        Exp ::= Apply
              | If
              | While
              | Begin
              | Var
              | Set
              | Print
              | BExp
              | AExp
              | INTEGER
              | BOOLEAN
              | VARIABLE

      Apply ::= '(' 'apply' Exp Exp* ')'  // function value followed by actual parameters

         If ::= '(' 'if' Exp Exp Exp ')'

      While ::= '(' 'while' Exp Exp ')'

      Begin ::= '(' 'begin' Exp+ ')'

        Var ::= '(' 'var' VARIABLE Exp ')'

        Set ::= '(' 'set' VARIABLE Exp ')'

      Print ::= '(' 'print' Exp ')'

       BExp ::= '(' '||'  Exp Exp+ ')'
              | '(' '&&'  Exp Exp+ ')'
              | '(' '!'   Exp ')'
              | '('  EqOp Exp Exp ')'
              | '(' RelOp Exp Exp ')'

       EqOp ::= '==' | '!='
      RelOp ::= '<' | '>' | '<=' | '>='

       AExp ::= '(' '+' Exp Exp* ')'
              | '(' '-' Exp Exp? ')'
              | '(' '*' Exp Exp+ ')'
              | '(' '/' Exp Exp  ')'
              | '(' '%' Exp Exp  ')'
              | '(' '^' Exp Exp  ')'

    INTEGER ::= [-|+][0-9]+
    BOOLEAN ::= 'true' | 'false'
   VARIABLE ::= [a-zA-Z][a-zA-Z0-9]*
*/

public class Evaluate
{
   public static int DEBUG = 1;

   public static Environment globalEnv; // reference to the global environment object
                                        // this is needed to implement lexical scope
   /**
      The methods evaluateProg(), evaluateExp(), evaluateAexp(), evaluateBexp(),
      and evaluateRexp() are essentially a post-order traversal of the abstract
      syntax tree.
   */
   public static Value eval(Tree tree) throws EvalException
   {
      // Instantiate a global environment object.
      globalEnv = new Environment();

      return evaluateProg( tree, globalEnv );
   }//eval()


   // Evaluate a prog
   public static Value evaluateProg(Tree tree, Environment env) throws EvalException
   {
      final Value result;  // a blank final

      // Check which kind of Prog we have.
      if ( ! tree.getElement().equals("prog") )
      {
         // Evaluate the single expression.
         result = evaluateExp( tree, env );
      }
      else
      {
         // Evaluate each Fun or Exp in the Prog.
         // Any Fun will have the side effect of putting
         // a function name in the global environment.
         // Any Var expressions will have the side effect
         // of putting a variable in the environment chain.
         // Any Set expressions will have the side effect
         // of changing a value in the environment chain.
         // Any Print expressions will have the side effect
         // of printing an output.
         // Any other expressions would be pointless!
         for (int i = 0; i < tree.degree()-1; i++)
         {
            if ( tree.getSubTree(i).getElement().equals("fun") )
            {
               handleFun( tree.getSubTree(i), env );
            }
            else
            {
               evaluateExp( tree.getSubTree(i), env );
            }
         }

         // Evaluate the last expression and use its
         // value as the value of the prog expression.
         result = evaluateExp( tree.getSubTree(tree.degree()-1), env );
      }

      return result;
   }//evaluateProg()


   /**
      Handle a function definition. Notice that the return type
      is void since Fun isn't an expression.

      This method mutates the global environment object.
      The Value object put into the environment by this method
      has the tag "lambda" and its value field is a reference
      to the function's "lambda expression".
   */
   public static void handleFun(Tree tree, Environment env) throws EvalException
   {
      // Get the function name.
/*1*/ final String name = tree.getSubTree(0).getElement();

      // Get the "lambda expression" and create a function Value.
/*2*/ final Value lambda = new Value( tree.getSubTree(1) );

      // Add the <name, value> pair to the global environment.
/*3*/ env.add(name, lambda);

      if (DEBUG > 0) System.out.println( env + "\n" ); // for debugging purposes
   }//handleFun()


   // Evaluate an expression
   public static Value evaluateExp(Tree tree, Environment env) throws EvalException
   {
      final Value result;  // a blank final

      final String node = tree.getElement();

      if ( node.equals("apply") )
      {
         result = evaluateApply(tree, env);
      }
      else if ( node.equals("if") )
      {
         result = evaluateIf(tree, env);
      }
      else if ( node.equals("while") )
      {
         result = evaluateWhile(tree, env);
      }
      else if ( node.equals("begin") )
      {
         result = evaluateBegin(tree, env);
      }
      else if ( node.equals("var") )
      {
         result = evaluateVar(tree, env);
      }
      else if ( node.equals("set") )
      {
         result = evaluateSet(tree, env);
      }
      else if ( node.equals("print") )
      {
         result = evaluatePrint(tree, env);
      }
      else if ( node.equals("&&")
             || node.equals("||")
             || node.equals("!") )
      {
         result = evaluateBexp(tree, env);  // boolean expression
      }
      else if ( node.equals("==")
             || node.equals("!=") )
      {
         result = evaluateEqexp(tree, env);  // equality operator
      }
      else if ( node.equals("<")
             || node.equals(">")
             || node.equals("<=")
             || node.equals(">=") )
      {
         result = evaluateRelexp(tree, env);  // relational operator
      }
      else if ( node.equals("+")
             || node.equals("-")
             || node.equals("*")
             || node.equals("/")
             || node.equals("%")
             || node.equals("^") )
      {
         result = evaluateAexp(tree, env);  // arithmetic expression
      }
      else if ( tree.degree() == 0 )
      {
         if ( node.equals("true") || node.equals("false") )
         {
            result = new Value( node.equals("true") );
         }
         else if ( node.matches("^[-|+]*[0-9][0-9]*") )
         {
            result = new Value( Integer.parseInt( node ) );
         }
         else  // a variable
         {
            result = env.lookUp( node );
         }
      }
      else
      {
         throw new EvalException("invalid expression: " + tree);
      }

      return result;
   }//evaluateExp()


   /**
      This method "applies" a function value to actual parameters.
      This method evaluates the body of the function in an environment
      that binds the actual parameter values (from this function application)
      to the formal parameters (from the function's lambda expression).
   */
   private static Value evaluateApply(Tree tree, Environment env) throws EvalException
   {
      // Evaluate the apply's first parameter to a function value.
/*1*/ final Value funValue = evaluateExp( tree.getSubTree(0), env );

      // Check that what we are applying really is a function.
/*2*/ if ( ! funValue.tag.equals(Value.LAMBDA_TAG)) // runtime type check
      {
         throw new EvalException("bad function value: " + tree);
      }

      // Get a reference to the function's "lambda expression".
/*3*/ final Tree lambda = funValue.valueL;

      // Check that the number of actual parameters
      // is equal to the number of formal parameters.
      // (Actually, all we really need to know is that
      // the number of actual parameters is at least
      // the number of formal parameters.)
/*4*/ if ( tree.degree() != lambda.degree() ) // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      // Create a new environment object that is "nested"
      // in the global environment ("lexical scope") (or, is
      // "nested" in the current environment ("dynamic scope")).
      // This environment is used to bind actual parameter
      // values to formal parameter names.
      // (Note: Change globalEnv to env for dynamic scope.)
/*5*/ final Environment localEnv = new Environment(globalEnv, "Function Activation");

      // Bind, in the new environment object, each actual
      // parameter value to a formal parameter name.
/*6*/ for (int i = 1; i < tree.degree(); ++i) // iterate through the actual parameters
      {
         // Evaluate, using the current environment chain
         // (NOT the new local environment object)
         // an actual parameter expression's value.
/*7*/    final Value actualParamValue = evaluateExp( tree.getSubTree(i), env );

         // Retrieve, from within the lambda expression,
         // a formal parameter name.
/*8*/    final String formalParamName = lambda.getSubTree(i-1).getElement();

         // Bind, in the new local environment object, the actual
         // parameter value to the formal parameter name.
/*10*/   localEnv.add(formalParamName, actualParamValue);
      }

      if (DEBUG > 0) System.out.println( localEnv + "\n" ); // for debugging purposes

      // Evaluate the body of the lambda expression using the
      // new environment (which contains the binding of the actual
      // parameter values to the function's formal parameter names).
/*11*/final Value result = evaluateExp(lambda.getSubTree(tree.degree()-1), localEnv);

      // When this method returns, the local Environment
      // object that we created to hold the parameter bindings
      // becomes a garbage object (and will be garbage collected).
      // This is like "popping" a stack frame off of the call stack
      // in Java, C , or C++.

      return result;
   }//evaluateApply()


   // Evaluate an if-expression
   private static Value evaluateIf(Tree tree, Environment env) throws EvalException
   {
      final Value result;  // a blank final

      final Value conditionalExp = evaluateExp( tree.getSubTree(0), env );
      if ( ! conditionalExp.tag.equals(Value.BOOL_TAG) ) // runtime type check
      {
         throw new EvalException("illegal boolean expression: " + tree);
      }

      if ( conditionalExp.valueB )
      {
         result = evaluateExp( tree.getSubTree(1), env );
      }
      else
      {
         result = evaluateExp( tree.getSubTree(2), env );
      }

      return result;
   }//evaluateIf()


   // Evaluate a while-loop expression
   private static Value evaluateWhile(Tree tree, Environment env) throws EvalException
   {
      // evaluate the boolean condition
      Value conditionalExp = evaluateExp( tree.getSubTree(0), env );
      if ( ! conditionalExp.tag.equals(Value.BOOL_TAG) ) // runtime type check
      {
         throw new EvalException("illegal boolean expression: " + tree);
      }

      while ( conditionalExp.valueB )
      {
         // evaluate the body of the loop (for its side effects)
         evaluateExp( tree.getSubTree(1), env );
         // re-evaluate the boolean condition
         conditionalExp = evaluateExp( tree.getSubTree(0), env );
         if ( ! conditionalExp.tag.equals(Value.BOOL_TAG) ) // runtime type check
         {
            throw new EvalException("illegal boolean expression: " + tree);
         }
      }

      // always return false for a while-loop expression
      return new Value( false );
   }//evaluateWhile()


   // Evaluate a begin expression
   private static Value evaluateBegin(Tree tree, Environment env) throws EvalException
   {
      // Create a new Environment object chained to (or "nested in")
      // the previous ("outer") environment object.
      final Environment newEnv = new Environment(env, "Local (begin)");

      // Evaluate each sub expression in the begin
      // expression (using the new environment chain).
      // The return value of each expression is
      // discarded, so any expression without a
      // side-effect is worthless.
      for (int i = 0; i < tree.degree()-1; i++)
      {
         evaluateExp( tree.getSubTree(i), newEnv );
      }

      // Evaluate the last expression and use its
      // value as the value of the begin expression.
      final Value result = evaluateExp( tree.getSubTree(tree.degree()-1), newEnv );

      // When this method returns, the local Environment
      // object that we created at the beginning of this method
      // becomes a garbage object (and will be garbage collected).
      // This is like "popping" a stack frame off of the call stack
      // in Java, C , or C++.

      return result;
   }//evaluateBegin()


   // Evaluate a var expression
   private static Value evaluateVar(Tree tree, Environment env) throws EvalException
   {
      // get the variable
      final String variable = tree.getSubTree(0).getElement();

      // get, and then evaluate, the expression
      final Tree expr = tree.getSubTree(1);
      final Value result = evaluateExp( expr, env );

      // declare the new, local, variable
      env.add(variable, result);

      if (DEBUG > 0) System.out.println( env + "\n" ); // for debugging purposes

      return result;
   }//evaluateVar()


   // Evaluate a set expression
   private static Value evaluateSet(Tree tree, Environment env) throws EvalException
   {
      // get the variable
      final String variable = tree.getSubTree(0).getElement();

      // get, and then evaluate, the expression
      final Tree expr = tree.getSubTree(1);
      final Value result = evaluateExp( expr, env );
      // update this variable in the environment
      env.update(variable, result);

      if (DEBUG > 0) System.out.println( env + "\n" ); // for debugging purposes

      return result;
   }//evaluateSet()


   // Evaluate a print expression
   private static Value evaluatePrint(Tree tree, Environment env) throws EvalException
   {
      final Value result = evaluateExp( tree.getSubTree(0), env );

      if (DEBUG > 0)
      {
         System.out.println( result );
      }
      else
      {
         System.out.println( result.toSimpleString() );
      }

      return result;
   }//evaluatePrint()


   // Evaluate a boolean expression
   private static Value evaluateBexp(Tree tree, Environment env) throws EvalException
   {
      boolean result = false;

      final String node = tree.getElement();

      Value value = evaluateExp( tree.getSubTree(0), env );
      if ( ! value.tag.equals(Value.BOOL_TAG) ) // runtime type check
      {
         throw new EvalException("not a boolean expression: "
                                  + tree.getSubTree(0));
      }
      result = value.valueB;

      if ( node.equals("&&") )
      {
         for (int i = 1; i < tree.degree(); i++)
         {
            if (result)
            {
               value = evaluateExp( tree.getSubTree(i), env );
               if ( ! value.tag.equals(Value.BOOL_TAG) ) // runtime type check
               {
                  throw new EvalException("not a boolean expression: "
                                           + tree.getSubTree(i));
               }
               result = result && value.valueB;
            }
            else  // short circuit the evaluation of '&&'
            {
               result = false;
               break;
            }
         }
      }
      else if ( node.equals("||") )
      {
         for (int i = 1; i < tree.degree(); i++)
         {
            if (! result)
            {
               value = evaluateExp( tree.getSubTree(i), env );
               if ( ! value.tag.equals(Value.BOOL_TAG) ) // runtime type check
               {
                  throw new EvalException("not a boolean expression: "
                                           + tree.getSubTree(i));
               }
               result = result || value.valueB;
            }
            else  // short circuit the evaluation of '||'
            {
               result = true;
               break;
            }
         }
      }
      else if ( node.equals("!") )
      {
         result = ! result;
      }

      return new Value( result );
   }//evaluateBexp()


   // Evaluate an equality expression (which is a kind of boolean expression)
   private static Value evaluateEqexp(Tree tree, Environment env) throws EvalException
   {
      final boolean result;  // a blank final

      final String op = tree.getElement();

      final Value valueL = evaluateExp( tree.getSubTree(0), env );
      final Value valueR = evaluateExp( tree.getSubTree(1), env );

      if ( op.equals("==") )
      {
         if ( ! valueL.tag.equals(valueR.tag) )
         {
            result = false;
         }
         else if ( valueL.tag.equals(Value.INT_TAG) )
         {
            final int resultL = valueL.valueI;
            final int resultR = valueR.valueI;
            result = resultL == resultR;
         }
         else // boolean data type
         {
            final boolean resultL = valueL.valueB;
            final boolean resultR = valueR.valueB;
            result = resultL == resultR;
         }
      }
      else // the '!=' operator
      {
         if ( ! valueL.tag.equals(valueR.tag) )
         {
            result = true;
         }
         else if ( valueL.tag.equals(Value.INT_TAG) )
         {
            final int resultL = valueL.valueI;
            final int resultR = valueR.valueI;
            result = resultL != resultR;
         }
         else // boolean data type
         {
            final boolean resultL = valueL.valueB;
            final boolean resultR = valueR.valueB;
            result = resultL != resultR;
         }
      }

      return new Value( result );
   }//evaluateEqexp()


   // Evaluate a relational expression (which is a kind of boolean expression)
   private static Value evaluateRelexp(Tree tree, Environment env) throws EvalException
   {
      final boolean result;  // a blank final

      final String op = tree.getElement();

      final Value valueL = evaluateExp( tree.getSubTree(0), env );
      if ( ! valueL.tag.equals(Value.INT_TAG) ) // runtime type check
      {
         throw new EvalException("not a integer expression: "
                                  + tree.getSubTree(0));
      }

      final Value valueR = evaluateExp( tree.getSubTree(1), env );
      if ( ! valueR.tag.equals(Value.INT_TAG) ) // runtime type check
      {
         throw new EvalException("not a integer expression: "
                                  + tree.getSubTree(1));
      }

      final int resultL = valueL.valueI;
      final int resultR = valueR.valueI;

      if ( op.equals("<") )
      {
         result = resultL < resultR;
      }
      else if ( op.equals(">") )
      {
         result = resultL > resultR;
      }
      else if ( op.equals("<=") )
      {
         result = resultL <= resultR;
      }
      else // if ( op.equals(">=") )
      {
         result = resultL >= resultR;
      }

      return new Value( result );
   }//evaluateRelexp()


   // Evaluate an arithmetic expression
   private static Value evaluateAexp(Tree tree, Environment env) throws EvalException
   {
      int result = 0;

      final String node = tree.getElement();

      final Value valueL = evaluateExp( tree.getSubTree(0), env );
      if ( ! valueL.tag.equals(Value.INT_TAG) ) // runtime type check
      {
         throw new EvalException("not a integer expression: "
                                  + tree.getSubTree(0));
      }
      final int resultL = valueL.valueI;
      int resultR = 0;

      Value valueR = null;
      if ( tree.degree() >= 2 )
      {
         valueR = evaluateExp( tree.getSubTree(1), env );
         if ( ! valueR.tag.equals(Value.INT_TAG) ) // runtime type check
         {
            throw new EvalException("not a integer expression: "
                                     + tree.getSubTree(1));
         }
         resultR = valueR.valueI;
      }

      if ( node.equals("+") )
      {
         if ( tree.degree() == 1 )
            result = resultL;
         else
         {
            result = resultL + resultR;

            for (int i = 2; i < tree.degree(); i++)
            {
               Value temp = evaluateExp( tree.getSubTree(i), env );
               if ( ! temp.tag.equals(Value.INT_TAG) ) // runtime type check
               {
                  throw new EvalException("not a integer expression: "
                                           + tree.getSubTree(i));
               }
               result += temp.valueI;
            }
         }
      }
      else if ( node.equals("-") )
      {
         if ( tree.degree() == 1 )
            result = -resultL;
         else
            result = resultL - resultR;
      }
      else if ( node.equals("*") )
      {
         result = resultL * resultR;

         for (int i = 2; i < tree.degree(); i++)
         {
            Value temp = evaluateExp( tree.getSubTree(i), env );
            if ( ! temp.tag.equals(Value.INT_TAG) ) // runtime type check
            {
               throw new EvalException("not a integer expression: "
                                        + tree.getSubTree(i));
            }
            result *= temp.valueI;
         }
      }
      else if ( node.equals("/") )
      {
         result = resultL / resultR;
      }
      else if ( node.equals("%") )
      {
         result = resultL % resultR;
      }
      else if ( node.equals("^") )
      {
         result = (int)Math.pow(resultL, resultR);
      }

      return new Value( result );
   }//evaluateAexp()
}
