/*
   This ast2infix() method takes an Abstract Syntax Tree
   for Language_7 and converts it into a string that
   contains the infix version of the expression.

   ast2infix() uses knowledge about operator precedence
   (in infix notation) to use the minimum number of
   parentheses.
*/

public class AST2infix
{
   public static String ast2infix(Tree tree)
   {
      String result = "";

      String node = tree.getElement();

      if ( node.equals("prog") )
      {
         result += "Program\n{\n";

         // process each expression
         for (int i = 0; i < tree.degree(); i++)
         {
            result += ast2infix( tree.getSubTree(i), "   ", false );

            if ( tree.getSubTree(i).getElement().equals("fun") )
            {  // give function definitions an extra \n
               result += "\n";
            }

            if ( tree.getSubTree(i).getElement().equals("fun")
              || tree.getSubTree(i).getElement().equals("begin")
              || tree.getSubTree(i).getElement().equals("if")
              || tree.getSubTree(i).getElement().equals("while") )
            {  // the above expressions do not need a semicolon
               result += "\n";
            }
            else // include a semicolon
               result += ";\n";
         }

         result += "}";
      }
      else  // a single expression
      {
         result += ast2infix( tree, "", false );
      }

      return result;
   }

   /**
      This ast2infix() method is essentially
      a in-order traversal of the tree.
   */
   public static String ast2infix(Tree tree, String indent, boolean inline)
   {
      String result = "";

      String node = tree.getElement();

      if ( ! inline )
         result += indent;

      if ( node.equals("fun") )
      {
         // do the name of the function
         result += tree.getSubTree(0).getElement() + "(";
         // get the number of formal parameters from the lambda expression
         int numberOfParameters = tree.getSubTree(1).degree() - 1;
         // do the list of formal parameters from the lambda expression
         for (int i = 0; i < numberOfParameters; i++)
         {
            String variable = tree.getSubTree(1).getSubTree(i).getElement();
            result += variable;
            if (i < numberOfParameters - 1)
               result += ", ";
         }
         result += ")\n";
         // do the function body from the lambda expression
         if ( tree.getSubTree(1).getSubTree(numberOfParameters).getElement().equals("begin") )
         {
            result += ast2infix( tree.getSubTree(1).getSubTree(numberOfParameters), indent, false );
         }
         else // a C/C++/Java type function body
         {
            result += indent + "{\n";
            result += ast2infix( tree.getSubTree(1).getSubTree(numberOfParameters), indent+"   ", false );
            if ( tree.getSubTree(1).getSubTree(numberOfParameters).getElement().equals("if")
              || tree.getSubTree(1).getSubTree(numberOfParameters).getElement().equals("while") )
            {  // the above expressions do not need a semicolon
               result += "\n" + indent + "}";
            }
            else  // include a semicolon
               result += ";\n" + indent + "}";
         }
      }
      else if ( node.equals("apply") )
      {
          // do the function value
         result += ast2infix( tree.getSubTree(0), indent+"   ", true) + "(";
         // do the actual parameters
         for (int i = 1; i < tree.degree(); i++)
         {
            result += ast2infix( tree.getSubTree(i), indent+"   ", true );
            if (i < tree.degree() - 1)
               result += ", ";
         }
         result += ")";
      }
      else if ( node.equals("if") )
      {
         result += "if (" + ast2infix( tree.getSubTree(0), indent+"       ", true ) + ")\n";

         if ( tree.getSubTree(1).getElement().equals("begin") )
         {
            result += ast2infix( tree.getSubTree(1), indent, false );
         }
         else if ( tree.getSubTree(1).getElement().equals("while")
                 ||tree.getSubTree(1).getElement().equals("if") )
         {
            result += ast2infix( tree.getSubTree(1), indent+"   ", false );
         }
         else
         {
            result += ast2infix( tree.getSubTree(1), indent+"   ", false );
            result += ";";
         }

         result += "\n" + indent + "else\n";

         if ( tree.getSubTree(2).getElement().equals("begin") )
         {
            result += ast2infix( tree.getSubTree(2), indent, false );
         }
         else if ( tree.getSubTree(2).getElement().equals("while")
                 ||tree.getSubTree(2).getElement().equals("if") )
         {
            result += ast2infix( tree.getSubTree(2), indent+"   ", false );
         }
         else
         {
            result += ast2infix( tree.getSubTree(2), indent+"   ", false ) + ";";
         }
      }
      else if ( node.equals("while") )
      {
         result += "while (" + ast2infix(tree.getSubTree(0), indent+"       ", true) + ")\n";

         if ( tree.getSubTree(1).getElement().equals("begin") )
         {
            result += ast2infix( tree.getSubTree(1), indent, false );
         }
         else if ( tree.getSubTree(1).getElement().equals("while")
                 ||tree.getSubTree(1).getElement().equals("if") )
         {
            result += ast2infix( tree.getSubTree(1), indent+"   ", false );
         }
         else
         {
            result += ast2infix( tree.getSubTree(1), indent+"   ", false );
            result += ";";
         }
      }
      else if ( node.equals("set") )
      {
       //indent += 3 + tree.getSubTree(0).getElement().length();
         result += tree.getSubTree(0).getElement()
                   + " = " + ast2infix( tree.getSubTree(1), "   "+indent, true );
      }
      else if ( node.equals("var") )
      {
       //indentation += 7 + tree.getSubTree(0).getElement().length();
         result += "var " + tree.getSubTree(0).getElement()
                   + " = " + ast2infix( tree.getSubTree(1), "       "+indent, true );
      }
      else if ( node.equals("begin") )
      {
         result += "{\n";

         // process each expression
         for(int i = 0; i < tree.degree(); i++)
         {
            result += ast2infix( tree.getSubTree(i), indent + "   ", false );
            if ( tree.getSubTree(i).getElement().equals("begin")
              || tree.getSubTree(i).getElement().equals("if")
              || tree.getSubTree(i).getElement().equals("while") )
            {  // the above expressions do not need a semicolon
               result += "\n";
            }
            else
               result += ";\n";
         }

         result += indent + "}";
      }
      else if ( node.equals("print") )
      {
         result += "print( " + ast2infix( tree.getSubTree(0), indent, true ) + " )";
      }
      else if ( tree.degree() == 0 )  // constant term
      {
         result += tree.getElement();
      }
      else if ( tree.degree() == 1 )  // unary operator
      {
         if ( tree.getSubTree(0).degree() >= 2 )
         {
            result += tree.getElement() + "(" + ast2infix( tree.getSubTree(0), indent, true ) + ")";
         }
         else
         {
            result += tree.getElement() + ast2infix( tree.getSubTree(0), indent, true );
         }
      }
      else if ( tree.degree() == 2 )  // binary operator
      {
         int middlePrecedence = precedence( tree.getElement() );
         int leftPrecedence = precedence( tree.getSubTree(0).getElement() );

         if ( leftPrecedence > middlePrecedence )
         {
            result += "(" + ast2infix( tree.getSubTree(0), indent, true ) + ")";
         }
         else
         {
            result += ast2infix( tree.getSubTree(0), indent, true );
         }

         result += " " + tree.getElement() + " ";

         int rightPrecedence = precedence( tree.getSubTree(1).getElement() );

         if ( rightPrecedence >= middlePrecedence )
         {
            result += "(" + ast2infix( tree.getSubTree(1), indent, true ) + ")";
         }
         else
         {
            result += ast2infix( tree.getSubTree(1), indent, true );
         }
      }
      else  // operators with 3 or more operands
      {
         int operatorPrecedence = precedence( tree.getElement() );
         int leftPrecedence = precedence( tree.getSubTree(0).getElement() );

         if ( leftPrecedence > operatorPrecedence )
         {
            result += "(" + ast2infix( tree.getSubTree(0), indent, true ) + ")";
         }
         else
         {
            result += ast2infix( tree.getSubTree(0), indent, true );
         }

         for (int i = 1; i < tree.degree(); i++)
         {
            result += " " + tree.getElement() + " ";

            int rightPrecedence = precedence( tree.getSubTree(i).getElement() );

            if ( rightPrecedence >= operatorPrecedence )
            {
               result += "(" + ast2infix( tree.getSubTree(i), indent, true ) + ")";
            }
            else
            {
               result += ast2infix( tree.getSubTree(i), indent, true );
            }
         }
      }

      return result;
   }


   // Add the 'var' and 'print' operators to this function.
   // http://www.java-tips.org/java-se-tips/java.lang/what-is-java-operator-precedence.html
   // http://www.cs.princeton.edu/introcs/11precedence/
   private static int precedence(String op)
   {
      int result = 0;  // "highest" precedence (i.e., constants)

      if ( op.equals("!") )
      {
         result = 2;
      }
      else if ( op.equals("^") )
      {
         result = 3;
      }
      else if ( op.equals("*")
             || op.equals("/")
             || op.equals("%") )
      {
         result = 4;
      }
      else if ( op.equals("+")
             || op.equals("-") )
      {
         result = 5;
      }
      else if ( op.equals("<")
             || op.equals(">")
             || op.equals("<=")
             || op.equals(">=") )
      {
         result = 7;
      }
      else if ( op.equals("==")
             || op.equals("!=") )
      {
         result = 8;
      }
      else if ( op.equals("&&") )
      {
         result = 11;
      }
      else if ( op.equals("||") )
      {
         result = 12;
      }
      else if ( op.equals("if") )
      {
         result = 14;
      }
      else if ( op.equals("while") )
      {
         result = 14;
      }
      else if ( op.equals("var") )
      {
         result = 14;
      }
      else if ( op.equals("set") )
      {
         result = 14;
      }
      else if ( op.equals("print") )
      {
         result = 14;
      }
      else if ( op.equals("begin") )
      {
         result = 14;
      }

      return result;
   }
}
