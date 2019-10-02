//Expr_Tree class

// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

#include "Expr_Tree.h"
Expr_Tree::Expr_Tree(void):
left_(nullptr),
right_(nullptr)
{
	
}
Expr_Tree::~Expr_Tree(void)
{
	delete left_;
	delete right_;
}
		
		