// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#include "Multiply_Expr_Node.h"

Multiply_Expr_Node::Multiply_Expr_Node(void)
{
	//constructor
}
Multiply_Expr_Node::~Multiply_Expr_Node(void)
{
	//destructor
}
		
int Multiply_Expr_Node::calculate(int num1, int num2)
{
	//return multiplication of two numbers
	return num1 * num2;
}

void Multiply_Expr_Node::accept (Expr_Node_Visitor & v)
{
	v.Visit_Multiply_Node (*this);
}


