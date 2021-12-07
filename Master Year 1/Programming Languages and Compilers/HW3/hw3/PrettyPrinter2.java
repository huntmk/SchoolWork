/**
   This file is a stub. Replace it with your
   PrettyPrinter2.java file from assignment 2.
*/

public class PrettyPrinter2
{
   public static String prettyPrint(Tree tree)
   {
      return prettyPrint(tree, "", true);
   }

   private static String prettyPrint(Tree tree, String indentation, boolean inline)
   {
      String result = "";
              
      //always print root
      result += "(" + tree.getElement() + " " ;
            
       
      if (tree.depth() == 1 || tree.depth() ==2 )
      {
         for (int i = 0; i < tree.degree(); i++)
         {
            //print out all subtrees
            result += tree.getSubTree(i);
            
         }

         result += ")";
      }

      //print subtree on a new line (noninlined nodes) 
      else if(tree.depth() > 2)
      {
         //recursively call left node
         result += prettyPrint(tree.getSubTree(0),indentation, false) ;

         //increase indentation
         indentation += "   ";

         for (int i = 1; i < tree.degree(); i++)
         {
            //create new line and indent before calling recursion
            result+= "\n";
            result+= indentation + prettyPrint(tree.getSubTree(i), indentation, false);
         }

         //everytime else statement is called, add closing end parenthesis
         result+=")";

      } 
      return result;
   }
}