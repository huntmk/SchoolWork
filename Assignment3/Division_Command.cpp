// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Division_Command.h"

Division_Command::Division_Command(Stack <int> &s):
Binary_Op_Command(s),
precedence(3)
{
	//constructor
}

Division_Command::~Division_Command(void)
{
	//destructor 
}

int Division_Command::evaluate (int n1, int n2) const
{
  // COMMENT: You are not handling divide by zero.
  
  //RESPONSE: I will check if either of the ints are zero and if so I will throw an exception.
  
	if (n1 == 0 || n2 == 0)
	{
		throw ("Can't divide by 0. ");
		
	}
	else
		//return result of dividing integers
		return n1 / n2;
}

int Division_Command::prec (void) const
{
	return precedence;
}
