/*
   This program parses and evaluates strings from Language_6.
*/

public class Language_6_Examples
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you need
      // to see all of the interpreter's debugging information.
      Evaluate_6a.DEBUG = 1;

      String[] programs =
      {
         "(if true 0 1)",

         "(if (== 0 1) (+ 3 -5) (^ 2 5))",

         "(prog" +
             "(var b true)" +
             "(if b (var x -1) (var x 1)))",

         "(prog" +
             "(var i -5)" +
             "(if ( >= i 0)" +
               "(begin" +
                 "(print i))" +
               "(begin" +
                 "(print (- i)))))",

         "(prog" +
             "(var i 5)" +
             "(while (> i 0)" +
               "(begin" +
                 "(print i)" +
                 "(set i (- i 1)))))",

         "(prog" +
             "(var i 5)" +
             "(while (> i 0)" +
                 "(print (set i (- i 1)))))",

         "(prog" +
             "(var i -5)" +
             "(if (>= i 0)" +
                "(while (> i 0)" +
                  "(begin" +
                    "(print i)" +
                    "(set i (- i 1))))" +
                "(while (< i 0)" +
                  "(begin" +
                    "(print i)" +
                    "(set i (+ i 1))))))",

         "(prog" +
            "(var n 1)" +
            "(var sum 0)" +
            "(while (< n 5)" +
              "(begin" +
                "(var i n)" +
                "(set sum (+ sum i))" +
                "(set n (+ n 1))" +
                "(print n)" +
                "(print sum)))" +
            "sum)",
      };


      int i = 0;
      for (i = 0; i < programs.length; i++)
      {
         System.out.println(i + " =========================================");

         // Build the abstract syntax tree that represents the expression.
         try
         {
            Tree ast = ParseTree.buildTree( programs[i] );

            // Print the AST as an S-expression
            //System.out.println( ast + "\n" );

            // Pretty-print the abstract syntax tree.
            System.out.println( PrettyPrinter2.prettyPrint( ast ) + "\n" );

            // Print the infix version of the expression.
            System.out.println( AST2infix_6a.ast2infix( ast ) + "\n" );

            // Evaluate the expression (interpret the AST).
            try
            {
               Value value = Evaluate_6a.eval( ast );
               if (Evaluate_6a.DEBUG > 0)
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
            if (Evaluate_6a.DEBUG > 0)
            try
            {
               // Create the (empty) dot file.
               String baseName = String.format("Language_6.%02d", i);
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
