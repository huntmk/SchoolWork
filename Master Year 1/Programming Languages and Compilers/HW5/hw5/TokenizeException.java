/**
   This is thrown by Tokenizer.java when it detects an
   unrecoverable tokenizing error in an expression.
 */

public class TokenizeException extends java.lang.Exception
{
   private static final long serialVersionUID = 0;

   public TokenizeException()
   {
      super();
   }

   public TokenizeException(String errMessage)
   {
      super(errMessage);
   }
}
