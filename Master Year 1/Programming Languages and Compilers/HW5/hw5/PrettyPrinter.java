/**
   The prettyPrint() method takes a Tree and
   converts it into a well formatted string.
*/

public class PrettyPrinter
{
   public static String prettyPrint(Tree tree)
   {
      return prettyPrint(tree, "");
   }

   /**
      This prettyPrint() method is essentially
      a pre-order traversal of the tree.
   */
   public static String prettyPrint(Tree tree, String indentation)
   {
      String result = indentation;

      if ( 0 == tree.degree() )
      {
         result += tree.getElement();
      }
      else
      {
         // "process" the root node
         result += "(" + tree.getElement();

         // recursively traverse all the sub trees
         for (int i = 0; i < tree.degree(); i++)
         {
            result += "\n" + prettyPrint( tree.getSubTree(i), indentation + "  " );
         }

         result += "\n" + indentation + ")";
      }

      return result;
   }//prettyPrint()
}
