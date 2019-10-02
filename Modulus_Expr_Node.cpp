// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#include "Modulus_Expr_Node.h"

Modulus_Expr_Node::Modulus_Expr_Node(void)
{
	//constructor
}
Modulus_Expr_Node::~Modulus_Expr_Node(void)
{
	//destructor
}
		
int Modulus_Expr_Node::calculate(int num1, int num2)
{
	//return multiplication of two numbers
	return num1 % num2;
}

void Modulus_Expr_Node::accept (Expr_Node_Visitor & v)
{
	v.Visit_Modulus_Node (*this);
}

