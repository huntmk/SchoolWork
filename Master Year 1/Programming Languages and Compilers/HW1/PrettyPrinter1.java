/*
Course: CS 51530
Name: Marcellus Hunt
Email: mkhunt@pnw.edu
Assignment: 1
*/

/**
   The prettyPrint() method takes a BTree and
   converts it into a well formated string.
*/
public class PrettyPrinter1
{
   public static String prettyPrint(BTree tree)
   {
      return prettyPrint(tree, "");
   }


   /**
      This prettyPrint() method is essentially a
      preorder traversal of the tree.
   */
   private static String prettyPrint(BTree tree, String indentation)
   {
      String result = "";
      
      //empty tree
      if (tree == null)  // empty tree (stops the recursion)
      {
         result += indentation + "()" + "\n";
      }

      //tree of single node
      else if (tree.depth() == 0)  // depth==0 stops the recursion also
      {
         result += indentation + tree.getElement() + "\n" ;
      }
      else
      {
         //indent after new line
         //increase indentation before starting new tree
         result += indentation + "(" + tree.getElement() + "\n";
         indentation +=" ";

         //begin left tree
         result += prettyPrint( tree.getLeftTree() ,indentation) ;

         //begin right tree
         result += prettyPrint( tree.getRightTree(),indentation);

         //decrease indentation before printing end parenthesis
         indentation = indentation.substring(0, indentation.length()-1);
         result+= indentation + ")" + "\n";
      }


      return result;
   }
}
