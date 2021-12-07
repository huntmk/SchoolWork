/**
   This class defines the nodes for a tree data structure.

   A "label" for each node is stored as a String.

   The subtrees of a node are stored in an ArrayList, so
   a node can have an arbitrary number of subtrees and
   the subtrees are ordered by the index of the ArrayList.
*/
import java.util.ArrayList;

public class Tree
{
   private String element;
   private ArrayList<Tree> subTrees;

   public Tree(String element, Tree... subTrees) // variable number of arguments
   {
      this.element = element;
      if (0 != subTrees.length)
      {
         this.subTrees = new ArrayList<Tree>();
         for (Tree tree : subTrees)
         {
            this.subTrees.add(tree);
         }
      }
   }

   public String getElement()
   {
      return element;
   }

   public void addSubTree(Tree tree)
   {
      if (subTrees == null)
         subTrees = new ArrayList<Tree>();
      subTrees.add( tree );
   }

   public Tree getSubTree(int i)
   {
      return subTrees.get(i);
   }

   /**
      Compute the number of subtrees below this node.
   */
   public int degree()
   {
      if (subTrees == null)
         return 0;
      else
         return subTrees.size();
   }

   /**
      Calculate the depth of the subtree below this node.
   */
   public int depth()
   {
      int result = 0;
      if (degree() > 0)
      {
         int max = 0;
         for (int i = 0; i < degree(); i++)
         {
            int temp = getSubTree(i).depth();
            if (temp > max) max = temp;
         }
         result = 1+max;
      }
      return result;
   }

   /**
      Convert the Tree into an S-expression like form.
   */
   public String toString()
   {
      String result = "";
      if (null == subTrees)
      {
         result += element;
      }
      else
      {
         result += "(" + element;
         for (int i = 0; i < subTrees.size(); i++)
         {
            result += " " + subTrees.get(i);
         }
         result += ")";
      }
      return result;
   }
}
