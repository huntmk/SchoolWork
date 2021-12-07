/**
   An Environment object holds <variable,value> pairs and
   it has a link to the rest of the objects in the environment
   chain. This link is used to look up "non-local" variable
   references.

   The Environment interface has methods for
   1) Adding new <variable,value> pairs to this environment object.
   2) Looking up a variable to see if it is in the Environment chain.
   2) Looking up a variable to see if it is in this Environment object.
   3) Looking up a variable's value from the variable's <variable,value> pair.
   4) Mutating the value part of a varaible's <variable,value> pair.
*/
import java.util.ArrayList;

public class Environment
{
   private ArrayList<String> variables;
   private ArrayList<Value>  values;
   private Environment nonLocalLink;  // used to look up "non-local" variables
   private String label = "";         // used in debugging output


   // Constructors
   public Environment()
   {
      variables = new ArrayList<String>();
      values    = new ArrayList<Value>();
      nonLocalLink = null;
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
      for (i = 0; i < variables.size(); i++)
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
      for (i = 0; i < variables.size(); i++)
         if ( variable.equals(variables.get(i)) )
            break;

      if ( i < variables.size() )
         return true;
      else
         return false;
   }


   /**
      Update the value associated with variable in the environment chain.
      Return true if the update is succesfull,
      return false if the variable is not found in the chain.
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
         if ( null == nonLocalLink )
            return false; // variable cannot be found
         else // recursively search the rest of the environment chain
            return nonLocalLink.update(variable, value);
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
         result += "[Global Environment";

       // Now convert this Environment object.
      for (int i = 0; i < variables.size(); i++)
      {
         result += "\n[ " + variables.get(i) + " = " + values.get(i);
      }

      return result;
   }
}
