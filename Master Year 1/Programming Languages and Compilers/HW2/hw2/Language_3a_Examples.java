/*
   This program parses and evaluates strings from Language_3a.
*/

public class Language_3a_Examples
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you need
      // to see all of the interpreter's debugging information.
      Evaluate_3a.DEBUG = 1;

      String[] programs =
      {
         "(++ false)",   // runtime errors
         "(--- 2)",
         "(++ x)",
         "(prog (var x 3) (++ x 1))",
         "(-- (+ 2 3))",
         "(<=> 1)",
         "(<=> 1 2 3)",
         "(<=> 1 false)",

         // Examples.
         "(<=> 3 4)",
         "(<=> 4 3)",
         "(<=> 4 4)",
         "(<=> false true)",
         "(<=> true false)",
         "(<=> false false)",
         "(<=> true true)",
         "(prog (var x false) (var y true) (var z (<=> (+++ x) (+++ y))))",
         "(prog (var x true) (var y false) (var z (<=> (--- x) (+++ y))))",
         "(prog (var x 3) (print (++ x))  x)",
         "(prog (var x 3) (print (+++ x)) x)",
         "(prog (var x 3) (print (-- x))  x)",
         "(prog (var x 3) (print (--- x)) x)",

         "(prog (var x true) (print (++x))  x)",
         "(prog (var x true) (print (+++x)) x)",
         "(prog (var x true) (print (--x))  x)",
         "(prog (var x true) (print (---x)) x)",
         "(prog (var x false) (print (++x)) x)",
         "(prog (var x false) (print (+++x)) x)",
         "(prog (var x false) (print (--x))  x)",
         "(prog (var x false) (print (---x)) x)",

         // the next two examples show that the addition operator is not commutative
         "(prog " +
            "(var x 1) " +
            "(+ (* 3 (++ x)) " +
               "(* 2 (++ x))))",
         "(prog " +
            "(var x 1) " +
            "(+ (* 2 (++ x)) " +
               "(* 3 (++ x))))",
         // the next two expressions should return the same value
         "(prog " +
            "(var x 1) " +
            "(+ (* 3 (+++ x)) " +
               "(* 2 (++ x))))",
         "(prog " +
            "(var x 1) " +
            "(+ (* 2 (+++ x)) " +
               "(* 3 (++ x))))",

         "(prog " +
            "(var x 1) " +
            "(print (++ x)) " +
            "(print (+++ x)) " +
            "(print (++ x)) " +
            "(print (+++ x)) " +
            "(print (-- x)) " +
            "(print (--- x)) " +
            "(print (-- x)) " +
            "(print (--- x)))"
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
               String baseName = String.format("Language_3a.%02d", i);
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
