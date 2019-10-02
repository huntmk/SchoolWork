// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Num_Command.h"

Num_Command::Num_Command (Stack <int> & s, int n):
s_(s),
n_(n)
{
	//constructor
}

Num_Command::~Num_Command(void)
{
	
}
void Num_Command::execute (void)
{
	//push number onto stack
	s_.push(this->n_);
}

int Num_Command::prec (void) const
{
	//number doesn't have precedence
	return 0;
	
}