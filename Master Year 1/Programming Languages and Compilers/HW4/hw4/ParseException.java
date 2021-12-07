/**
   This is thrown by BuildTree.java when it detects an
   unrecoverable parsing error in an expression.
 */

public class ParseException extends java.lang.Exception
{
   private static final long serialVersionUID = 0;

   public ParseException()
   {
      super();
   }

   public ParseException(String errMessage)
   {
      super(errMessage);
   }
}
