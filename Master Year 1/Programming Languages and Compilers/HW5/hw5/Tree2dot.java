/**
   This program converts a Tree data structure into
   a DOT description of the tree. The DOT description
   can then be processed by the dot.exe command to
   produce a graphical image of the tree data structure.

   Create a png file (from a dot file) with the following
   command line.
   > dot.exe -Tpng -O tree.dot

   See
   http://www.graphviz.org/Documentation.php
*/

public class Tree2dot
{
   private static int nodeCount;

   public static String tree2dot(Tree tree)
   {
      String result = "graph {\n";
      result += "node0 [label=\"" + tree.getElement() + "\"];\n";
      nodeCount = 0;
      result += tree2dot(tree, nodeCount);
      result += "}\n";
      return result;
   }


   /**
      This tree2dot() method is essentially a
      preorder traversal of the tree.
   */
   public static String tree2dot(Tree tree, int nodeNumber)
   {
      String result = "";

      // create new nodes
      for (int i = 0; i < tree.degree(); i++)
      {
         result += "node" + (nodeCount+(i+1)) + " ";
         result += "[label=\"" + tree.getSubTree(i).getElement() + "\"];\n";
      }

      // create the edges
      for (int i = 0; i < tree.degree(); i++)
      {
         result += "node" + nodeNumber + " -- " + "node" + (nodeCount+(i+1)) + ";\n";
      }

      nodeNumber = nodeCount;
      nodeCount += tree.degree();  // count the nodes that we just created

      // convert each sub tree into a dot description
      for (int i = 0; i < tree.degree(); i++)
      {
         result += tree2dot(tree.getSubTree(i), nodeNumber+(i+1));
      }

      return result;
   }
}
