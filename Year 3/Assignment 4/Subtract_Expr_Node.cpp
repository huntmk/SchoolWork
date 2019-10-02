// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#include "Subtract_Expr_Node.h"

Subtract_Expr_Node::Subtract_Expr_Node(void)
{
	//constructor
}
Subtract_Expr_Node::~Subtract_Expr_Node(void)
{
	//destructor
}
		
int Subtract_Expr_Node::calculate(int num1, int num2)
{
	//return subtraction of two numbers
	return num1 - num2;
}

void Subtract_Expr_Node::accept (Expr_Node_Visitor & v)
{
	v.Visit_Subtract_Node (*this);
}


