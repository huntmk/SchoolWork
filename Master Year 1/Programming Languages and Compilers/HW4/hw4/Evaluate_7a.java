/*
   Course: CS 51530
   Name: Marcellus Hunt
   Email: mkhunt@pnw.edu
   Assignment: 4
*/

import java.util.Random;

/**
   This program, as distributed, interprets Language_7.
   You are to modify this program so it interprets the
   following modified version of Language_7.

       Prog ::= Exp
              | '(' 'prog' (Fun | Exp)+ Exp ')'

        Fun ::= '(' 'fun' VARIABLE Lambda ')'   // a function declaration

     Lambda ::= '(' 'lambda' VARIABLE* Exp ')'  // formal parameters followed by function body

        Exp ::= Array
              | IndexExp
              | SizeOf
              | Rand
              | Repeat
              | For
              | IncDec
              | Spaceship
              | Apply
              | If
              | While
              | Begin
              | Set
              | Var
              | Print
              | AExp
              | BExp
              | INTEGER
              | BOOLEAN
              | VARIABLE

      Array ::= '(' 'array' 'dim' Exp ')'       // instantiate an array value of size Exp
              | '(' 'array'  Exp+ ')'           // instantiate an array value literal
              | '(' 'array' 'dim' Exp Exp* ')'  // partially initialized array

   IndexExp ::= '(' 'index' Exp Exp ')'     // 1st operand is array, 2nd operand is integer

     SizeOf ::= '(' sizeOf Exp ')'          // operand must be an array

       Rand ::= '(' 'rand' Exp Exp ')'      // return a random integer between Exp and Exp

     Repeat ::= '(' 'repeat' Exp Exp ')'

        For ::= '(' 'for' Exp Exp Exp Exp ')'

     IncDec ::= '(' '++'  VARIABLE ')'      // post-increment
              | '(' '--'  VARIABLE ')'      // post-decrement
              | '(' '+++' VARIABLE ')'      // pre-increment
              | '(' '---' VARIABLE ')'      // pre-decrement

  Spaceship ::= '(' '<=>' Exp Exp ')'

      Apply ::= '(' 'apply' Exp Exp* ')'    // function value followed by actual parameters

         If ::= '(' 'if' Exp Exp Exp ')'

      While ::= '(' 'while' Exp Exp ')'

      Begin ::= '(' 'begin' Exp+ ')'

        Set ::= '(' 'set' VARIABLE Exp ')'
              | '(' 'set' IndexExp Exp ')'  // set an array element

        Var ::= '(' 'var' VARIABLE Exp ')'

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

public class Evaluate_7a
{
   public static int DEBUG = 1;

   public static Environment globalEnv; // reference to the global environment object

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

      // Check whick kind of Prog we have.
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

      // Check that we have a proper name.
/*2*/ if ( ! name.matches("^[a-zA-Z][a-zA-Z0-9]*") )  // runtime check
      {
         throw new EvalException("improper function name: " + name);
      }

      // Check if this function name has already been defined.
/*3*/ if ( env.definedLocal(name) )  // runtime check
      {
         throw new EvalException("function already exists: " + name);
      }

      // Check if the definition really is a function.
/*4*/ if ( ! tree.getSubTree(1).getElement().equals("lambda"))
      {
         throw new EvalException("bad function definition: " + tree);
      }

      // Get the "lambda expression" and create a function Value.
/*5*/ final Value lambda = new Value( tree.getSubTree(1) );

      // Add the <name, value> pair to the global environment.
/*6*/ env.add(name, lambda);

      if (DEBUG > 0) System.out.println( env + "\n" ); // for debugging purposes
   }//handleFun()


   // Evaluate an expression
   public static Value evaluateExp(Tree tree, Environment env) throws EvalException
   {
      final Value result;  // a blank final

      final String node = tree.getElement();

      if ( node.equals("apply") )
      {
         result = evaluateApply( tree, env );
      }
      else if ( node.equals("if") )
      {
         result = evaluateIf( tree, env );
      }
      else if ( node.equals("while") )
      {
         result = evaluateWhile( tree, env );
      }
      else if ( node.equals("set") )
      {
         result = evaluateSet( tree, env );
      }
      else if ( node.equals("var") )
      {
         result = evaluateVar( tree, env );
      }
      else if ( node.equals("begin") )
      {
         result = evaluateBegin( tree, env );
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
      else if (node.equals("for"))
      {
         result = For(tree, env);
      }
      else if (node.equals("repeat"))
      {
         result = Repeat(tree, env);
      }
      else if (node.equals("array"))
      {
         result = evalArray(tree,env);
      }
      
      else if (node.equals("index"))
      {
         result = evalIndex(tree,env);
      }
      
      else if (node.equals("sizeOf"))
      {
         result = evalSize(tree,env);
      }
      else if (node.equals("rand"))
      {
         result = evalRand(tree,env);
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
/*2*/ if ( ! funValue.tag.equals(Value.LAMBDA_TAG))  // runtime check
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
/*4*/ if ( tree.degree() != lambda.degree() )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      // Create a new environment object that is "nested"
      // in the global environment ("lexical scope") (or, is
      // "nested" in the current environment ("dynamic scope")).
      // This environment is used to bind actual parameter
      // values to formal paramter names.
      // (Note: Change globalEnv to env for dynamic scope.)
/*5*/ final Environment localEnv = new Environment(globalEnv, "Function Activation");

      // Bind, in the new environment object, the actual parameter
      // values to the formal parameter names.
/*6*/ for (int i = 1; i < tree.degree(); ++i) // iterate through the actual parameters
      {
         // Evaluate, using the current environment chain
         // (NOT the new local environment object)
         // an actual parameter expression's value.
/*7*/    final Value actualParamValue = evaluateExp( tree.getSubTree(i), env );

         // Retrieve, from within the lambda expression,
         // a formal parameter name.
/*8*/    final String formalParamName = lambda.getSubTree(i-1).getElement();

         // Check that we have a proper parameter name.
/*9*/    if ( ! formalParamName.matches("^[a-zA-Z][a-zA-Z0-9]*") )  // runtime check
         {
            throw new EvalException("improper parameter name: " + formalParamName);
         }

         // Bind, in the new local environment object, the actual
         // paramter value to a formal parameter name.
/*10*/   localEnv.add(formalParamName, actualParamValue);
      }

      if (DEBUG > 0) System.out.println( localEnv + "\n" ); // for debugging purposes

      // Evaluate the body of the lambda expression using the
      // new environment (which contains the binding of the actual
      // parameter values to the function's formal parameter names).
/*11*/final Value result = evaluateExp(lambda.getSubTree(tree.degree()-1), localEnv);

      // When this method returns, the local Environment
      // object that we created to hold the parameter bindings
      // becomes a garbage object (and will be garbage colected).
      // This is like "popping" a stack frame off of the call stack
      // in Java, C , or C++.

      return result;
   }//evaluateApply()

   
   private static Value evalIndex(Tree tree, Environment env) throws EvalException
   {

      //eval exception
      if(tree.degree() != 2)
      {
         throw new EvalException("Expression must have only 2 subtrees");
      }

      //access 1st subtree which is array
      Value arr = evaluateExp(tree.getSubTree(0), env);

      //check if subtree is an array
      if(!arr.tag.equals(Value.ARRAY_TAG))
      {
         throw new EvalException("Subtree must be an array");
      }

      //access 2nd subtree which is index
      int index = evaluateExp(tree.getSubTree(1), env).valueI;

      //return value
      return arr.valueA[index];
      
   }

   
   private static Value evalSize(Tree tree, Environment env) throws EvalException
   {

      //eval exception argument check
      if(tree.degree() != 1)
      {
         throw new EvalException("Expression must have only 2 subtrees");
      }

      //get array value
      Value arr = evaluateExp(tree.getSubTree(0), env);

      //check if subtree is an array
      if(!arr.tag.equals(Value.ARRAY_TAG))
      {
         throw new EvalException("Subtree must be an array");
      }

      //return length of array
      return new Value (arr.valueA.length);

   }

   
   private static Value evalRand(Tree tree, Environment env) throws EvalException
   {

      //eval exception argument check
      if(tree.degree() != 2)
      {
         throw new EvalException("Expression must have only 2 subtrees");
      }

      //get both int expressions
      Value valueL = evaluateExp(tree.getSubTree(0), env);
      Value valueR = evaluateExp((tree.getSubTree(1)), env);

      //eval exception type check
      if(!valueL.tag.equals(Value.INT_TAG) && valueR.tag.equals(Value.INT_TAG))
      {
         throw new EvalException("Expressions must be integers");
      }

      //get left and right values
      int intL = valueL.valueI;
      int intR = valueR.valueI;

      //check if left value is less than or equal to right value
      if(intL > intR)
      {
         throw new EvalException("Left value must be less than or equal to right value.");
      }

      //create random object
      Random rand = new Random();

      //return integer from next int method
      return new Value(rand.nextInt(intR - intL + 1));

   }
   

   private static Value evalArray(Tree tree, Environment env) throws EvalException
   {
      //this is the 'dim' keyword
      final String node = tree.getSubTree(0).getElement();
      
      //create the array and initialize to null until it's used 
      Value [] arr = {};

      //if first operand is dim
      if (node.equals("dim"))
      {
         //only 1 or two expression operands should be present
         if(tree.degree() < 2)
         {
            throw new EvalException("wrong number of arguments" + tree);
         }

         //this should declare an array of desired size
         if(tree.degree() == 2)
         {

            //get 2nd subtree with desired size
            Value size = evaluateExp(tree.getSubTree(1), env);

            //value check
            if ( !size.tag.equals(Value.INT_TAG) )
            {
               throw new EvalException("illegal boolean expression: " + tree);
            }

            //get int value
            int Isize = size.valueI;

            //declare size of array
            arr = new Value [Isize];

         }
         else
         {

            // handle declaration of size and partial initialization
            //create array ojbect

            //retrieve desired size
            int obj = evaluateExp(tree.getSubTree(1), env).valueI;

            //check if array size is positive
            if(obj < 0)
            {
               throw new EvalException("Size must be positive integer");
            }

            //give array size
            arr = new Value [obj];
            
            
            //partially initialized
            if (tree.degree() > 3)
            {
               //used for assigning to array object
               int index = 0;

               //loop through subtrees(starting at 2)
               for(int i = 2; i < tree.degree(); i++)
               {
                  if(index >= arr.length)
                  {
                     throw new EvalException("The number of argument values must be less than or equal to the value of the dimension expression");
                  }
                  
                  //set value of array
                  arr[index] = evaluateExp(tree.getSubTree(i), env);
                  index++;
               }
            } 
         }
      }
      else
      {
         //set value of array
         arr = new Value [tree.degree()];
         
         //loop through tree and add expressions to tree.
         for(int i = 0; i < tree.degree(); i++)
         {
            arr[i] = evaluateExp(tree.getSubTree(i), env);
         }
      }

      return new Value(arr);

   }

   private static Value For(Tree tree, Environment env) throws EvalException
   {

      //runtime check
      //check if it has 3 children nodes before it; else throw error
      if(tree.degree()!=4 )
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      // Create a new Environment object chained to (or "nested in")
      // the previous ("outer") environment object.
      final Environment newEnv = new Environment(env,"newEnv");

      //evaluate first expression once, which is intitalization
      evaluateExp( tree.getSubTree(0), newEnv );

      //evaluate second expression which is the bool condition
      Value bool = evaluateExp(tree.getSubTree(1), newEnv);

      // do a runtime type check
      if ( ! bool.tag.equals(Value.BOOL_TAG) )
      {
         throw new EvalException("illegal boolean expression: " + tree);
      }
      
      //while bool is true,
      while(bool.valueB)
      {
         
         //evaluate fourth expression (body)
         evaluateExp(tree.getSubTree(3), newEnv);

         //next evaluate third expression (update)
         evaluateExp(tree.getSubTree(2), newEnv);

         //re-evaluate bool condition
         bool = evaluateExp(tree.getSubTree(1), newEnv);

         //runtime check
         if ( ! bool.tag.equals(Value.BOOL_TAG) )
         {
         throw new EvalException("illegal boolean expression: " + tree);
         }

      }

      return new Value(false);
   }
   //repeat statement
   private static Value Repeat(Tree tree, Environment env) throws EvalException
   {
      if ( 2 != tree.degree() )  // runtime check
      {
         throw new EvalException("incorrect repeat expression: " + tree);
      }

      //evaluate body
      // evaluate the body of the loop (for its side effects)
      Value body = evaluateExp( tree.getSubTree(0), env );

      //evaluate condition
      Value condition = evaluateExp(tree.getSubTree(0),env);

      //run time check
      if ( ! condition.tag.equals(Value.BOOL_TAG) )
      {
         throw new EvalException("illegal boolean expression: " + tree);
      }

      while(condition.valueB)
      {
         //evaluate body again
         body = evaluateExp(tree.getSubTree(0), env);

         //re-evaluate condition
         condition = evaluateExp(tree.getSubTree(1), env);

         //runtime check
         if ( ! condition.tag.equals(Value.BOOL_TAG) )
         {
         throw new EvalException("illegal boolean expression: " + tree);
         }
 
      }
      
      return body;

   }

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
   // Evaluate an if-expression
   private static Value evaluateIf(Tree tree, Environment env) throws EvalException
   {
      if ( 3 != tree.degree() )  // runtime check
      {
         throw new EvalException("incorrect conditional expression: " + tree);
      }

      final Value result;  // a blank final

      final Value conditionalExp = evaluateExp( tree.getSubTree(0), env );
      // do a runtime type check
      if ( ! conditionalExp.tag.equals(Value.BOOL_TAG) )
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
      if ( 2 != tree.degree() )  // runtime check
      {
         throw new EvalException("incorrect while expression: " + tree);
      }

      // evaluate the boolean condition
      Value conditionalExp = evaluateExp( tree.getSubTree(0), env );
      // do a runtime type check
      if ( ! conditionalExp.tag.equals(Value.BOOL_TAG) )
      {
         throw new EvalException("illegal boolean expression: " + tree);
      }

      while ( conditionalExp.valueB )
      {
         // evaluate the body of the loop (for its side effects)
         evaluateExp( tree.getSubTree(1), env );
         // re-evaluate the boolean condition
         conditionalExp = evaluateExp( tree.getSubTree(0), env );
         // do a runtime type check
         if ( ! conditionalExp.tag.equals(Value.BOOL_TAG) )
         {
            throw new EvalException("illegal boolean expression: " + tree);
         }
      }

      // always return false for a while-loop expression
      return new Value( false );
   }//evaluateWhile()


   // Evaluate a set expression
   private static Value evaluateSet(Tree tree, Environment env) throws EvalException
   {
      
      if ( 2 != tree.degree() )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree + "\n");
      }

      //get left and right tree
      Tree ltree = tree.getSubTree(0);
      Tree rtree = tree.getSubTree(1);

      //check if left tree equals index
      if(ltree.getElement().equals("index"))
      {
         //get variable
         final String var = ltree.getElement();

         //need to get array from index subtree (left subtree)
         Value [] arr = evaluateExp(ltree.getSubTree(0), env).valueA;

         //get the desired index
         int ind = evaluateExp(ltree.getSubTree(1), env).valueI;

         //get and evaluate expression
         final Value result = evaluateExp(rtree,env);

         //set value of array index
         arr[ind] = result;

         //updated variable in the environment
         env.update(var, result);

         return result;
         
      }

      //check if right tree equals index
      else if(rtree.getElement().equals("index"))
      {
         final String var = ltree.getElement();

         //get and evaluate expression
         final Value result = evalIndex(rtree, env);

         //updated variable in the enviornment
         env.update(var, result);

         //return result
         return result;
      }

      //check if both trees equals index
      else if(ltree.getElement().equals("index") && rtree.getElement().equals("index"))
      {
         //get variable
         final String var = ltree.getElement();

         //need to get array from index subtree (left subtree)
         Value [] arr = evaluateExp(ltree.getSubTree(0), env).valueA;

         //get the desired index
         int ind = evaluateExp(ltree.getSubTree(1), env).valueI;

         //get and evaluate expression
         final Value result = evalIndex(rtree,env);

         //set value of array index
         arr[ind] = result;

         //updated variable in the environment
         env.update(var, result);

         //return result
         return result;
      }

      // get the variable
      final String variable = tree.getSubTree(0).getElement();

      // check that we have a proper variable
      if ( ! variable.matches("^[a-zA-Z][a-zA-Z0-9]*") )  // runtime check
      {
         throw new EvalException("improper variable name: " + variable);
      }

      // check if this variable has already been declared
      if ( ! env.defined(variable) )
      {
         throw new EvalException("undefined variable: " + variable);
      }

      // get, and then evaluate, the expression
      final Tree expr = tree.getSubTree(1);
      final Value result = evaluateExp( expr, env );
      // update this variable in the environment
      env.update(variable, result);

      if (DEBUG > 0) System.out.println( env + "\n" ); // for debugging purposes

      return result;
   }//evaluateSet()


   // Evaluate a var expression
   private static Value evaluateVar(Tree tree, Environment env) throws EvalException
   {
      if ( 2 != tree.degree() )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree + "\n");
      }

      // get the variable
      final String variable = tree.getSubTree(0).getElement();

      // check that we have a proper variable
      if ( ! variable.matches("^[a-zA-Z][a-zA-Z0-9]*") )  // runtime check
      {
         throw new EvalException("improper variable name: " + variable);
      }

      // check if this variable has already been declared
      // in the local environment
      if ( env.definedLocal(variable) )
      {
         throw new EvalException("variable already declared: " + variable + "\n");
      }

      // get, and then evaluate, the expression
      final Tree expr = tree.getSubTree(1);
      final Value result = evaluateExp( expr, env );

      // declare the new, local, variable
      env.add(variable, result);

      if (DEBUG > 0) System.out.println( env + "\n" ); // for debugging purposes

      return result;
   }//evaluateVar()


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
      // becomes a garbage object (and will be garbage colected).
      // This is like "popping" a stack frame off of the call stack
      // in Java, C , or C++.

      return result;
   }//evaluateBegin()


   // Evaluate a print expression
   private static Value evaluatePrint(Tree tree, Environment env) throws EvalException
   {
      if ( 1 != tree.degree() )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree + "\n");
      }

      final Value result = evaluateExp( tree.getSubTree(0), env );

      System.out.println( result );

      return result;
   }//evaluatePrint()


   // Evaluate a boolean expression
   private static Value evaluateBexp(Tree tree, Environment env) throws EvalException
   {
      boolean result = false;

      final String node = tree.getElement();

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
      if ( 2 != tree.degree() )  // runtime check
      {
         throw new EvalException("wrong number of arguments: " + tree);
      }

      final boolean result;  // a blank final

      final String op = tree.getElement();

      final Value valueL = evaluateExp( tree.getSubTree(0), env );
      if ( ! valueL.tag.equals(Value.INT_TAG) )  // runtime check
      {
         throw new EvalException("not a integer expression: "
                                  + tree.getSubTree(0));
      }

      final Value valueR = evaluateExp( tree.getSubTree(1), env );
      if ( ! valueR.tag.equals(Value.INT_TAG) )  // runtime check
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
      if ( ! valueL.tag.equals(Value.INT_TAG) )  // runtime check
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
