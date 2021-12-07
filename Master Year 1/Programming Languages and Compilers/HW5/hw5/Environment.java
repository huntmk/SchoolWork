/**
   An Environment object holds <variable, value> pairs and
   it has a link to the rest of the objects in the environment
   chain. This link is used to look up "non-local" variable
   references.

   The Environment interface has methods for
   1) Adding a new <variable, value> pair to this environment object.
   2) Looking up a variable to see if it is in the Environment chain.
   3) Looking up a variable to see if it is in this Environment object.
   4) Looking up a variable's value from the variable's <variable, value> pair.
   5) Mutating the value part of a variable's <variable, value> pair.
   6) Determining if a variable is only defined globally.
   7) Removing a <variable, value> pair from this environment object.
*/
import java.util.ArrayList;

public class Environment
{
   private final ArrayList<String> variables;
   private final ArrayList<Value>  values;
   private final Environment nonLocalLink;  // used to look up "non-local" variables
   private final String label;              // used in debugging output

   // Constructors
   public Environment()
   {
      variables = new ArrayList<String>();
      values    = new ArrayList<Value>();
      nonLocalLink = null; // denotes the global environment object
      label = "Global";    // default label for global env object
   }

   public Environment(Environment env, String label)
   {
      variables = new ArrayList<String>();
      values    = new ArrayList<Value>();
      nonLocalLink = env;
      this.label = label;
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
      Look up variable in the environment chain.
      Return true if the variable is in the chain,
      otherwise return false.
   */
   public boolean defined(String variable)
   {
      return ( null != this.lookUp(variable) );
   }


   /**
      Look up variable in the environment chain and
      return its associated value.

      Return null if variable is not found.
   */
   public Value lookUp(String variable)
   {
      int i;
      for (i = 0; i < variables.size(); ++i)
         if ( variable.equals(variables.get(i)) )
            break;

      if ( i < variables.size() )
      {
         return values.get(i);
      }
      else
      {
         if ( null == nonLocalLink )
            return null; // variable cannot be found
         else // recursively search the rest of the environment chain
            return nonLocalLink.lookUp(variable);
      }
   }


   /**
      Look up variable in this environment object.
      Return true if the variable is in this object,
      otherwise return false.
   */
   public boolean definedLocal(String variable)
   {
      int i;
      for (i = 0; i < variables.size(); ++i)
         if ( variable.equals(variables.get(i)) )
            break;

      if ( i < variables.size() )
         return true;
      else
         return false;
   }


   /**
      Update the value associated with variable in the environment chain.
      Return true if the update is successful,
      return false if the variable is not found in the chain.
   */
   public boolean update(String variable, Value value)
   {
      int i;
      for (i = 0; i < variables.size(); ++i)
         if ( variable.equals(variables.get(i)) )
            break;

      if ( i < variables.size() )
      {
         values.set(i, value);
         return true;
      }
      else
      {
         if ( null == nonLocalLink )
            return false; // variable cannot be found
         else // recursively search the rest of the environment chain
            return nonLocalLink.update(variable, value);
      }
   }


   /**
      Return true if the variable is defined only in
      the global environment, otherwise return false.
   */
   public boolean definedGlobal(String variable)
   {
      if ( null == nonLocalLink )
         return definedLocal(variable);
      else
         return !definedLocal(variable)
              && nonLocalLink.definedGlobal(variable);
   }


   /**
      Remove a <variable, value> pair from this environment object.
   */
   public void remove(String variable)
   {
      if ( definedLocal(variable) )
      {
         int index = variables.indexOf(variable);
         variables.remove(index);
            values.remove(index);
      }
   }


   /**
      Convert the contents of the environment chain into a string.
      This is mainly for debugging purposes.
   */
   public String toString()
   {
      String result = "";

      // Convert the environment chain first.
      if ( null != nonLocalLink )
         result = nonLocalLink.toString() + "\n/\\\n||\n[" + label + " Environment";
      else
         result += "[" + label + " Environment";

       // Now convert this Environment object.
      for (int i = 0; i < variables.size(); ++i)
      {
         result += "\n[ " + variables.get(i) + " = " + values.get(i);
      }

      return result;
   }
}
