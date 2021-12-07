/**
   The preOrder() method returns a String containing a
   preorder traversal of the nodes of a BTree.

   The inOrder() method returns a String containing a
   inorder traversal of the nodes of a BTree.

   The postOrder() method returns a String containing a
   postorder traversal of the nodes of a BTree.
*/

public class Traverse
{
   public static String preOrder(BTree tree)
   {
      String result = "";
      if ( tree != null )  // an empty tree stops the recursion
      {
         result += tree.getElement() + " ";
         result += preOrder( tree.getLeftTree() );
         result += preOrder( tree.getRightTree() );
      }
      return result;
   }


   public static String inOrder(BTree tree)
   {
      String result = "";
      if ( tree != null )
      {
         result += inOrder( tree.getLeftTree() );
         result += tree.getElement() + " ";
         result += inOrder( tree.getRightTree() );
      }
      return result;
   }


   public static String postOrder(BTree tree)
   {
      String result = "";
      if ( tree != null )
      {
         result += postOrder( tree.getLeftTree() );
         result += postOrder( tree.getRightTree() );
         result += tree.getElement() + " ";
      }
      return result;
   }

}//Traverse