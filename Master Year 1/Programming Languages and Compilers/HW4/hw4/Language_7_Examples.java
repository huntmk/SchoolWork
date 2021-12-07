/*
   This program parses and evaluates strings from Language_7.
*/

public class Language_7_Examples
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you need
      // to see all of the interpreter's debugging information.
      Evaluate_7a.DEBUG = 1;

      String[] programs =
      {
         // 0, demonstrate a function call
         "(prog" +
           "(var y 6) " +
           "(fun f (lambda x (* x y)))" +  // y is a non-local reference
           "(var z (apply f 5)))",

         // 1, demonstrate a function with multiple parameters
         "(prog" +
           "(var w 10)" +
           "(fun f (lambda x y z (+ x y z w)))" + // w is a non-local reference
           "(var x (apply f 1 2 3))" +
           "(var y (apply f 4 5 x))" +
           "(var z (apply f (+ w x y) 10 20)))",

         // 2, demonstrate composition of functions
         "(prog" +
           "(var w 2)" +
           "(fun f (lambda x (* x x w)))" +
           "(fun g (lambda y (+ y y w)))" +
           "(var z (apply g (apply f 5))))",  // function composition

         // 3, demonstrate caling a function from within another function
         "(prog" +
           "(var w 2)" +
           "(fun f (lambda x (var v (* x x w))))" +
           "(fun g (lambda y (var u (+ y w (apply f y)))))" +
           "(var z (apply g 5)))",

         // 4, demonstrate local variables
         "(prog" +
           "(var w 10)" +
           "(fun f (lambda x (var u (* x x w))))" +
           "(begin" +
             "(var x 0)" +
             "(begin" +
               "(var y 2)" +
               "(begin" +
                 "(set x w)" +
                 "(var z (apply f (+ x y w)))))))",

         // 5, demonstrate local variables inside a function body
         "(prog" +
           "(var n 1)" +
           "(fun f (lambda x (* n x)))" +
           "(fun g (lambda x (begin" +
                                "(var n 5)" +
                                "(var r (apply f x)))))" +
           "(var z (apply g 2)))", // result =  2 with static scope
                                   // result = 10 with dynamic scope

         // 6, recursive function
         "(prog" +
           "(fun fac (lambda n" +
              "( if (<= n 0)" +
                   "1" +
                   "(* n (apply fac (- n 1))))))" +
           "(apply fac 6))",

         // 7, recursive function
         "(prog" +
           "(fun even (lambda n" +
              "( && ( != n 1)" +      // use short-circuiting
                   "( || (<= n 0)" +  // use short-circuiting
                        "(apply even (- n 2))))))" +
           "(print (apply even 7))" +
           "(print (apply even 6))" +
           "(print (apply even 0))" +
           "(apply even -1))",

         // 8, mutually recursive functions
         "(prog" +
           "(fun even ( lambda n " +
              "(|| (<= n 0 ) " +     // use short-circuiting
                   "(apply odd (- n 1)))))" +
           "(fun odd (lambda n" +
              "(&& (>  n 0)" +       // use short-circuiting
                   "(|| (== n 1)" +  // use short-circuiting
                        "(apply even (- n 1))))))" +
           "(print (apply even 8))" +
           "(print (apply odd 7))" +
           "(print (apply odd 6))" +
           "(print (apply even 3))" +
           "(apply even -1))",
      };


      int i = 0;
      for (i = 0; i < programs.length; i++)
      {
         System.out.println(i + " =========================================");

         // Build and evaluate the AST that represents the expression.
         try
         {
            Tree ast = ParseTree.buildTree( programs[i] );

            // Print the AST as an S-expression
            //System.out.println( ast + "\n" );

            // Pretty-print the abstract syntax tree.
            System.out.println( PrettyPrinter.prettyPrint( ast ) + "\n" );

            // Print the infix version of the expression.
            System.out.println( AST2infix_7a.ast2infix( ast ) + "\n" );

            // Evaluate the expression (interpret the AST).
            try
            {
               Value value = Evaluate_7a.eval( ast );

               System.out.println("result = " + value + "\n" );
            }
            catch (EvalException e)
            {
               System.out.println(e);
             //e.printStackTrace();
            }


            // Create dot and png files from the AST.
            if (Evaluate_7a.DEBUG > 0)
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
