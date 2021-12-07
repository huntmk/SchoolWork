/*
Course: CS 51530
Name: Marcellus Hunt
Email: mkhunt@pnw.edu
Assignment: 1
*/

/**
  This program tests your pretty printing methods.
  Here is an example binary tree.

                 a
                / \
              /     \
             b       c
            / \        \
          /     \        \
         d       e        f
                         /
                       /
                      g
                       \
                         \
                           h
*/
public class Hw1
{
   public static void main(String[] args)
   {
      // Here is the Java declaration of the above binary tree.
      BTree bTree1 = new BTree("a",
                              new BTree("b",
                                        new BTree("d"),
                                        new BTree("e")),
                              new BTree("c",
                                        null,
                                        new BTree("f",
                                                  new BTree("g",
                                                            null,
                                                            new BTree("h")),
                                                  null)));

      // Write the Java declaration for the binary tree in picture bTree2.png.
      BTree bTree2 = new BTree("a",
                              new BTree("b",
                                       new BTree("d"),
                                       new BTree("e",
                                                new BTree("h"),
                                                new BTree("i"))),
                              new BTree("c",
                                       new BTree("f",
                                                new BTree ("j"),
                                                new BTree ("k")),
                                       new BTree("g")));     


      // Write the Java declaration for the binary tree in picture bTree3.png.
      BTree bTree3 = new BTree("*",
                              new BTree("+",
                                       new BTree("/",
                                                new BTree("a"),
                                                new BTree("2")),
                                       new BTree("b")),
                              new BTree("^",
                                       new BTree("c"),
                                       new BTree("3")));



      // Write the Java declaration for the binary tree in picture bTree4.png.
      BTree bTree4 = new BTree("/",
                              new BTree("+",
                                       new BTree("-",
                                                null,
                                                new BTree("b")),
                                        new BTree("sqrt",
                                                 null,
                                                 new BTree("-",
                                                          new BTree("^",
                                                                   new BTree("b"),
                                                                   new BTree("2")),
                                                          new BTree("*",
                                                                   new BTree("a"),
                                                                   new BTree("c"))))),
                              new BTree("*",
                                       new BTree("2"),
                                       new BTree("a")));
                  


      // Write the Java declaration for the binary tree in picture bTree5.png.
      BTree bTree5 = new BTree("if",
                              new BTree("<",
                                       new BTree("a"),
                                       new BTree("b")),
                              new BTree("while", 
                                       new BTree("!=",
                                                new BTree("a"),
                                                new BTree("b")),
                                        new BTree("=",
                                                 new BTree("a"),
                                                 new BTree("+",
                                                          new BTree("a"),
                                                          new BTree("l")))));




      System.out.println( " Preorder traversal --> " + Traverse.preOrder( bTree1 ) );
      System.out.println( "  Inorder traversal --> " + Traverse.inOrder( bTree1 ) );
      System.out.println( "Postorder traversal --> " + Traverse.postOrder( bTree1 ) + "\n" );

      System.out.println( PrettyPrinter0.prettyPrint( bTree1 ) + "\n" );
      System.out.println( PrettyPrinter1.prettyPrint( bTree1 ) + "\n" );
      System.out.println( PrettyPrinter2.prettyPrint( bTree1 ) + "\n" );
      System.out.println( PrettyPrinter3.prettyPrint( bTree1 ) + "\n" );


      System.out.println( " Preorder traversal --> " + Traverse.preOrder( bTree2 ) );
      System.out.println( "  Inorder traversal --> " + Traverse.inOrder( bTree2 ) );
      System.out.println( "Postorder traversal --> " + Traverse.postOrder( bTree2 ) + "\n" );

      System.out.println( PrettyPrinter0.prettyPrint( bTree2 ) + "\n" );
      System.out.println( PrettyPrinter1.prettyPrint( bTree2 ) + "\n" );
      System.out.println( PrettyPrinter2.prettyPrint( bTree2 ) + "\n" );
      System.out.println( PrettyPrinter3.prettyPrint( bTree2 ) + "\n" );


      System.out.println( " Preorder traversal --> " + Traverse.preOrder( bTree3 ) );
      System.out.println( "  Inorder traversal --> " + Traverse.inOrder( bTree3 ) );
      System.out.println( "Postorder traversal --> " + Traverse.postOrder( bTree3 ) + "\n" );

      System.out.println( PrettyPrinter0.prettyPrint( bTree3 ) + "\n" );
      System.out.println( PrettyPrinter1.prettyPrint( bTree3 ) + "\n" );
      System.out.println( PrettyPrinter2.prettyPrint( bTree3 ) + "\n" );
      System.out.println( PrettyPrinter3.prettyPrint( bTree3 ) + "\n" );


      System.out.println( " Preorder traversal --> " + Traverse.preOrder( bTree4 ) );
      System.out.println( "  Inorder traversal --> " + Traverse.inOrder( bTree4 ) );
      System.out.println( "Postorder traversal --> " + Traverse.postOrder( bTree4 ) + "\n" );

      System.out.println( PrettyPrinter0.prettyPrint( bTree4 ) + "\n" );
      System.out.println( PrettyPrinter1.prettyPrint( bTree4 ) + "\n" );
      System.out.println( PrettyPrinter2.prettyPrint( bTree4 ) + "\n" );
      System.out.println( PrettyPrinter3.prettyPrint( bTree4 ) + "\n" );


      System.out.println( " Preorder traversal --> " + Traverse.preOrder( bTree5 ) );
      System.out.println( "  Inorder traversal --> " + Traverse.inOrder( bTree5 ) );
      System.out.println( "Postorder traversal --> " + Traverse.postOrder( bTree5 ) + "\n" );

      System.out.println( PrettyPrinter0.prettyPrint( bTree5 ) + "\n" );
      System.out.println( PrettyPrinter1.prettyPrint( bTree5 ) + "\n" );
      System.out.println( PrettyPrinter2.prettyPrint( bTree5 ) + "\n" );
      System.out.println( PrettyPrinter3.prettyPrint( bTree5 ) + "\n" );
   }
}
