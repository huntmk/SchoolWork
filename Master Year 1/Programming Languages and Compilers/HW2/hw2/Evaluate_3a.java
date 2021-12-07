import javax.lang.model.util.ElementScanner6;

/**
   This program, as distributed, interprets Language_3.
   You are to modify this program so it interprets the
   following modified version of Language_3.

       Prog ::= Exp
              | '(' 'prog' Exp+ Exp ')'

        Exp ::= PostInc
              | PreInc
              | PostDec
              | PreDec
              | Var
              | Print
              | AExp
              | BExp
              | INTEGER
              | BOOLEAN
              | VARIABLE

    PostInc ::= '(' '++'  VARIABLE ')'
    PostDec ::= '(' '--'  VARIABLE ')'
     PreInc ::= '(' '+++' VARIABLE ')'
     PreDec ::= '(' '---' VARIABLE ')'

  Spaceship ::= '(' '<=>' Exp Exp ')'

        Var ::= '(' 'var' VARIABLE Exp ')'

      Print ::= '(' 'print' Exp ')'

       AExp ::= '(' '+' Exp Exp* ')'
              | '(' '-' Exp Exp? ')'
              | '(' '*' Exp Exp+ ')'
              | '(' '/' Exp Exp  ')'
              | '(' '%' Exp Exp  ')'
              | '(' '^' Exp Exp  ')'

       BExp ::= '(' '||'  Exp Exp+ ')'
              | '(' '&&'  Exp Exp+ ')'
              | '(' '!'   Exp ')'
              | '('  EqOp Exp Exp ')'
              | '(' RelOp Exp Exp ')'

       EqOp ::= '==' | '!='
      RelOp ::= '<' | '>' | '<=' | '>='

    INTEGER ::= [-|+][0-9]+
    BOOLEAN ::= 'true' | 'false'
   VARIABLE ::= [a-zA-Z][a-zA-Z0-9]*
*/

/*
   Course: CS 51530
   Name: Marcellus Hunt
   Email: mkhunt@pnw.edu
   Assignment: 2
*/


public class Evaluate_3a
{
   public static int DEBUG = 1;

   /**
      The methods evaluateProg(), evaluateExp(), evaluateAexp(), evaluateBexp(),
      and evaluateRexp() are essentially a post-order traversal of the abstract
      syntax tree.
   */
   public static Value eval(Tree tree) throws EvalException
   {
      Environment env = new Environment();  // global environment data structure

      return evaluateProg( tree, env );
   }//eval()


   // Evaluate a prog
   public static Value evaluateProg(Tree tree, Environment env) throws EvalException
   {
      Value result = null;

      // Instantiate the global environment object.
      env = new Environment();

      // Check whick kind of Prog we have.
      if ( ! tree.getElement().equals("prog") )
      {
         // Evaluate the single expression.
         result = evaluateExp( tree, env );
      }
      else
      {
         // Evaluate each Exp in the Prog.
         // Any Var expressions will have the side effect
         // of putting a variable in the environment.
         // Any Print expressions will have the side effect
         // of printing an output.
         // Any other expressions would be pointless!
         for (int i = 0; i < tree.degree()-1; i++)
         {
            evaluateExp( tree.getSubTree(i), env );
         }

         // Evaluate the last expression and use its
         // value as the value of the prog expression.
         result = evaluateExp( tree.getSubTree(tree.degree()-1), env );
      }

      return result;
   }//evaluateProg()


   // Evaluate an expression
   public static Value evaluateExp(Tree tree, Environment env) throws EvalException
   {
      Value result = null;

      String node = tree.getElement();

      if ( node.equals("var") )
      {
         result = evaluateVar( tree, env );
      }
      else if ( node.equals("print") )
      {
         result = evaluatePrint( tree, env );
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
      else if (node.equals("<=>") )
      {
         result = spaceship(tree, env);
      }
      else if (node.equals("++")
            ||node.equals("+++") )
      {
         result = increment(tree,env);
      }

      else if (node.equals("--")
            || node.equals("---"))
      {
         result = decrement(tree,env);
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
         else if ( env.defined(node) )  // a variable
         {
            result = env.lookUp( node );
         }
         else  // runtime check
         {
            throw new EvalException("undefined variable: " + node);
         }
      }
      else
      {
         throw new EvalException("invalid expression: " + tree);
      }

      return result;
   }//evaluateExp()

   //spaceship function
   private static Value spaceship(Tree tree, Environment env) throws EvalException
   {

      //runtime check
      if (tree.degree() != 2)
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      int result = 0;

      //get elements being comapred
      Value valueL = evaluateExp( tree.getSubTree(0), env );
      Value valueR = evaluateExp( tree.getSubTree(1), env );

      //runtime check 
      if (valueL.tag.equals(Value.BOOL_TAG) && valueR.tag.equals(Value.INT_TAG))
      {
         throw new EvalException("wrong type of arguments: " + tree);
      }

      //runtime check 
      if (valueL.tag.equals(Value.INT_TAG) && valueR.tag.equals(Value.BOOL_TAG))
      {
         throw new EvalException("wrong type of arguments: " + tree);
      }
      
      // boolean tags
      if (valueL.tag.equals(Value.BOOL_TAG))
      {
         boolean resL = valueL.valueB;
         boolean resR = valueR.valueB;

         if(resL == false && resR == true)
         {
            result = -1;
         }

         else if(resL == true &&  resR == false)
         {
            result = 1;
         }

         else 
         {
            result = 0;
         }

      }
      
      // int tags
      else if ( valueL.tag.equals(Value.INT_TAG) )
      {
         int resultL = valueL.valueI;
         int resultR = valueR.valueI;

         if (resultL < resultR)
            result = -1;

         else if (resultL > resultR)
            result = 1;

         else
            result = 0;
      }
      
      return new Value (result);
      
   }

   //pre/post increment function
   private static Value increment(Tree tree, Environment env) throws EvalException
   {
   
      String node = tree.getElement();

      //get value being incremented
      Value temp = evaluateExp( tree.getSubTree(0), env );
      
      if (tree.degree() > 1 )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      String strTemp = tree.getSubTree(0).getElement();

      if(!env.defined(strTemp) || strTemp.matches("^[-|+]*[0-9][0-9]*"))
      {
         throw new EvalException("cannot increment a literal: " + tree);
      }

      int result = 0;
      boolean boolRes = false;
      //post increment 
      if (node.equals("++"))
      {
         //boolean increment
         if(temp.tag.equals(Value.BOOL_TAG))
         {
            //use to test post increment
            int a = 0;
            
            //if temp boolean is true
            if (temp.valueB == true)
            {
               boolRes = true;

               return new Value(boolRes);
            }

            else 
            {
               //boolRes hold temp value
               boolRes = temp.valueB;

               //change value of temp
               temp.valueB = !boolRes;

               //update temp
               env.update(node,temp);

               //change boolRes back to true because it's increment
               boolRes = true;
               
               return new Value(!boolRes);

            }
            
         }

         //int increment
         else
         {
            //access value
            //then increment
            result= temp.valueI++;
            env.update(node, temp);
         }
         //return new Value(boolRes);
      }
      //pre increment
      else if (node.equals("+++"))
      {
         //boolean increment
         if(temp.tag.equals(Value.BOOL_TAG))
         {
            boolRes = true;
            temp.valueB =  boolRes;
            env.update(node,temp);
            return new Value(boolRes);

         }

         //int increment
         else 
         {
            //access value
            //then increment
            result= ++temp.valueI;
            env.update(node, temp);
         }
         
      }
      return new Value(result);
   }

   //pre/post decrement function
   private static Value decrement(Tree tree, Environment env) throws EvalException
   {
      String node = tree.getElement();

      //get value being incremented
      Value temp = evaluateExp( tree.getSubTree(0), env );

      
      if (tree.degree() > 1 )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      String strTemp = tree.getSubTree(0).getElement();

      if(!env.defined(strTemp) || strTemp.matches("^[-|+]*[0-9][0-9]*"))
      {
         throw new EvalException("cannot decrement a literal: " + tree);
      }

      int result = 0;
      boolean boolRes = false;

      //post decrement
      if (node.equals("--"))
      {
         //boolean decrement
         if(temp.tag.equals(Value.BOOL_TAG))
         {
            //use to test post increment
            int a = 0;
            
            //if temp boolean is true
            if (temp.valueB == false)
            {
               boolRes = false;

               return new Value(boolRes);
            }

            else 
            {
               //boolRes hold temp value
               boolRes = temp.valueB;

               //change value of temp
               temp.valueB = !boolRes;

               //update temp
               env.update(node,temp);

               //change boolRes back to false because it's decrement
               boolRes = false;
               
               return new Value(!boolRes);

            }
         }
         else
         {
         //access value
         //then decrement
         result= temp.valueI--;
         env.update(node, temp);
         }
      
      }
      //pre decrement
      else if (node.equals("---"))
      {
         //boolean decrement
         if(temp.tag.equals(Value.BOOL_TAG))
         {
            boolRes = false;
            temp.valueB =  boolRes;
            env.update(node,temp);
            return new Value(boolRes);

         }
         else
         {
            //access value
            //then decrement
            result= --temp.valueI;
            env.update(node, temp);
         }

      }
      return new Value(result);

   }


   // Evaluate a var expression
   private static Value evaluateVar(Tree tree, Environment env) throws EvalException
   {
      if ( 2 != tree.degree() )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      Value result = null;

      // get the variable
      String variable = tree.getSubTree(0).getElement();

      // get, and then evaluate, the expression
      Tree expr = tree.getSubTree(1);
      result = evaluateExp( expr, env );

      // check if this variable has already been declared
      if (! env.defined(variable))
      {
         env.add(variable, result);
      }
      else // this variable is already in the environment
      {
         env.update(variable, result);
      }

      if (DEBUG > 0) System.out.println( env + "\n"); // for debugging purposes

      return result;
   }//evaluateVar()


   // Evaluate a print expression
   private static Value evaluatePrint(Tree tree, Environment env) throws EvalException
   {
      if ( 1 != tree.degree() )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      Value result = evaluateExp( tree.getSubTree(0), env );

      if (Evaluate_3a.DEBUG > 0)
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

      String node = tree.getElement();

      Value value = evaluateExp( tree.getSubTree(0), env );
      if ( ! value.tag.equals(Value.BOOL_TAG) )  // runtime check
      {
         throw new EvalException("not a boolean expression: "
                                  + tree.getSubTree(0));
      }
      result = value.valueB;

      if ( node.equals("&&") )
      {
         if ( 2 > tree.degree() )  // runtime check
         {
            throw new EvalException("wrong number of arguments: " + tree);
         }

         for (int i = 1; i < tree.degree(); i++)
         {
            if (result)
            {
               value = evaluateExp( tree.getSubTree(i), env );
               if ( ! value.tag.equals(Value.BOOL_TAG) )  // runtime check
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
         if ( 2 > tree.degree() )  // runtime check
         {
            throw new EvalException("wrong number of arguments: " + tree);
         }

         for (int i = 1; i < tree.degree(); i++)
         {
            if (! result)
            {
               value = evaluateExp( tree.getSubTree(i), env );
               if ( ! value.tag.equals(Value.BOOL_TAG) )  // runtime check
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
         if ( 1 != tree.degree() )  // runtime check
         {
            throw new EvalException("wrong number of arguments: " + tree);
         }
         result = ! result;
      }

      return new Value( result );
   }//evaluateBexp()


   // Evaluate an equality expression (which is a kind of boolean expression)
   private static Value evaluateEqexp(Tree tree, Environment env) throws EvalException
   {
      if ( 2 != tree.degree() )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      boolean result = false;

      String op = tree.getElement();

      Value valueL = evaluateExp( tree.getSubTree(0), env );
      Value valueR = evaluateExp( tree.getSubTree(1), env );

      if ( op.equals("==") )
      {
         if ( ! valueL.tag.equals(valueR.tag) )
         {
            result = false;
         }
         else if ( valueL.tag.equals(Value.INT_TAG) )
         {
            int resultL = valueL.valueI;
            int resultR = valueR.valueI;
            result = resultL == resultR;
         }
         else // boolean data type
         {
            boolean resultL = valueL.valueB;
            boolean resultR = valueR.valueB;
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
            int resultL = valueL.valueI;
            int resultR = valueR.valueI;
            result = resultL != resultR;
         }
         else // boolean data type
         {
            boolean resultL = valueL.valueB;
            boolean resultR = valueR.valueB;
            result = resultL != resultR;
         }
      }

      return new Value( result );
   }//evaluateEqexp()


   // Evaluate a relational expression (which is a kind of boolean expression)
   private static Value evaluateRelexp(Tree tree, Environment env) throws EvalException
   {
      if ( 2 != tree.degree() )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      boolean result = false;

      String op = tree.getElement();

      Value valueL = evaluateExp( tree.getSubTree(0), env );
      if ( ! valueL.tag.equals(Value.INT_TAG) )  // runtime check
      {
         throw new EvalException("not a integer expression: "
                                  + tree.getSubTree(0));
      }

      Value valueR = evaluateExp( tree.getSubTree(1), env );
      if ( ! valueR.tag.equals(Value.INT_TAG) )  // runtime check
      {
         throw new EvalException("not a integer expression: "
                                  + tree.getSubTree(1));
      }

      int resultL = valueL.valueI;
      int resultR = valueR.valueI;

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
      else if ( op.equals(">=") )
      {
         result = resultL >= resultR;
      }
      return new Value( result );
   }//evaluateRelexp()


   // Evaluate an arithmetic expression
   private static Value evaluateAexp(Tree tree, Environment env) throws EvalException
   {
      int result = 0;

      String node = tree.getElement();

      Value valueL = evaluateExp( tree.getSubTree(0), env );
      if ( ! valueL.tag.equals(Value.INT_TAG) )  // runtime check
      {
         throw new EvalException("not a integer expression: "
                                  + tree.getSubTree(0));
      }
      int resultL = valueL.valueI;
      int resultR = 0;

      Value valueR = null;
      if ( tree.degree() >= 2 )
      {
         valueR = evaluateExp( tree.getSubTree(1), env );
         if ( ! valueR.tag.equals(Value.INT_TAG) )  // runtime check
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
               if ( ! temp.tag.equals(Value.INT_TAG) )  // runtime check
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
         if ( 2 < tree.degree() )  // runtime check
         {
            throw new EvalException("wrong number of arguments: " + tree);
         }
         if ( tree.degree() == 1 )
            result = -resultL;
         else
            result = resultL - resultR;
      }
      else if ( node.equals("*") )
      {
         if ( 1 == tree.degree() )  // runtime check
         {
            throw new EvalException("wrong number of arguments: " + tree);
         }

         result = resultL * resultR;

         for (int i = 2; i < tree.degree(); i++)
         {
            Value temp = evaluateExp( tree.getSubTree(i), env );
            if ( ! temp.tag.equals(Value.INT_TAG) )  // runtime check
            {
               throw new EvalException("not a integer expression: "
                                        + tree.getSubTree(i));
            }
            result *= temp.valueI;
         }
      }
      else if ( node.equals("/") )
      {
         if ( 2 != tree.degree() )  // runtime check
         {
            throw new EvalException("wrong number of arguments: " + tree);
         }
         result = resultL / resultR;
      }
      else if ( node.equals("%") )
      {
         if ( 2 != tree.degree() )  // runtime check
         {
            throw new EvalException("wrong number of arguments: " + tree);
         }
         result = resultL % resultR;
      }
      else if ( node.equals("^") )
      {
         if ( 2 != tree.degree() )  // runtime check
         {
            throw new EvalException("wrong number of arguments: " + tree);
         }
         result = (int)Math.pow(resultL, resultR);
      }

      return new Value( result );
   }//evaluateAexp()
}
