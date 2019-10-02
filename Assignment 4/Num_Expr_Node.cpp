// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//

#include "Num_Expr_Node.h"
Num_Expr_Node::Num_Expr_Node(int n):
num(0)
{
	//constructor
	num = n;
}
Num_Expr_Node::~Num_Expr_Node(void)
{
	//constructor
}

int Num_Expr_Node::eval(void)
{
	//return the number passed in the parameter
	return this->num;
	
}

void Num_Expr_Node::accept (Expr_Node_Visitor & v)
{
	v.Visit_Number_Node (*this);
}

