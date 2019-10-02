// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#ifndef _UNARY_EXPR_NODE
#define _UNARY_EXPR_NODE

#include "Expr_Node.h"
class Unary_Expr_Node : public Expr_Node
{
	public:
		Unanry_Expr_Node(void);
		virtual ~Unary_Expr_Node(void);
		
		virtual int eval(void);
		
	protected:
		Expr_Node * child_;
		
};
#endif