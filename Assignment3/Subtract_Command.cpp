// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Subtract_Command.h"

Subtract_Command:: Subtract_Command(Stack <int> &s):
Binary_Op_Command(s),
precedence(2)
{
	//constructor
}

Subtract_Command::~Subtract_Command(void)
{
	//destructor
}

int Subtract_Command::evaluate (int n1, int n2) const
{
	//return result of subtracting integers
	return n1 - n2;
}

int Subtract_Command::prec (void) const
{
	return precedence;
	
}
