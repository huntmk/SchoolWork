// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//



#ifndef _BINARY_EXPR_NODE
#define _BINARY_EXPR_NODE

#include "Expr_Node.h"

class Binary_Expr_Node : public Expr_Node
{
	public:
		Binary_Expr_Node(void);
		virtual ~Binary_Expr_Node(void);
		
		virtual void accept (Expr_Node_Visitor & v);
		
		//template method
		virtual int eval(void);
		
		//this method does the operation depending on the operator
		virtual int calculate(int,int) = 0;
		
	protected:
		//the binary has a right and left operation
		Expr_Node * right_;
		Expr_Node * left_;
};
#endif