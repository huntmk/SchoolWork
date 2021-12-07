/*
   Use this program to test your static analyzer's
   ability to catch semantic errors.

   Every Language_7 string in this file should
   produce a static analysis error.
*/

public class Language_7_Test_Semantic_Errors
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you
      // need to see all of the analyzer's debugging information.
      StaticAnalysis.DEBUG = 0;

      final String[] programs =
      {
         // test var, set, begin
         "x",
         "(set x 0)",
         "(set x y)",
         "(var x x)",
         "(begin x)",
         "(begin (set x 0))",
         "(begin (set x y))",
         "(begin (var x x))",
         "(begin (var x 0) (var x 1))",
         "(begin (var x 0) (var x y))",
         "(begin (var x x) (set x 0))",
         "(prog\nx)",
         "(prog\n(set x 0))",
         "(prog\n(set x y))",
         "(prog\n(var x x))",
         "(prog\n(var x 0)\n(var x 1))",
         "(prog\n(var x 0)\n(var x y))",
         "(prog\n(var x x)\n(set x 0))",
         "(prog\n(begin\nx))",
         "(prog\n(begin\n(set x 0)))",
         "(prog\n(begin\n(set x y)))",
         "(prog\n(begin\n(var x x)))",
         "(prog\n(begin\n(var x 0)\n(var x 1)))",
         "(prog\n(begin\n(var x 0)\n(var x y)))",
         "(prog\n(begin\n(var x x)\n(set x 0)))",
         "(prog\n(var x 0)\n(begin\n(var x x)\n(print y)))",

         // test while
         "(while true\n (print x))",
         "(prog\n(var x 0)\n(begin\n(while true\n  (print x))\n  (print y)))",

         // test fun, lambda, and apply
         "(apply f 0)",
         "(begin\n(apply f 0))",
         "(prog\n(apply f 0))",
         "(prog\n(begin\n(apply f 0)))",
         "(prog\n(fun f (lambda 0))\n(var f true))",
         "(prog\n(var f true)\n(fun f (lambda 0))\nfalse)",
         "(prog\n(fun f (lambda 0))\n(fun f (lambda x 0))\ntrue)",
         "(prog\n(fun f (lambda x x 0))\ntrue)",
         "(prog\n(var y 0)\n(fun f (lambda x (* x y z)))\ntrue)",
         "(prog\n(var x 0)\n(fun f (lambda y (+ x y z)))\n(var z 0)\n(apply f 1))",
         "(prog\n(fun f (lambda x (apply f x)))\n(apply f x))",
         "(prog\n(fun f (lambda x (apply g x)))\n(fun g (lambda y (apply f z)))\n(var z 2)\n(apply g 0))",
         "(prog\n(fun f (lambda x (* x x)))\n(var h f)\n(apply h x))",
         "(prog\n(fun f (lambda x (* x y)))\n(fun g (lambda y (var y x)))\ntrue)",

         // test number of parameters in function calls
         "(prog\n(fun f (lambda 0))\n(apply f 0))",
         "(prog\n(fun f (lambda x 0))\n(apply f 0 1))",
         "(prog\n(fun f (lambda x y z 0))\n(apply f 1))",
         "(prog\n(fun f (lambda x y z 0))\n(apply f 1 2))",
         "(prog\n(fun f (lambda x y z 0))\n(apply f 1 2 3 4))",
         "(prog\n(fun f (lambda x x 0))\n(apply f 1))",
         "(prog\n(fun f (lambda (apply f)))\n(apply f 1))",
         "(prog\n(fun f (lambda x (* x x)))\n(var h f)\n(apply h x x))",
         "(prog\n(fun f (lambda x y (* x y)))\n(var h f)\n(apply h x))",
         "(prog\n(fun f (lambda x y (* x y)))\n(var h f)\n(apply h 0)\n(apply f 0))",
         "(prog\n(fun f (lambda x y (* x y)))\n(fun g (lambda y (* y y)))\n(begin\n(var f g)\n(apply f x)))",
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
               System.out.println(">>>>>> WARNING: This expression should have caused at least one analysis error!\n");
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
            System.out.println(">>>>>> WARNING: This expression should not have caused a parse error!\n");
            System.exit(0);
         }
      }
   }
}
