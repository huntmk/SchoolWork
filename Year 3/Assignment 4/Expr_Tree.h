//Expr_Tree class

// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

#ifndef _EXPR_TREE
#define _EXPR_TREE

#include "Expr_Node.h"

class Expr_Tree
{
	public:
		Expr_Tree(void);
		~Expr_Tree(void);
		
		Expr_Node * left_;
		Expr_Node * right_;
		
};
#endif