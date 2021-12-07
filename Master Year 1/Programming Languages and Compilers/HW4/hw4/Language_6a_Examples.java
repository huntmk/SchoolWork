/*
   This program parses and evaluates strings from Language_6a.
*/

public class Language_6a_Examples
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you need
      // to see all of the interpreter's debugging information.
      Evaluate_7a.DEBUG = 1;

      String[] programs =
      {
         "(for (var i 1) (<= i 8) (set i (+ i 2))" +
             "(print i))",

         "(prog" +
             "(var i -1)" +
             "(for (set i 1) (<= i 4) (set i (+ i 1))" +
                 "(print i))" +
             "(print i))",

         "(prog" +
             "(var i 0)" +
             "(for (var i 1) (<= i 4) (+++ i)" +
                 "(print i))" +
             "(print i))",

         "(prog" +
             "(var i -1)" +
             "(for (var i 0) (< i 4) (++ i)" +
                 "(begin" +
                    "(var i 0)" +
                    "(print i)))" +
             "(print i))",

         "(prog" +
             "(var x 100)" +
             "(var i -1)" +
             "(for (var i 0) (< i 4) (++ i)" +
                 "(begin" +
                    "(var i 0)" +
                    "(print (+ x (+++ i)))))" +
             "(print i))",

         "(prog" +
             "(for (var i 1) (<= i 5) (+++ i)" +
                 "(print i))" +
             "(print i))",   // this is an error

         "(prog" +
             "(var n 8)" +  // compute n!
             "(for (var i (- n 1)) (> i 0) (--- i)" +
                 "(set n (* i n)))" +
             "(print n))",

         "(prog" +
             "(var n 420135)" +  // reverse the digits of n
             "(print n)" +
             "(var m 0)" +
             "(for (var d 0) (> n 0) (set n (/ (- n d) 10))" +
                 "(begin" +
                    "(set d (% n 10))" +
                    "(set m (+ (* 10 m) d))" +
                    "(print m)))" +
              "(print m))",

         "(prog" +
             "(var i 0)" +
             "(repeat (print (++ i)) (< i 5))" +
             "(repeat (print (-- i)) (>= i 0)))",

         "(prog" +
             "(var n 420135)" + // print the digits of n
             "(print n)" +
             "(repeat" +
                "(begin" +
                   "(var d (% n 10))" +
                   "(set n (/ n 10))" +
                   "(print d))" +
                "(> n 0)))",

         "(prog" +
             "(var n 420135)" +  // reverse the digits of n
             "(print n)" +
             "(var m 0)" +
             "(repeat" +
                 "(begin" +
                    "(var d (% n 10))" +
                    "(set n (/ n 10))" +
                    "(set m (+ (* 10 m) d))" +
                    "(print m))" +
                 "(> n 0)))"
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
               String baseName = String.format("Language_6a.%02d", i);
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
