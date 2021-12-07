/**

*/

class Token
{
   final String lexeme;   // the text of the token
   final int    line;     // line number
   final int    position; // starting position of this token

   Token(String lexeme, int line, int position)
   {
      this.lexeme = lexeme;
      this.line = line;
      this.position = position;
   }

   @Override
   public String toString()
   {
      return "\"" + lexeme + "\"" + " - Line " + line + ", Character " + position;
   }
}
