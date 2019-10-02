// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Binary_Op_Command.h"

Binary_Op_Command::Binary_Op_Command (Stack <int > & s):
s_(s)
{
	//constructor
}

Binary_Op_Command::~Binary_Op_Command(void)
{
	//destructor
}

void Binary_Op_Command::execute (void)
{
	//pop 2 values from stack
	int n2 = s_.top();
	s_.pop();
	int n1 = s_.top();
	
	//evaluate the result based on opeartor and push to stack
	int result = this->evaluate (n1,n2);
	s_.push (result);
	
}

