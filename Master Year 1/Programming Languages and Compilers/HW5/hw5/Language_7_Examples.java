/*
   This program parses and evaluates strings from Language_7.
*/

public class Language_7_Examples
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set these to 0 or 1 depending on whether you need to
      // see all of the analyzer's or interpreter's debugging information.
      Evaluate.DEBUG = 1;
      StaticAnalysis.DEBUG = 1;

      final String[] programs =
      {
         // 0, demonstrate a function call
         "(prog\n" +
         "  (var y 6)\n" +
         "  (fun f (lambda x (* x y)))\n" +  // y is a non-local reference
         "  (var z (apply f 5)))",

         // 1, demonstrate a function with multiple parameters
         "(prog\n" +
         "  (var w 10)\n" +
         "  (fun f (lambda x y z (+ x y z w)))\n" + // w is a non-local reference
         "  (var x (apply f 1 2 3))\n" +
         "  (var y (apply f 4 5 x))\n" +
         "  (var z (apply f (+ w x y) 10 20)))",

         // 2, demonstrate composition of functions
         "(prog\n" +
         "  (var w 2)\n" +
         "  (fun f (lambda x (* x x w)))\n" +
         "  (fun g (lambda y (+ y y w)))\n" +
         "  (var z (apply g (apply f 5))))\n",  // function composition

         // 3, demonstrate caling a function from within another function
         "(prog\n" +
         "  (var w 2)\n" +
         "  (fun f (lambda x (var v (* x x w))))\n" +
         "  (fun g (lambda y (var u (+ y w (apply f y)))))\n" +
         "  (var z (apply g 5)))\n",

         // 4, demonstrate local variables
         "(prog\n" +
         "  (var w 10)\n" +
         "  (fun f (lambda x (var u (* x x w))))\n" +
         "  (begin\n" +
         "    (var x 0)\n" +
         "    (begin\n" +
         "      (var y 2)\n" +
         "      (begin\n" +
         "        (set x w)\n" +
         "        (var z (apply f (+ x y w)))))))\n",

         // 5, demonstrate local variables inside a function body
         "(prog\n" +
         "  (var n 1)\n" +
         "  (fun f (lambda x (* n x)))\n" +
         "  (fun g (lambda x (begin\n" +
         "                      (var n 5)\n" +
         "                      (var r (apply f x)))))\n" +
         "  (var z (apply g 2)))\n", // result =  2 with static scope
                                     // result = 10 with dynamic scope

         // 6, recursive function
         "(prog\n" +
         "  (fun fac (lambda n\n" +
         "     (if (<= n 0)\n" +
         "          1\n" +
         "          (* n (apply fac (- n 1))))))\n" +
         "  (apply fac 6))\n",

         // 7, recursive function
         "(prog\n" +
         "  (fun even (lambda n\n" +
         "     (&& (!= n 1)\n" +       // use short-circuiting
         "          (|| (<= n 0)\n" +  // use short-circuiting
         "               (apply even (- n 2))))))\n" +
         "  (print (apply even 7))\n" +
         "  (print (apply even 6))\n" +
         "  (print (apply even 0))\n" +
         "  (apply even -1))\n",

         // 8, mutually recursive functions
         "(prog\n" +
         "  (fun even (lambda n\n" +
         "     (|| (<= n 0)\n" +       // use short-circuiting
         "          (apply odd (- n 1)))))\n" +
         "  (fun odd (lambda n\n" +
         "     (&& (>  n 0)\n" +       // use short-circuiting
         "          (|| (== n 1)\n" +  // use short-circuiting
         "               (apply even (- n 1))))))\n" +
         "  (print (apply even 8))\n" +
         "  (print (apply odd 7))\n" +
         "  (print (apply odd 6))\n" +
         "  (print (apply even 3))\n" +
         "  (apply even -1))\n",
      };


      for (int i = 0; i < programs.length; ++i)
      {
         System.out.println(i + " =========================================");

         // Build, analyze and evaluate the AST that represents the expression.
         try
         {
            final Tree ast = ParseTree.buildTree( programs[i] );

            // Print the AST as an S-expression
            //System.out.println( ast + "\n" );

            // Pretty-print the abstract syntax tree.
            //System.out.println( PrettyPrinter.prettyPrint( ast ) + "\n" );

            // Print the infix version of the expression.
            System.out.println( AST2infix.ast2infix( ast ) + "\n" );

            // Analyze the AST.
            if (! StaticAnalysis.analyze(ast))
            {
               continue;
            }

            // Evaluate the expression (interpret the AST).
            try
            {
               final Value value = Evaluate.eval( ast );
               if (Evaluate.DEBUG > 0)
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
            if (Evaluate.DEBUG > 0)
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
