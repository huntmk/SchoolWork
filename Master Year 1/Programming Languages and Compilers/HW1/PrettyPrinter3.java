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
public class PrettyPrinter3
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
         //output parenthesis
         result += "()";
      }

      //tree of single node 
      else if (tree.depth() == 0)  // depth==0 stops the recursion also
      {
         //get tree element
        result += tree.getElement();
      }

      //tree depth equals 1
      else if (tree.depth() == 1)
      {
         result += "(" + tree.getElement() + " ";

         //get left/right elements
         result += prettyPrint( tree.getLeftTree() ,indentation) + " " ;
         result += prettyPrint( tree.getRightTree() ,indentation) + ")";

      }
      
      //tree depth greater than 1
      else
      {
         //indent after new line
         //increase indentation before starting new sub-tree
         result += "(" + tree.getElement();
         indentation +="   ";

         //begin left sub-tree
         result += " "+ prettyPrint( tree.getLeftTree() ,indentation);
         result += "\n";
         
         //begin right sub-tree
         result += indentation + prettyPrint( tree.getRightTree(),indentation);

         //decrease indentation before printing end parenthesis
         indentation = indentation.substring(0, indentation.length()-3);
         result+= "\n" + indentation + ")" ;
         
      }

      return result;
   }
}
