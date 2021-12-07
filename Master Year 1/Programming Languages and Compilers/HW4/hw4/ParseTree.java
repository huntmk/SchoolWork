/**
   A Tree is defined by the following grammar.

     Tree ::= '(' String Tree+ ')'
            | String

   where '(' and ')' are literals, and Tree+ means "one or more".

   The static method getTree() is essentially a recursive descent
   parser for this Tree langauge.
*/

public class ParseTree
{
   public static Tree buildTree(String expression) throws ParseException, TokenizeException
   {
      Tokenizer tokens = new Tokenizer(expression);

      return buildTree(tokens);
   }


   public static Tree buildTree(Tokenizer tokens) throws ParseException
   {
      Tree result = getTree(tokens); // parse the tokens

      if ( tokens.hasToken() ) // there shouldn't be any more tokens
         throw new ParseException("unexpected input: "
                                   + tokens.peekToken().lexeme
                                   + " at line " + tokens.peekToken().line
                                   + ", position " + tokens.peekToken().position
                                 //+ "\n" + tokens
                                   );
      return result;
   }


   private static Tree getTree(Tokenizer tokens) throws ParseException
   {
      Tree result;

      if ( ! tokens.hasToken() ) // there should be another token
         throw new ParseException("unexpected end of input: "
                                  //+ "\n" + tokens
                                  );
      Token token = tokens.nextToken(); // consume one token

      if ( token.lexeme.equals("(") ) // look for a parenthesized tree
      {
         if ( ! tokens.hasToken() ) // there should be another token
            throw new ParseException("unexpected end of input: "
                                     //+ "\n" + tokens
                                     );
         result = new Tree( tokens.nextToken().lexeme ); // consume the root of the tree

         result.addSubTree( getTree(tokens) ); // consume first sub tree

         if ( ! tokens.hasToken() )  // there should be another token
            throw new ParseException("unexpected end of input: "
                                     //+ "\n" + tokens
                                     );
         token = tokens.peekToken(); // one token look ahead

         while ( ! token.lexeme.equals(")") )
         {
            result.addSubTree( getTree(tokens) ); // consume the sub tree
            if ( ! tokens.hasToken() )  // there should be another token
               throw new ParseException("unexpected end of input: "
                                        //+ "\n" + tokens
                                        );
            token = tokens.peekToken(); // one token look ahead
         }
         tokens.match(")"); // consume the matching ")"
      }
      else // the tree must be just the root
      {
         result = new Tree(token.lexeme);
      }
      return result;
   }
}
