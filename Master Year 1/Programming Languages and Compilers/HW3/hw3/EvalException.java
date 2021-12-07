/**
   This is thrown by Evaluate.java when it detects an
   unrecoverable runtime problem with evaluating an expression.
 */

public class EvalException extends java.lang.Exception
{
   private static final long serialVersionUID = 0;

   public EvalException()
   {
      super();
   }

   public EvalException(String errMessage)
   {
      super(errMessage);
   }
}
