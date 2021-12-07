/*
   Use this program to test your analyzer's
   ability to catch syntax errors. These errors
   could be found be a parser, but we are using
   a semantic analyzer to detect them.

   Every Language_7 string in this file should
   produce a static analysis error.
*/

public class Language_7_Test_Syntax_Errors
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you
      // need to see all of the analyzer's debugging information.
      StaticAnalysis.DEBUG = 1;

      final String[] programs =
      {
         // test var and set
         "(var x)",
         "(set x)",
         "(var var 0)",
         "(set set 0)",
         "(var 0 0)",
         "(set 0 0)",
         "(var x 0 y)",
         "(set x 0 y)",

         // test prog
         "(fun f (lambda x x))",
         "(prog\n(what is this))",

         // test fun
         "(prog\n(fun f))",
         "(prog\n(fun 0 1))",
         "(prog\n(fun f lambda))",
         "(prog\n(fun f x))",
         "(prog\n(fun f (what x y)))",
         "(prog\n(fun f (lambda 0) (lambda 1)))",
         "(prog\n(fun true (lambda x y)))",
         "(prog\n(fun 1234 (lambda x y)))",

         // test lambda
         "(prog\n(fun f\n(lambda if 0)))",
         "(prog\n(fun f\n(lambda 10 0)))",
         "(prog\n(fun f\n(lambda  x if y (+ x y))))",
         "(prog\n(fun f\n(lambda  x 20 y (+ x y))))",

         // test if, while, print
         "(if true)",
         "(if true x)",
         "(if false 10 100 1000)",
         "(while true)",
         "(while true x y)",
         "(print x y)",
         "(print x y z)",
         "(if (print 0 1) (while false))",

         // test arithmetic and boolean expressions
         "(* 1)",
         "(/ 1)",
         "(% 1)",
         "(^ 1)",
         "(- 1 2 3)",
         "(/ 1 2 3)",
         "(% 1 2 3)",
         "(^ 1 2 3)",
         "(|| false)",
         "(&& false)",
         "(== 1)",
         "(!= 1)",
         "(<= 1)",
         "(>= 1)",
         "(<  1)",
         "(>  1)",
         "(== 1 2 3)",
         "(!= 1 2 3)",
         "(<= 1 2 3)",
         "(>= 1 2 3)",
         "(<  1 2 3)",
         "(>  1 2 3)",
         "(! true false)",
         "(- (* 1)\n   (/ 2 3 4) (% 5))"
      };


      for (int i = 0; i < programs.length; ++i)
      {
         System.out.println(i + " =========================================");

         // Build and analyze the AST that represents the expression.
         try
         {
            // Print the expression.
            System.out.println( programs[i] + "\n" );

            final Tree ast = ParseTree.buildTree( programs[i] );

            // Print the AST as an S-expression
            //System.out.println( ast + "\n" );

            // Pretty-print the abstract syntax tree.
            //System.out.println( PrettyPrinter.prettyPrint( ast ) + "\n" );

            // Print the infix version of the expression.
            //System.out.println( AST2infix.ast2infix( ast ) + "\n" );

            // Create dot and png files from the AST.
            if(true)
            try
            {
               // Create the (empty) dot file.
               final String baseName = String.format("Language_7.%02d", i);
               java.io.PrintWriter out = new java.io.PrintWriter(
                                            new java.io.File(baseName + ".dot") );
               // Put dot commands into the dot file
               out.println( Tree2dot.tree2dot(ast) + "\n" );
               out.close();
               // Create a command line for running dot.exe.
               final String[] cmd = {"C:\\graphviz-2.38\\release\\bin\\dot.exe",
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

            // Analyze the AST.
            if ( StaticAnalysis.analyze(ast) )
            {
               System.out.println(
                 ">>>>>> WARNING: This expression should have caused at least one analysis error!\n");
               System.exit(0);
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
