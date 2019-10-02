// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#include "Division_Expr_Node.h"

Division_Expr_Node::Division_Expr_Node(void)
{
	//constructor
}
Division_Expr_Node::~Division_Expr_Node(void)
{
	//destructor
}
		
int Division_Expr_Node::calculate(int num1, int num2)
{
	//return division of two numbers
	if (num1 == 0 || num2 == 0)
	{
		throw ("Can't divide by zero. ");
	}
	else
	{
		return num1 / num2;
	}
}

void Division_Expr_Node::accept (Expr_Node_Visitor & v)
{
	v.Visit_Division_Node (*this);
}


