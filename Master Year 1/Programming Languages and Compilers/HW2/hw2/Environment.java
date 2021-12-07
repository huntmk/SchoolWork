/**
   A Environment object holds <variable,value> pairs.
*/
import java.util.ArrayList;

public class Environment
{
   private ArrayList<String> variables;
   private ArrayList<Value>  values;

   // Constructors
   public Environment()
   {
      variables = new ArrayList<String>();
      values    = new ArrayList<Value>();
   }

   public Environment(Environment env)
   {
      variables = new ArrayList<String>();
      values    = new ArrayList<Value>();
   }

   /**
      Add a <variable, value> pair to this environment object.
   */
   public void add(String variable, Value value)
   {
      variables.add(variable);
         values.add(value);
   }

   /**
      Look up variable in the environment and
      return its associated value.

      Returns null if variable is not found.
   */
   public Value lookUp(String variable)
   {
      int i;
      for (i = 0; i < variables.size(); i++)
         if ( variable.equals(variables.get(i)) )
            break;

      if ( i < variables.size() )
         return values.get(i);
      else
      {
         return null;  // variable cannot be found
      }
   }

   /**
      Look up variable in the environment.
      Return true if the variable is in the it,
      otherwise return false.
   */
   public boolean defined(String variable)
   {
      int i;
      for (i = 0; i < variables.size(); i++)
         if ( variable.equals(variables.get(i)) )
            break;

      if ( i < variables.size() )
         return true;
      else
      {
         return false;
      }
   }


   /**
      Update the value associated with variable in the environment.
      Return true if the update is succesfull,
      return false if variable is not found.
   */
   public boolean update(String variable, Value value)
   {
      int i;
      for (i = 0; i < variables.size(); i++)
         if ( variable.equals(variables.get(i)) )
            break;

      if ( i < variables.size() )
      {
         values.set(i, value);
         return true;
      }
      else
      {
         return false;
      }
   }


   /**
      Convert the contents of the environment into a string.
      This is mainly for debugging purposes.
   */
   public String toString()
   {
      String result = "";

      result += "[Global Environment";

      for (int i = 0; i < variables.size(); i++)
      {
         result += "\n " + variables.get(i) + " = " + values.get(i);
      }
      result += "]";

      return result;
   }
}
