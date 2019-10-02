// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Add_Command.h"

Add_Command::Add_Command (Stack <int> & s):
Binary_Op_Command (s),
precedence(2)
{
	//constructor
}

Add_Command::~Add_Command (void)
{
	//destructor	
}


int Add_Command::evaluate (int n1, int n2) const
{	
	
	//return the addition of the two integers
	return n1 + n2;
}

int Add_Command::prec (void) const
{
	return precedence;
}
