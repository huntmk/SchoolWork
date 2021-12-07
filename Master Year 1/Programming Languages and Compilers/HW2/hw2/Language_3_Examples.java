/*
   This program parses and evaluates strings from Language_3.
*/

public class Language_3_Examples
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you need
      // to see all of the interpreter's debugging information.
      Evaluate_3a.DEBUG = 1;

      String[] programs =
      {
         "(+ 1 false)",   // runtime errors
         "(+ true 2)",
         "(&& 1 false)",
         "(|| false 0)",
         "(! (+ 1 1))",
         "(+ x 2)",
         "(var x (var x 2))",
         "(prog (var x 0) (&& true x))",
         // Examples.
         "(prog " +
            "(var x 2) " +
            "(var y x))",

         "(prog " +
            "(var x 2) " +
            "(var y 2) " +
            "(var z (+ x y)))",

         "(prog " +
            "(var x 0) " +
            "(var y 0) " +
            "(var x (+ x 1)) " +
            "(var y (+ x y)) " +
            "(var x (+ x y 3)))",

         "(prog " +
            "(var x 4) " +
            "(var y false) " +
            "(|| y ( == (+ 2 x) (* 2 3))))",

         "(var z (+ (var x (var y 2)) (+ y (* 3 x))))",

         "(prog " +
            "(var x 1) " +
            "(+ (* 3 (var x (+ x 1))) (* 2 (var x (+ x 1)))))",

         "(prog " +
            "(var n 1) " +
            "(var x1 (* n n)) " +
            "(var n 2) " +
            "(var x2 (* n n)) " +
            "(var n 3) " +
            "(var x3 (* n n)) " +
            "(var n 4) " +
            "(var x4 (* n n)) " +
            "(+ (+ (+ x1 x2) x3) x4) " +
            "(+  x1 x2 x3 x4))",

         "(prog " +
            "(var n 1) " +
            "(var x (* n n)) " +
            "(print x) " +
            "(var n 2) " +
            "(var x (+ x (* n n))) " +
            "(print x) " +
            "(var n 3) " +
            "(var x (+ x (* n n))) " +
            "(print x) " +
            "(var n 4) " +
            "(+ x (* n n)))",
      };

      int i = 0;
      for (i = 0; i < programs.length; i++)
      {
         System.out.println(i + " =============================================");

         // Build the abstract syntax tree that represents the expression.
         try
         {
            Tree ast = ParseTree.buildTree( programs[i] );

            // Print the AST as an S-expression
            System.out.println( ast + "\n" );

            // Pretty-print the abstract syntax tree.
            System.out.println( PrettyPrinter2.prettyPrint( ast ) + "\n" );

            // Print the infix version of the expression.
            System.out.println( AST2infix_3a.ast2infix( ast ) + "\n" );

            // Evaluate the expression (interpret the AST).
            try
            {
               Value value = Evaluate_3a.eval( ast );
               if (Evaluate_3a.DEBUG > 0)
               {
                  System.out.println("result = " + value + "\n" );
               }
               else
               {
                  System.out.println("result = " + value.toSimpleString() + "\n" );
               }
            }
            catch (EvalException e)
            {
               System.out.println(e);
             //e.printStackTrace();
            }


            // Create dot and png files from the AST.
            if (Evaluate_3a.DEBUG > 0)
            try
            {
               // Create the (empty) dot file.
               String baseName = String.format("Language_3.%02d", i);
               java.io.PrintWriter out = new java.io.PrintWriter(
                                            new java.io.File(baseName + ".dot") );
               // Put dot commands into the dot file
               out.println( Tree2dot.tree2dot(ast) + "\n" );
               out.close();
               // Create a command line for running dot.exe.
               String[] cmd = {"C:\\graphviz-2.38\\release\\bin\\dot.exe",
                               "-Tpng",
                               baseName + ".dot",
                               "-o",
                               baseName + ".png"};
               // Execute the command line.
               java.lang.Runtime.getRuntime().exec(cmd);
            }
            catch (Exception e)
            {
               System.out.println( e );
            }
         }
         catch (TokenizeException e)
         {
            System.out.println(e);
          //e.printStackTrace();
         }
         catch (ParseException e)
         {
            System.out.println(e);
          //e.printStackTrace();
         }
      }
   }
}
