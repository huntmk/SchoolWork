/**
   This clas is a kind of "tagged union". Each instance of this class holds
   a value for our simple language. But our language has two kinds of values,
   ints and booleans. So each instance of this class holds both an int value
   and a boolean value. The "tag" field is used to determine which of the two
   values that an instance holds, the int or the boolean, is valied.

   See also
      http://en.wikipedia.org/wiki/Tagged_union
*/

public class Value
{
   public String tag;     // either "int" or "bool"
   public int valueI = 0;
   public boolean valueB = false;

   public static final String INT_TAG = "int";
   public static final String BOOL_TAG = "bool";

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

   public String toString()
   {
      return "[tag->" + tag + ", valueI->" + valueI + ", valueB->" + valueB + "]";
   }


   public String toSimpleString()
   {
      String result = "";

      if ( tag.equals(BOOL_TAG) )
      {
         result += valueB;
      }
      else if ( tag.equals(INT_TAG) )
      {
         result += valueI;
      }
      else // bad tag (shouldn't get here)
      {
         result += "[tag->" + tag + ", valueI->" + valueI + ", valueB->" + valueB + "]";
      }

      return result;
   }
}
