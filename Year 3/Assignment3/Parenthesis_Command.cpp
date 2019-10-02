// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

#include "Parenthesis_Command.h"

Parenthesis_Command::Parenthesis_Command(Stack <int> & s):
s_(s)
{
	//constructor
}

void Parenthesis_Command::execute(void)
{
	//remove it from stack
	s_.pop();
	
}

int Parenthesis_Command::prec (void) const
{
	return 1;
}
