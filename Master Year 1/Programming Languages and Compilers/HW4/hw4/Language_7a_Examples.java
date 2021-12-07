/*
   This program parses and evaluates strings from Language_7a.
*/

public class Language_7a_Examples
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you need
      // to see all of the interpreter's debugging information.
      Evaluate_7a.DEBUG = 1;

      String[] programs =
      {
         "(prog (var a (array 1 2 3 4 5 6 false true))" +
                "(var b (array dim 10 1 2 3 4 5 false a))" +
                "(print a)" +
                "(print b)" +
                "(print (index b 4))" +
                "(print (index b 5))" +
                "(print (index b 6))" +
                "(print (sizeOf b))" +
                "(var c (array dim 10))" +
                "(set (index c 5) (index a 2))" +
                "c)",


         "(prog (var n 5)" +
                "(var a (array dim n))" +
                "(print (sizeOf a))" +
                "(print a)" +
                "(var i 0)" +
                "(while (< i (sizeOf a))" +
                  "(begin" +
                    "(set (index a i) (- i))" +
                    "(print (index a i))" +
                    "(set i (+ i 1))))" +
                "a)",


        "(prog" +
          "(fun g (lambda x (+ 10 x)))" +
          "(fun printArray (lambda a" +
            "(for (var i 0) (< i (sizeOf a)) (++ i)" +
              "(print (index a i)))))" +
          "(fun arrayMap (lambda f a" +  // f is a function parameter
            "(begin" +
              "(for (var i 0) (< i (sizeOf a)) (+++ i)" +
                "(set (index a i) (apply f (index a i))))" +
              "a)))" +
          "(var a (array 2 3 4 5))" +
          "(print a)" +
          "(apply arrayMap g a)" +
          "(print a)" +
          "(apply printArray (apply arrayMap g a))" +
          "a)",


        "(prog" +
          "(fun fill (lambda a" +
            "(begin" +
              "(var i 0)" +
              "(while (< i (sizeOf a))" +
                "(begin" +
                  "(set (index a i) (rand 0 100))" +
                  "(set i (+ i 1))))" +
              "a)))" + // this is the return value for fill()
          "(fun max (lambda a" +
            "(begin" +
              "(var m (index a 0))" +
              "(var i 1)" +
              "(while (< i (sizeOf a))" +
                "(begin" +
                  "(if (< m (index a i))" +
                    "(set m (index a i))" +
                    "0) " +
                  "(set i (+ i 1))))" +
              "m)))" +   // this is the return value for max()
          "(var SIZE 20)" +
          "(apply max (print (apply fill (array dim SIZE)))))",


        "(prog" +
          "(fun fill (lambda a" +
            "(for (var i 0) (< i (sizeOf a)) (++ i)" +
              "(set (index a i) (rand 0 100)))))" +
          "(fun extreme (lambda a comp" +  // a "generic" max/min function
            "(begin" +
              "(var m (index a 0))" +
              "(for (var i 1) (< i (sizeOf a)) (++ i)" +
                "(if (apply comp (index a i) m)" +
                  "(set m (index a i))" +
                  "0))" +
              "m)))" +   // return value for extreme()
          "(fun comp1 (lambda a b (< a b)))" +
          "(fun comp2 (lambda a b (> a b)))" +
          "(var SIZE 20)" +
          "(var a (array dim SIZE))" +
          "(apply fill  a)" +
          "(print (apply extreme a comp1))" +  // find the min
          "(print (apply extreme a comp2))" +  // find the max
          "a)",


        "(prog" +
          "(fun fill (lambda a" +
            "(begin" +
              "(for (var i 0) (< i (sizeOf a)) (++ i)" +
                "(set (index a i) (rand 0 100)))" +
              "a)))" +  // return value for fill()
          "(fun sort (lambda a comp" +  // a "generic" bubble sort function
            "(begin" +
              "(var done false)" +
              "(while (! done)" +
                "(begin" +
                  "(set done true)" +
                  "(for (var i 0) (< i (- (sizeOf a) 1)) (++ i)" +
                    "(if (apply comp (index a (+ i 1)) (index a i))" +
                      "(begin" +
                        "(set done false)" +
                        "(var temp (index a i))" +
                        "(set (index a i) (index a (+ i 1)))" +
                        "(set (index a (+ i 1)) temp))" +
                      "0))))" +
              "a ) ) ) " +  // return value for sort()
          "(fun comp1 (lambda a b (< a b)))" +
          "(fun comp2 (lambda a b (> a b)))" +
          "(var SIZE 10)" +
          "(var a (array dim SIZE))" +
          "(print (apply fill  a))" +
          "(print (apply sort a comp1))" +  // sort ascending
          "(print (apply sort a comp2))" +  // sort descending
          "a)"
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
               String baseName = String.format("Language_7.%02d", i);
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
