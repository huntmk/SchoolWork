// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//

#include "Unary_Expr_Node.h"
Unary_Expr_Node::Unary_Expr_Node(void)
{
	//default constructor
}
Unary_Expr_Node::~Unary_Expr_Node(void)
 {
	//destructor
 }
		
Unary_Expr_Node::int eval(void)
{
	//unary has only one child
	if(this->child_)
	{
		return this->child_->eval();
	}
}