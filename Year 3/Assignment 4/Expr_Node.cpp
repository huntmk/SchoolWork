// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#include "Expr_Node.h"
Expr_Node::Expr_Node(void):
right_leaf(nullptr),
left_leaf(nullptr)
{
	//initialize leaves to nullptr
}

Expr_Node::~Expr_Node(void)
{
	delete right_leaf;
	delete left_leaf;
}