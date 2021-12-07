/*
   This program parses and evaluates strings from Language_7a.
*/

public class Language_7a_Runtime_Errors
{
   public static void main(String[] args)
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you need
      // to see all of the interpreter's debugging information.
      Evaluate_7a.DEBUG = 0;

      String[] programs = // these are all one-line programs
      {
        "(array dim)",
        "(array dim 0)",
        "(array dim -1)",
        "(begin (var x false) (array dim x))",
        "(array dim 1 2 3)",
        "(index (array dim 5) 5)",
        "(index (array dim 5) -1)",
        "(index (array dim 5) 1 2)",
        "(index (array dim 5))",
        "(index 10 1)",
        "(set (index (array dim 5) 5) 0)",
        "(set (index (array dim 2) 0) (index (array dim 5) 5))",
        "(set 0 1)",
        "(set x 1)",
        "(sizeOf 0)",
        "(sizeOf x)",
        "(sizeOf (array dim 3) 4)",
        "(begin (var x false) (sizeOf x))",
        "(sizeOf (array dim 5) 0)",
        "(rand 1)",
        "(rand 1 2 3)",
        "(rand 10 1)",
        "(rand 1 1)",
        "(rand false 1)",
        "(rand 0 true)",
        "(begin (var x false) (rand x 10))"
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
            System.out.println( ast + "\n" );

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
