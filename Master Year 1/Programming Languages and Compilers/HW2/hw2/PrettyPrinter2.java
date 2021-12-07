import javax.lang.model.util.ElementScanner6;

/*
   This prettyPrint() method takes a Tree
   and converts it into a well formatted string.

   This pretty-printer does the following.
      1) Always puts the left node inline with the root.
      2) Inlines all the nodes of a tree with depth == 1.
      3) Inlines all the nodes of a tree with depth == 2.
      4) All non-inlined child nodes are vertically aligned with the left node.
      5) All consecutive closing parentheses are at the end of a line.
*/

/*
   Course: CS 51530
   Name: Marcellus Hunt
   Email: mkhunt@pnw.edu
   Assignment: 2
*/


public class PrettyPrinter2
{
   public static String prettyPrint(Tree tree)
   {
      return prettyPrint(tree, "", false);
   }


   private static String prettyPrint(Tree tree, String indentation, boolean inline)
   {
      String result = "";

      //if depth equals 1, print subtree
      if (tree.depth() == 1)
      {
         result += "(" + tree.getElement() + " ";

         
         for (int i = 0; i < tree.degree(); i++)
         {
            result += tree.getSubTree(i) + " " ;
         }

         result += ")";
      }

      //if depth equals 2, print subtree
      else if (tree.depth() == 2)
      {
         result += "(" + tree.getElement() + " ";

         for (int i = 0; i < tree.degree(); i++)
         {
            result += tree.getSubTree(i) + " " ;
         }

         result += ")";

         inline = true;
      } 

      //print subtree on a new line (noninlined nodes) 
      else if (tree.depth() > 2)
      {
         //might need static int to keep track of which subtree i'm on
         // get recursion working inside this part of function
         inline = false;

         //print root
         result += "(" + tree.getElement() + " ";

         
         for (int i = 0; i < tree.degree(); i++)
         {
            if(tree.getSubTree(i).depth() == 0)
            {
               result += tree.getSubTree(i) + ")";
            }
            //if depth less than or equal to 2, print subtree 
            else if(tree.getSubTree(i).depth() <= 2)
            {
               result += tree.getSubTree(i)  + "\n" + "   " ;
               prettyPrint(tree.getSubTree(i), result, inline);
            }

            
            //print new root node then next subtrees 
            else
            {
               Tree n = tree.getSubTree(i);
               //print root
               result += "(" + n.getElement() + " " + n.getSubTree(0) +  "\n" + "   ";

               //loop through and print out remaining subtrees
               for  (int x = 1; x < n.degree(); x++)
               {
                  result+= "   " + n.getSubTree(x) + ")";
                  prettyPrint(n.getSubTree(x), result, inline);
               }

               result += ")";

            }
            
         }
        
      }
      return result;
   }
}
