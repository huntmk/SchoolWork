/**

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Language_7
{
   public static void main(String[] args) throws IOException
   {
      // IMPORTANT: Set these to 0 or 1 depending on whether you need to
      // see all of the analyzer's or interpreter's debugging information.
      StaticAnalysis.DEBUG = 1;
      Evaluate.DEBUG = 1;

      if (args.length > 1)
      {
         System.out.println("Usage: Language_7 <script>");
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
      final byte[] bytes = Files.readAllBytes(Paths.get(path));

      final String source = new String(bytes, Charset.defaultCharset());

      // Tokenize, parse, and evaluate the source file.
      try
      {
         final Tokenizer tokens = new Tokenizer(source);

         // Print the token list.
         //System.out.println(tokens);

         final Tree ast = ParseTree.buildTree(tokens);

         // Print the AST as an S-expression
         //System.out.println( ast + "\n" );

         // Pretty-print the abstract syntax tree.
         //System.out.println( PrettyPrinter.prettyPrint( ast ) + "\n" );

         // Print the infix version of the expression.
         System.out.println( AST2infix.ast2infix( ast ) + "\n" );

         // Create dot and png files from the AST.
         if (Evaluate.DEBUG > 0)
         try
         {
            // Create the (empty) dot file.
            final String baseName = path;
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
         catch (IOException e)
         {
            System.out.println( e );
         }

         // Analyze the AST.
         if (StaticAnalysis.analyze(ast))
         {
            // Evaluate the AST.
            final Value value = Evaluate.eval( ast );
            if (Evaluate.DEBUG > 0)
            {
               System.out.println("result = " + value);
            }
            else
            {
               System.out.println("result = " + value.toSimpleString());
            }
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


   private static void runPrompt() throws IOException
   {
      final InputStreamReader input = new InputStreamReader(System.in);
      final BufferedReader reader = new BufferedReader(input);

      Evaluate.globalEnv = new Environment();
      StaticAnalysis.globalEnv = new Environment(null, "Global Analysis");

      System.out.println("Language 7 with static analyzer.");

      // REPL (read, eval, print loop)
      for (int i = 1;; i++)
      {
         System.out.print("> ");

         // Read.
         final String source = reader.readLine();

         try
         {
            final Tokenizer tokens = new Tokenizer(source);
            if ( ! tokens.hasToken() )
            {
               continue; // skip over empty string
            }
            final Tree ast = ParseTree.buildTree(tokens);

            if ( tokens.hasToken() )  // there shouldn't be any more tokens
               throw new ParseException("unexpected input: "
                                      + tokens.peekToken().lexeme
                                      + " at line " + tokens.peekToken().line
                                      + ", position " + tokens.peekToken().position
                                      //+ "\n" + tokens
                                      );

            // Create dot and png files from the AST.
            if (Evaluate.DEBUG > 0)
            try
            {
               // Create the (empty) dot file.
               final String baseName = String.format("repl.%02d", i);
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
            catch (IOException e)
            {
               System.out.println( e );
            }

            // Analyze the AST.
            if ( ast.getElement().equals("fun") )
            {
               boolean part_1 = StaticAnalysis.analyzeFun(ast, StaticAnalysis.globalEnv);

               boolean part_2 = StaticAnalysis.analyzeLambda(ast.getSubTree(1), StaticAnalysis.globalEnv);

               if (part_1 && ! part_2)
               {
                  // function's lambda failed, so remove function name
                  StaticAnalysis.globalEnv.remove(ast.getSubTree(0).getElement());
               }

               if(! part_1 || ! part_2) continue;
            }
            else if ( ast.getElement().equals("var") )
            {
               boolean alreadyExists = StaticAnalysis.globalEnv.defined(ast.getSubTree(0).getElement());

               boolean ok = StaticAnalysis.analyzeExp(ast, StaticAnalysis.globalEnv);

               if (! alreadyExists && ! ok)
               {
                  // variable declaration failed, so remove variable name
                  StaticAnalysis.globalEnv.remove(ast.getSubTree(0).getElement());
               }

               if (! ok) continue;
            }
            else // any other expression
            {
               if (! StaticAnalysis.analyzeExp(ast, StaticAnalysis.globalEnv) )
               {
                  continue;
               }
            }

            // Evaluate the AST using the evaluator's global Environment.
            if (ast.getElement().equals("fun"))
            {
               Evaluate.handleFun(ast, Evaluate.globalEnv);
            }
            else
            {
               final Value value = Evaluate.evaluateExp(ast, Evaluate.globalEnv);
               // Print.
               if (Evaluate.DEBUG > 0)
               {
                  // The "reverse prompt" idea comes from
                  // the JavaScript repl in Chrome DevTools.
                  System.out.println( "< " + value );
               }
               else
               {
                  System.out.println( "< " + value.toSimpleString() );
               }
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
