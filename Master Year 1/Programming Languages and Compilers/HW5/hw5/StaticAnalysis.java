/*

Course: CS 51530
Name: Marcellus Hunt
Email: mkhunt@pnw.edu
Assignment: 5

*/

/**
   This program analyzes an Abstract Syntax Tree (AST)
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

      Begin ::= '(' 'begin' Exp* Exp ')'

        Var ::= '(' 'var' VARIABLE Exp ')'

        Set ::= '(' 'set' VARIABLE Exp ')'

      Print ::= '(' 'print' Exp ')'

       BExp ::= '(' '||'  Exp Exp+ ')'
              | '(' '&&'  Exp Exp+ ')'
              | '(' '!'   Exp ')'
              | '(' RelOp Exp Exp ')'
              | '('  EqOp Exp Exp ')'

      RelOp ::= '<' | '>' | '<=' | '>='
       EqOp ::= '==' | '!='

       AExp ::= '(' '+' Exp Exp* ')'
              | '(' '-' Exp Exp? ')'
              | '(' '*' Exp Exp+ ')'
              | '(' '/' Exp Exp  ')'
              | '(' '%' Exp Exp  ')'
              | '(' '^' Exp Exp  ')'

    INTEGER ::= [-][0-9]+
    BOOLEAN ::= 'true' | 'false'
   VARIABLE ::= [a-zA-Z][a-zA-Z0-9]*
*/

public class StaticAnalysis
{
   public static int DEBUG = 1;

   public static Environment globalEnv; // reference to the global environment object
                                        // this is needed to implement lexical scope
   /**



   */
   public static boolean analyze(Tree tree)
   {
      // Instantiate a global environment object.
      globalEnv = new Environment();

      return analyzeProg( tree, globalEnv );
   }//analyze()


   // Analyze a prog.
   private static boolean analyzeProg(Tree tree, Environment env)
   {
      boolean result = true;

      // Check which kind of Prog we have.
      if ( ! tree.getElement().equals("prog") )
      {
         // Analyze the single expression.
         result = analyzeExp(tree, env);
      }
      else
      {
         boolean rv = true;
         // Analyze each Fun and gather all the function
         // names in the global environment. Do this before
         // analyzing each function's lambda expression so
         // that we can allow function forward references
         // (and mutually recursive functions).
         for (int i = 0; i < tree.degree(); ++i)
         {
            if ( tree.getSubTree(i).getElement().equals("fun") )
            {
               rv = analyzeFun(tree.getSubTree(i), env);
            }
            result = result && rv;
         }
         // Analyze each Fun or Exp in the Prog. In this pass,
         // we analyze the body (lambda expression) of each
         // function definition.
         for (int i = 0; i < tree.degree(); ++i)
         {
            if ( tree.getSubTree(i).getElement().equals("fun") )
            {
               if ( env.defined( tree.getSubTree(i).getSubTree(0).getElement() )
                 && env.lookUp( tree.getSubTree(i).getSubTree(0).getElement() ).tag.equals("lambda") )
               {
                  rv = analyzeLambda(tree.getSubTree(i).getSubTree(1), env);
               }
            }
            else
            {
               rv = analyzeExp(tree.getSubTree(i), env);
            }
            result = result && rv;
         }
      }

      return result;
   }//analyzeProg()


   // Analyze the first part of a function definition
   // (without analyzing the lambda part).
   // This is needed by the REPL, so we make it public.
   public static boolean analyzeFun(Tree tree, Environment env)
   {
      boolean result = true;

      //check if tree degree is greater than 1
      if (tree.degree() <= 1)
      {
         System.err.println("**** Analysis Error: wrong number of arguments: "
         + tree + ":"
         + " at line " + tree.getToken().line
         + ", position " + tree.getToken().position
         + "\n" );

         result = false;
      }

      // Get the function name.
      final String name = tree.getSubTree(0).getElement();
      Tree left = tree.getSubTree(0);

      // Check that we have a proper name.
      if ( ! name.matches("^[a-zA-Z][a-zA-Z0-9]*") )  // runtime check
      {
         System.err.println("**** Analysis Error: improper function name: "
         + name + ":"
         + " at line " + left.getToken().line
         + ", position " + left.getToken().position
         + "\n" );

         return false;
      }

      // Check if this function name has already been defined.
      /*3*/ if ( env.definedLocal(name) )  // runtime check
      {
         System.err.println("**** Analysis Error: function already exists: "
         + left + ":"
         + " at line " + left.getToken().line
         + ", position " + left.getToken().position
         + "\n" );

         result = false;
      }


      //if tree.degree() is greater than 2, ****SYNTAX *****
      if(tree.degree() > 2)
      {
         for(int i = 2; i < tree.degree(); i++)
         {
            System.err.println("**** Analysis Error: illegal expression: "
            + tree.getSubTree(i) + ":"
            + " at line " + tree.getSubTree(i).getToken().line
            + ", position " + tree.getSubTree(i).getToken().position
            + "\n" );

            result = false;
         }
      }

      //if tree degree is greater than or equal to 1
      if (tree.degree() == 2)
      {
         //check for keyword
         if (tree.getSubTree(0).getElement().equals("true") || tree.getSubTree(0).getElement().equals("false") )
         {
            System.err.println("**** Analysis Error: keyword not allowed: "
            + tree.getSubTree(0).getElement() + ":"
            + " at line " + tree.getSubTree(0).getToken().line
            + ", position " + tree.getSubTree(0).getToken().position
            + "\n" );

            return false;
         }

         //check if equals lambda
         if ( ! tree.getSubTree(1).getElement().equals("lambda"))
         {
            System.err.println("**** Analysis Error: bad function definition: "
            + tree.getSubTree(1).getElement() + ":"
            + " at line " + tree.getSubTree(1).getToken().line
            + ", position " + tree.getSubTree(1).getToken().position
            + "\n" );

            result = false;
         }

         //check degree of lambda function
         if(tree.getSubTree(1).degree() == 0)
         {
            System.err.println("**** Analysis Error: improper function definition: "
            + tree.getSubTree(1).getElement() + ":"
            + " at line " + tree.getSubTree(1).getToken().line
            + ", position " + tree.getSubTree(1).getToken().position
            + "\n" );

            result = false;
         }

         // Get the "lambda expression" and create a function Value.
         final Value lambda = new Value( tree.getSubTree(1) );

         // Add the <name, value> pair to the global environment.
         env.add(name, lambda);

         //analyze lambda
         analyzeLambda(tree.getSubTree(1),env);

      }

      if (DEBUG > 0) System.out.println( env + "\n" ); // for debugging purposes

      return result;
   }//analyzeFun()


   // Analyze a lambda expression.
   // This is needed by the REPL, so we make it public.
   public static boolean analyzeLambda(Tree tree, Environment env)
   {
      boolean result = true;    

      //create new environment
      final Environment newEnv = new Environment(env, "Local (lambda)");

      //if there is at least 1 subtree
      if(tree.degree() >= 1)
      {
         for (int i = 0; i < tree.degree()-1; i++)
         {
            if(tree.getSubTree(i).getElement().equals("if"))
            {
               System.err.println("**** Analysis Error: keyword not allowed "
               + tree.getSubTree(i) + ":"
               + " at line " + tree.getSubTree(i).getToken().line
               + ", position " + tree.getSubTree(i).getToken().position
               + "\n" );

               return false;
            }

            if(tree.getSubTree(i).getElement().matches("^[-]*[0-9][0-9]*"))
            {
               System.err.println("**** Analysis Error: improper parameter name: "
               + tree.getSubTree(i) + ":"
               + " at line " + tree.getSubTree(i).getToken().line
               + ", position " + tree.getSubTree(i).getToken().position
               + "\n" );

               return false;
            }
         }

      }

      //for every subtree that's a variable, analyze it
      for (int i = 0; i < tree.degree()-1; i++)
      {
         //then this is a variable or number
         if(tree.getSubTree(i).degree() == 0)
         {
            //if it's not a number (variable)
            if(! tree.getSubTree(i).getElement().matches("^[-]*[0-9][0-9]*"))
            {
               //if it hasn't been defined locally
               if(! newEnv.definedLocal(tree.getSubTree(i).getElement()))
               {
                  //add to new environment
                  Value val = new Value (false);
                  newEnv.add(tree.getSubTree(i).getElement(),val);

               }
               
               //it is a repeated parameter
               else
               {
                  System.err.println("**** Analysis Error: repeated formal parameter: "
                  + tree.getSubTree(i) + ":"
                  + " at line " + tree.getSubTree(i).getToken().line
                  + ", position " + tree.getSubTree(i).getToken().position
                  + "\n" );
                  result = false;
               }

            }        
   
            
         }
      }

      //analyze the body
      //check if tree degree is not 0
      if(tree.degree() != 0)
      {
         Tree body = tree.getSubTree(tree.degree()-1);
         
         if(! analyzeExp(body, newEnv))
         {
            result = false;
         }
         
      }      

      return result;
   }//analyzeLambda()


   // Analyze an expression.
   public static boolean analyzeExp(Tree tree, Environment env)
   {
      boolean result = true;

      String node = tree.getElement();

      if ( node.equals("apply") )
      {
         result = analyzeApply(tree, env);
      }
      else if ( node.equals("if") )
      {
         result = analyzeIf(tree, env);
      }
      else if ( node.equals("while") )
      {
         result = analyzeWhile(tree, env);
      }
      else if ( node.equals("begin") )
      {
         result = analyzeBegin(tree, env);
      }
      else if ( node.equals("var") )
      {
         result = analyzeVar(tree, env);
      }
      else if ( node.equals("set") )
      {
         result = analyzeSet(tree, env);
      }
      else if ( node.equals("print") )
      {
         result = analyzePrint(tree, env);
      }
      else if ( node.equals("&&")
             || node.equals("||")
             || node.equals("!") )
      {
         result = analyzeBExp(tree, env);  // boolean expression
      }
      else if ( node.equals("<")  || node.equals(">")
             || node.equals("<=") || node.equals(">=")
             || node.equals("==") || node.equals("!=") )
      {
         result = analyzeRExp(tree, env);  // relational operator
      }
      else if ( node.equals("+") || node.equals("-")
             || node.equals("*") || node.equals("/")
             || node.equals("%") || node.equals("^") )
      {
         result = analyzeAExp(tree, env);  // arithmetic expression
      }
      else if ( tree.degree() == 0 )
      {
         if ( node.equals("true") || node.equals("false") )
         {
            result = true;
         }
         else if ( node.matches("^[-]*[0-9][0-9]*") )
         {
            result = true;
         }
         else if ( env.defined(node) )  // a variable
         {
            result = true;
         }
         else
         {
            result = false;
            System.err.println("**** Analysis Error: undefined variable: "
                               + node
                               + " at line " + tree.getToken().line
                               + ", position " + tree.getToken().position
                               + "\n" );
         }
      }
      else
      {
         result = false;
         System.err.println("**** Analysis Error: illegal expression: "
                            + node + ":"
                            + " at line " + tree.getToken().line
                            + ", position " + tree.getToken().position
                            + "\n" );
      }

      return result;
   }//analyzeExp()


   // Analyze a function application.
   private static boolean analyzeApply(Tree tree, Environment env)
   {
      boolean result = true;

      
      Tree funct = tree.getSubTree(0);
      String strFunct = funct.getElement();

      //if function doesn't exist output error
      if (! env.defined(strFunct))
      {
         result = false;
         System.err.println("**** Analysis Error: undefined function: "
                              + strFunct
                              + " at line " + funct.getToken().line
                              + ", position " + funct.getToken().position
                              + "\n" );
      }
      
      if(env.definedGlobal(strFunct))
      {
         //get lambda expression
         final Value funValue = env.lookUp(tree.getSubTree(0).getElement());
         Tree lambda = funValue.valueL;
         
         //if tree degree is greater than lambda degree
         if( funValue.tag.equals(Value.LAMBDA_TAG) && tree.degree() > lambda.degree() )
         {
            result = false;
            System.err.println("**** Analysis Error: too many actual parameters: "
                                 + strFunct
                                 + " at line " + funct.getToken().line
                                 + ", position " + funct.getToken().position
                                 + "\n" );
         }

         if( funValue.tag.equals(Value.LAMBDA_TAG) && tree.degree() < lambda.degree())
         {
            result = false;
            System.err.println("**** Analysis Error: too few actual parameters: "
                                 + strFunct
                                 + " at line " + funct.getToken().line
                                 + ", position " + funct.getToken().position
                                 + "\n" );
         }
      }


      //check if variables are defined
      for (int i = 1; i < tree.degree(); i++)
      {
         //if it's a variable
         if(! tree.getSubTree(i).getElement().matches("^[-]*[0-9][0-9]*"))
         {
            //if it's not defined
            if(! env.defined(tree.getSubTree(i).getElement()))
            {
               result = false;
               System.err.println("**** Analysis Error: undefined variable: "
                                    + tree.getSubTree(i).getElement()
                                    + " at line " + tree.getSubTree(i).getToken().line
                                    + ", position " + tree.getSubTree(i).getToken().position
                                    + "\n" );
            }
         }
      }

      return result;
   }//analyzeApply()


   // Analyze an if expression.
   private static boolean analyzeIf(Tree tree, Environment env)
   {
      boolean result = true;

      //if degree doesn't equal 3
      if ( 3 != tree.degree() )  // runtime check
      {
         result = false;
         System.err.println("**** Analysis Error: incorrect conditional expression: "
                            + tree + ":"
                            + " at line " + tree.getToken().line
                            + ", position " + tree.getToken().position
                            + "\n" );
      }

      return result;
   }//analyzeIf()


   // Analyze a while expression.
   private static boolean analyzeWhile(Tree tree, Environment env)
   {
      boolean result = true;

      if ( 2 != tree.degree() )  // runtime check
      {
         result = false;
         System.err.println("**** Analysis Error: incorrect while expression: "
                            + tree + ":"
                            + " at line " + tree.getToken().line
                            + ", position " + tree.getToken().position
                            + "\n" );
      }

      if (tree.degree() >= 1)
      {
         //get first node
         Tree first_node = tree.getSubTree(0);
         String strFirst = first_node.getElement();

         //if it's a keyword
         if ( !strFirst.equals("true") || strFirst.equals("false") || strFirst.equals("if"))  // runtime check
         {
            System.err.println("**** Analysis Error: improper function name: "
            + first_node + ":"
            + " at line " + first_node.getToken().line
            + ", position " + first_node.getToken().position
            + "\n" );
   
            result = false;
            //return result;
         }
         else{
            analyzeExp(first_node, env);
         }
      }

      if (tree.degree() > 1)
      {
         //get second node
         Tree second_node = tree.getSubTree(1);

         result = analyzeExp(second_node, env);

      }

    
      return result;
   }//analyzeWhile()


   // Analyze a begin (block) expression.
   private static boolean analyzeBegin(Tree tree, Environment env)
   {
      boolean result = true;

      //create new enviornment, analyze expressions using new enviornment
      final Environment newEnv = new Environment(env, "Local (begin)");

      for (int i= 0; i < tree.degree(); i++)
      {
         result = analyzeExp( tree.getSubTree(i), newEnv );
      }
     
      
      return result;
   }//analyzeBegin()


   // Analyze a variable declaration expression.
   private static boolean analyzeVar(Tree tree, Environment env)
   {
      boolean result = true;

      //if not 2 subtrees
      if ( 2 != tree.degree() )  // runtime check
      {
         System.err.println("**** Analysis Error: wrong number of arguments: "
         + tree + ":"
         + " at line " + tree.getToken().line
         + ", position " + tree.getToken().position
         + "\n" );

         result = false;
      }


      if(tree.degree() == 2)
      {      
         // get the variable
         Tree lNode = tree.getSubTree(0);
         final String variable = tree.getSubTree(0).getElement();

         // check that we have a proper variable
         if ( ! variable.matches("^[a-zA-Z][a-zA-Z0-9]*") )  // runtime check
         {
            System.err.println("**** Analysis Error: improper variable name: "
            + tree + ":"
            + " at line " + tree.getToken().line
            + ", position " + tree.getToken().position
            + "\n" );

            result = false;
         }

         // check if this variable has already been declared
         // in the local environment
         if ( env.definedLocal(variable) )
         {
            System.err.println("**** Analysis Error: variable already declared: "
            + tree.getSubTree(0) + ":"
            + " at line " + tree.getSubTree(0).getToken().line
            + ", position " + tree.getSubTree(0).getToken().position
            + "\n" );

            result = false;
         }

         //if equals variable
         if(variable.equals("var"))
         {
            System.err.println("**** Analysis Error: keyword not allowed: "
            + tree.getSubTree(0) + ":"
            + " at line " + tree.getSubTree(0).getToken().line
            + ", position " + tree.getSubTree(0).getToken().position
            + "\n" );

            result = false;
         }

         // get, and then evaluate, the expression
         final Tree expr = tree.getSubTree(1);
         final Value val = new Value(false);

         //if false, then return false
         
         if(!analyzeExp(expr, env))
         {
            return false;
         }
         
         // declare the new, local, variable
         env.add(variable, val);               
      }
      if (DEBUG > 0) System.out.println( env + "\n" ); // for debugging purposes

      return result;
   }//analyzeVar()


   // Analyze a variable assignment expression.
   private static boolean analyzeSet(Tree tree, Environment env)
   {
      boolean result = true;

      //****** SYNTAX  *****/

      //if tree degree is not 2
      if ( 2 != tree.degree() )  // runtime check
      {
         System.err.println("**** Analysis Error: wrong number of arguments: "
         + tree + ":"
         + " at line " + tree.getToken().line
         + ", position " + tree.getToken().position
         + "\n" );

         result = false;
      }


      //if left node exists
      if(tree.degree() >= 1)
      {
         Tree lNode = tree.getSubTree(0);
         String strLNode = lNode.getElement();

         //if string is number
         if ( strLNode.matches("^[-]*[0-9][0-9]*") )
         {
            System.err.println("**** Analysis Error: improper variable name: "
            + strLNode + ":"
            + " at line " + lNode.getToken().line
            + ", position " + lNode.getToken().position
            + "\n" );

            result = false;
         }

         //check if first value is not 'var'
         else if(strLNode.equals("set"))
         {
            System.err.println("**** Analysis Error: keyword not allowed: "
            + strLNode + ":"
            + " at line " + lNode.getToken().line
            + ", position " + lNode.getToken().position
            + "\n" );

            result = false;
         }

         //if first varialbe is not defined
         else if(!env.definedLocal(strLNode))
         {
            System.err.println("**** Analysis Error: undefined variable: "
            + strLNode + ":"
            + " at line " + lNode.getToken().line
            + ", position " + lNode.getToken().position
            + "\n" );

            result = false;
         }
      }

      //if tree degree equals two
      if(tree.degree() >=2)
      {
         //right node
         Tree rNode = tree.getSubTree(1);
         String strRNode = tree.getSubTree(1).getElement();

         //if right node is a variable
         if (! strRNode.matches("^[-]*[0-9][0-9]*") )
         {
            if(!env.defined(strRNode))
            {
               System.err.println("**** Analysis Error: undefined variable: "
               + strRNode + ":"
               + " at line " + rNode.getToken().line
               + ", position " + rNode.getToken().position
               + "\n" );

               result = false;
            }

         }
      }

      return result;
   }//analyzeSet()


   // Analyze a print expression.
   private static boolean analyzePrint(Tree tree, Environment env)
   {
      boolean result = true;

      //allows only 1 argument
      if ( 1 != tree.degree() )  // runtime check
      {
         result = false;
         System.err.println("**** Analysis Error: wrong number of arguments: "
                            + tree + ":"
                            + " at line " + tree.getToken().line
                            + ", position " + tree.getToken().position
                            + "\n" );
      }

      //if degree >= 1

      for (int i = 0; i < tree.degree(); i++)
      {
         if (tree.degree() >= 1)
         {
            Tree lNode = tree.getSubTree(i);
            String strLNode = lNode.getElement();

            if(!env.defined(strLNode))
            {
               result = false;
               System.err.println("**** Analysis Error: undefined variable: "
                              + lNode + ":"
                              + " at line " + lNode.getToken().line
                              + ", position " + lNode.getToken().position
                              + "\n" );

            }
         }
      }
      
      //check if node is defined



      return result;
   }//analyzePrint()


   // Analyze a boolean expression.
   private static boolean analyzeBExp(Tree tree, Environment env)
   {
      boolean result = true;

      //allows only 2 arguments
      if ( 2 != tree.degree() )  // runtime check
      {
         result = false;
         System.err.println("**** Analysis Error: wrong number of arguments: "
                            + tree + ":"
                            + " at line " + tree.getToken().line
                            + ", position " + tree.getToken().position
                            + "\n" );
      }

      if (tree.getElement().equals("!"))
      {
         if (1 != tree.degree())
         {
            result = false;
            System.err.println("**** Analysis Error: wrong number of arguments: "
                            + tree + ":"
                            + " at line " + tree.getToken().line
                            + ", position " + tree.getToken().position
                            + "\n" );
         }
      }


      return result;
   }//analyzeBExp()


   // Analyze a relational expression.
   private static boolean analyzeRExp(Tree tree, Environment env)
   {
      boolean result = true;

      //allows only 2 arguments
      if ( 2 != tree.degree() )  // runtime check
      {
         result = false;
         System.err.println("**** Analysis Error: wrong number of arguments: "
                            + tree + ":"
                            + " at line " + tree.getToken().line
                            + ", position " + tree.getToken().position
                            + "\n" );
      }

      return result;
   }//analyzeRExp()


   // Analyze an arithmetic expression.
   private static boolean analyzeAExp(Tree tree, Environment env)
   {
      boolean result = true;

      //get arithmetic 
      String node = tree.getElement();

      //checks arguments for all operators

      if (node.equals("+") || node.equals("*") )
      {
          //allows 2 or more arguments
         if ( 1 == tree.degree() )  // runtime check
         {
            result = false;
            System.err.println("**** Analysis Error: wrong number of arguments: "
                              + node + ":"
                              + " at line " + tree.getToken().line
                              + ", position " + tree.getToken().position
                              + "\n" );
         }

        
         //loop through subtrees and analyze
         for (int i = 0; i < tree.degree(); i++)
         {
            if(! analyzeExp(tree.getSubTree(i), env))
            {
               result = false;
            }
         }
         
      }

      else if(node.equals("-"))
      {
         if ( 2 < tree.degree() )  // runtime check
         {
            result = false;
            System.err.println("**** Analysis Error: wrong number of arguments: "
                              + node + ":"
                              + " at line " + tree.getToken().line
                              + ", position " + tree.getToken().position
                              + "\n" );
         }

         //loop through subtrees and analyze
         for (int i = 0; i < tree.degree(); i++)
         {
            analyzeExp(tree.getSubTree(i), env);
         }
         
      }

      else if(node.equals("/") || node.equals("%") || node.equals("^") )
      {
         if ( 2 != tree.degree() )  // runtime check
         {
            result = false;
            System.err.println("**** Analysis Error: wrong number of arguments: "
                              + node + ":"
                              + " at line " + tree.getToken().line
                              + ", position " + tree.getToken().position
                              + "\n" );
         }

         else
         {
            //loop through subtrees and analyze
            for (int i = 0; i < tree.degree(); i++)
            {
               result = analyzeExp(tree.getSubTree(i), env);
            }
         }
      }

      else{
      
         //loop through subtrees and analyze
         for (int i = 0; i < tree.degree(); i++)
         {
            result = analyzeExp(tree.getSubTree(i), env);
         }
      }
   
      return result;
   }//analyzeAExp()


   /**
      Returns true if the given string is one of the reserved keywords.
   */
   private static boolean isKeyword(String word) {
      return keywords.contains(word);
   }

   private static final java.util.List<String> keywords
                   = java.util.Arrays.asList(
                       "prog",
                       "fun",
                       "lambda",
                       "apply",
                       "if",
                       "while",
                       "begin",
                       "var",
                       "set",
                       "print",
                       "true",
                       "false"
                     );
}//StaticAnalysis
