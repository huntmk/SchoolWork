/*
   Course: CS 51530
   Name: Marcellus Hunt
   Email: mkhunt@pnw.edu
   Assignment: 4
*/

/**
   This clas is a kind of "tagged union". Each instance of this class holds
   a value for our simple language. But our language has three kinds of values,
   ints, booleans, and functions. So each instance of this class holds both an
   int value, a boolean value, and a Tree value. The "tag" field is used to
   determine which of the three values that an instance holds, the int, boolean,
   or Tree, is valied.

   See also
      http://en.wikipedia.org/wiki/Tagged_union
*/

public class Value
{
   public String tag;     // either "int" or "bool" or "lambda"
   public int valueI = 0;
   public boolean valueB = false;
   public Tree valueL = null;
   public Value valueA [];

   public static final String INT_TAG = "int";
   public static final String BOOL_TAG = "bool";
   public static final String LAMBDA_TAG = "lambda";
   public static final String ARRAY_TAG = "array";

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
   public Value(Value [] val)
   {
      this.tag = ARRAY_TAG;
      this.valueA = val;
   }

   public String toString()
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
      else if (tag.equals(ARRAY_TAG))
      {
         result += "{";
         for (int i = 0; i < valueA.length; i++)
         {
            result += valueA[i] + " ";
         }
         result += "}";
      }
      else  // bad tag (shouldn't get here)
      {
         result += "[tag->" + tag
                            + ", valueI->" + valueI
                            + ", valueB->" + valueB
                            + ", valueL->" + valueL
                            + ", valueA->" + valueA
                            + "]";
      }

      return result;
   }
}
