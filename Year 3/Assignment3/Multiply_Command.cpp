// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Multiply_Command.h"


Multiply_Command::Multiply_Command(Stack <int> &s):
Binary_Op_Command(s),
precedence(3)
{
	
}

Multiply_Command::~ Multiply_Command(void)
{
	
}
int Multiply_Command::evaluate (int n1, int n2) const
{
	//return result of multiplying integers
	return n1 * n2;
}

int Multiply_Command::prec (void) const
{
	return precedence;
	
}
