/**
   This tokenizer is split into two parts.

   The first part is a private part that tokenizes
   the given input string into a list of tokens.

   The second part is a public part that lets a client
   program parse the list of tokens.
*/

import java.util.List;
import java.util.ArrayList;

class Tokenizer
{
   // tokenizer state
   private final String source;
   private final List<Token> tokens = new ArrayList<>();
   private int currentToken;

   // scanner state
   private int start = 0;
   private int current = 0;
   private int line = 1;
   private int startOfLine = current;

   /**
      This constructor has the private part of
      this file build the list of tokens from the
      given input string.
   */
   public Tokenizer(String source) throws TokenizeException
   {
      this.source = source;
      scanTokens();
      currentToken = 0;
   }

   /**
      Break up the input string into individual tokens.
   */
   private void scanTokens() throws TokenizeException
   {
      while (! isAtEnd())
      {
         // We are at the beginning of the next lexeme.
         start = current;
         scanToken(); // consume one token from the input string
      }
   }

   /**
      Consume one token and add it to the list of tokens.
   */
   private void scanToken() throws TokenizeException
   {
      char c = advance();
      switch (c)
      {
         // one character tokens
         case '(': addToken(); break;
         case ')': addToken(); break;
         case ',': addToken(); break;
         case ';': addToken(); break;
         case '*': addToken(); break;
         case '%': addToken(); break;
         case '^': addToken(); break;

         // one or two (and one three) character tokens
         case '|': match('|'); addToken(); break;
         case '&': match('&'); addToken(); break;
         case '=': match('='); addToken(); break;
         case '!': match('='); addToken(); break;
         case '<': match('='); match('>'); addToken(); break; // spaceship opeerator
         case '>': match('='); addToken(); break;

         // division (possible single line comment)
         case '/':
           if (match('/'))
           {
              // A comment goes until the end of the line.
              while (peek() != '\n' && !isAtEnd()) advance();
           }
           else
           {
              addToken();
           }
           break;

         // plus (possible positive integer or increment operator)
         case '+':
           if (isDigit(peek())) // positive integer
           {
              advance();
              integer();
           }
           else // addition operator or increment operator
           {
              match('+'); match('+'); addToken();
           }
           break;

         // minus (possible negative integer or decrement operator)
         case '-':
           if (isDigit(peek())) // negative integer
           {
              advance();
              integer();
           }
           else // subtraction operator or decrement operator
           {
              match('-'); match('-'); addToken();
           }
           break;

         // whitespace
         case ' ':
         case '\r':
         case '\t':
           // Ignore whitespace.
           break;
         case '\n':
           line++; // Count line numbers.
           startOfLine = current;
           break;

         default:
           if (isDigit(c)) // digit start
           {
              integer();
           }
           else if (isAlpha(c)) // identifier start
           {
              variable();
           }
           else // error
           {
              throw new TokenizeException("unexpected character: "
                                        + "'" + c + "'"
                                        + " at line " + line
                                        + ", position "
                                        + (start-startOfLine+1));
           }
           break;
      }
   }

   private void addToken()
   {
      String text = source.substring(start, current);
      tokens.add(new Token(text, line, start - startOfLine + 1));
   }

   private char advance()
   {
      current++;
      return source.charAt(current - 1);
   }

   private boolean match(char expected)
   {
      if (isAtEnd()) return false;
      if (source.charAt(current) != expected) return false;
      current++;
      return true;
   }

   private char peek()
   {
      if (isAtEnd()) return '\0';
      return source.charAt(current);
   }

   private char peekNext()
   {
      if (current + 1 >= source.length()) return '\0';
      return source.charAt(current + 1);
   }

   private boolean isAtEnd()
   {
      return current >= source.length();
   }

   private void variable()
   {
      while (isAlphaNumeric(peek()))
         advance();

      addToken();
   }

   private void integer()
   {
      while (isDigit(peek()))
         advance();

      addToken();
   }

   private boolean isAlpha(char c)
   {
      return (c >= 'a' && c <= 'z') ||
             (c >= 'A' && c <= 'Z') ||
              c == '_';
   }

   private boolean isAlphaNumeric(char c)
   {
      return isAlpha(c) || isDigit(c);
   }

   private boolean isDigit(char c)
   {
      return c >= '0' && c <= '9';
   }




   /**
      This method consumes and returns the current token.
   */
   public Token nextToken() {
      return tokens.get(currentToken++);
   }

   /**
      This method both tests and consumes the current token.
   */
   public boolean match(String lexeme) {
      boolean match = lexeme.equals( tokens.get(currentToken).lexeme );
      if (match) {
         currentToken++; // consume the matched token
         return true;
      } else {
         return false; // don't consume the unmatched token
      }
   }

   /**
      This method allows you to look at the current
      token without consuming it.
   */
   public Token peekToken() {
      return tokens.get(currentToken);
   }

   /**
      This method allows you to look at the token after
      the current token (without consuming any token).
   */
   public Token peek2Token() {
      return tokens.get(currentToken + 1);
   }

   /**
      Returns false if all of the tokens have been consumed.
   */
   public boolean hasToken() {
      return currentToken < tokens.size();
   }

   @Override
   public String toString()
   {
      String result = "";
      for (Token t : tokens)
      {
         result += t + "\n";
      }
      return result;
   }
}
