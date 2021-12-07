/*
   Course: CS 51530
   Name: Marcellus Hunt
   Email: mkhunt@pnw.edu
   Assignment: 4
*/

/**

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Language_7a
{
   public static void main(String[] args) throws IOException
   {
      // IMPORTANT: Set this to 0 or 1 depending on whether you need
      // to see all of the interpreter's debugging information.
      Evaluate_7a.DEBUG = 1;

      if (args.length > 1)
      {
         System.out.println("Usage: Language_7a <script>");
      }
      else if (args.length == 1)
      {
         runFile(args[0]);
      }
      else
      {
         runPrompt();
      }
   }


   private static void runFile(String path) throws IOException
   {
      byte[] bytes = Files.readAllBytes(Paths.get(path));

      String source = new String(bytes, Charset.defaultCharset());

      // Tokenize, parse, and evaluate the source file.
      try
      {
         Tokenizer tokens = new Tokenizer(source);

         // Print the token list.
         //System.out.println(tokens);

         Tree ast = ParseTree.buildTree(tokens);

         // Print the AST as an S-expression
         //System.out.println( ast + "\n" );

         // Pretty-print the abstract syntax tree.
         //System.out.println( PrettyPrinter.prettyPrint( ast ) + "\n" );

         // Print the infix version of the expression.
         System.out.println( AST2infix_7a.ast2infix( ast ) + "\n" );

         // Create dot and png files from the AST.
         if (Evaluate_7a.DEBUG > 0)
         try
         {
            // Create the (empty) dot file.
            String baseName = path;
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
         catch (IOException e)
         {
            System.out.println( e );
         }

         // Evaluate the AST.
         Value value = Evaluate_7a.eval( ast );

         System.out.println("result = " + value);
      }
      catch (TokenizeException e)
      {
         System.out.println(e);
      }
      catch (ParseException e)
      {
         System.out.println(e);
      }
      catch (EvalException e)
      {
         System.out.println(e);
      }
   }


   private static void runPrompt() throws IOException
   {
      InputStreamReader input = new InputStreamReader(System.in);
      BufferedReader reader = new BufferedReader(input);

      Evaluate_7a.globalEnv = new Environment();

      System.out.println("Language 7a.");

      // repl (read, eval, print loop)
      for (int i = 1;  ; i++)
      {
         System.out.print("> ");

         // read
         String source = reader.readLine();

         try
         {
            Tokenizer tokens = new Tokenizer(source);
            if ( ! tokens.hasToken() )
            {
               continue; // skip over empty string
            }
            Tree ast = ParseTree.buildTree(tokens);

            if ( tokens.hasToken() )  // there shouldn't be any more tokens
               throw new ParseException("unexpected input: "
                                      + tokens.peekToken().lexeme
                                      + " at line " + tokens.peekToken().line
                                      + ", position " + tokens.peekToken().position
                                      //+ "\n" + tokens
                                      );

            // Create dot and png files from the AST.
            if (Evaluate_7a.DEBUG > 0)
            try
            {
               // Create the (empty) dot file.
               String baseName = String.format("repl.%02d", i);
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
            catch (IOException e)
            {
               System.out.println( e );
            }

            // Evaluate the AST using the evaluater's global Environment.
            if (ast.getElement().equals("fun"))
            {
               Evaluate_7a.handleFun(ast, Evaluate_7a.globalEnv);
            }
            else
            {
               final Value value = Evaluate_7a.evaluateExp(ast, Evaluate_7a.globalEnv);
               // print

               // The "reverse prompt" idea comes from
               // the JavaScript repl in Chrome DevTools.
               System.out.println( "< " + value );
            }
         }
         catch (TokenizeException e)
         {
            System.out.println(e);
         }
         catch (ParseException e)
         {
            System.out.println(e);
         }
         catch (EvalException e)
         {
            System.out.println(e);
         }
      }
   }
}
