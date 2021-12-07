/**
   This class defines the nodes for a binary tree data structure.

   A "label" for each node is stored as a String.
*/

public class BTree
{
   private String element;
   private BTree left;
   private BTree right;

   public BTree(String element)
   {
      this.element = element;
      left  = null;
      right = null;
   }

   public BTree(String element, BTree left, BTree right)
   {
      this.element = element;
      this.left  = left;
      this.right = right;
   }

   public String getElement()
   {
      return element;
   }

   public BTree getLeftTree()
   {
      return left;
   }

   public BTree getRightTree()
   {
      return right;
   }

   public void addLeftTree(BTree tree)
   {
      left = tree;
   }

   public void addRightTree(BTree tree)
   {
      right = tree;
   }

   /**
      Compute the number of subtrees below this node.
   */
   public int degree()
   {
      int result = 0;
      if (  left != null ) result++;
      if ( right != null ) result++;
      return result;
   }

   /**
      Calculate the depth of the tree below this node.
   */
   public int depth()
   {
      int result = 0;
      int depthL = 0;
      if (left != null)
      {
         depthL = getLeftTree().depth();
         result = 1;
      }
      int depthR = 0;
      if (right != null)
      {
         depthR = getRightTree().depth();
         result = 1;
      }
      if (depthL > depthR)
      {
         result += depthL;
      }
      else
      {
         result += depthR;
      }
      return result;
   }

}//BTree
