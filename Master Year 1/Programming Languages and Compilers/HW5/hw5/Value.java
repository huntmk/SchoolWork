/**
   This class is a kind of "tagged union". Each instance of this class holds
   a value for our simple language. But our language has three kinds of values,
   ints, booleans, and functions. So each instance of this class holds both an
   int value, a boolean value, and a Tree value. The "tag" field is used to
   determine which of the three values that an instance holds, the int, boolean,
   or Tree, is valid.

   See also
      http://en.wikipedia.org/wiki/Tagged_union
*/

public class Value
{
   public String tag;     // either "int" or "bool" or "lambda"
   public int valueI = 0;
   public boolean valueB = false;
   public Tree valueL = null;

   public static final String INT_TAG = "int";
   public static final String BOOL_TAG = "bool";
   public static final String LAMBDA_TAG = "lambda";

   /**
      Construct a Value object that holds an int value.
   */
   public Value(int value)
   {
      this.tag = INT_TAG;
      this.valueI = value;
   }

   /**
      Construct a Value object that holds a boolean value.
   */
   public Value(boolean value)
   {
      this.tag = BOOL_TAG;
      this.valueB = value;
   }

   /**
      Construct a Value object that holds a "lambda value".
   */
   public Value(Tree value)
   {
      this.tag = LAMBDA_TAG;
      this.valueL = value;
   }

   public String toString()
   {
      return "[tag->" + tag
                      + ", valueI->" + valueI
                      + ", valueB->" + valueB
                      + ", valueL->" + valueL
                      + "]";
   }


   public String toSimpleString()
   {
      String result = "";

      if ( tag.equals(INT_TAG) )
      {
         result += valueI;
      }
      else if ( tag.equals(BOOL_TAG) )
      {
         result += valueB;
      }
      else if ( tag.equals(LAMBDA_TAG) )
      {
         result += valueL;
      }
      else  // bad tag (shouldn't get here)
      {
         result += "[tag->" + tag
                            + ", valueI->" + valueI
                            + ", valueB->" + valueB
                            + ", valueL->" + valueL
                            + "]";
      }

      return result;
   }
}
