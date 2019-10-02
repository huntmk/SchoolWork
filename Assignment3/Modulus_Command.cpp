// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Modulus_Command.h"

Modulus_Command::Modulus_Command(Stack <int> &s):
Binary_Op_Command(s),
precedence(3)
{
	//constructor
}

Modulus_Command::~Modulus_Command(void)
{
	//destructor
}

int Modulus_Command::evaluate (int n1, int n2) const
{
	//return result of multiplying integers
	return n1 % n2;
}

int Modulus_Command::prec (void) const
{
	return precedence;
}
