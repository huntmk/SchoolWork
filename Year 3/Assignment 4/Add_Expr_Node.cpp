// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#include "Add_Expr_Node.h"

Add_Expr_Node::Add_Expr_Node(void)
{
	//constructor
}
Add_Expr_Node::~Add_Expr_Node(void)
{
	//destructor
}
		
int Add_Expr_Node::calculate(int num1, int num2)
{
	//return addition of two numbers
	return num1 + num2;
}

void Add_Expr_Node:: accept (Expr_Node_Visitor &v) 
{
	v.Visit_Add_Node (*this);
}

