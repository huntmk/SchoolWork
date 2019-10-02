// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#ifndef _SUBTRACT_EXPR_NODE
#define _SUBTRACT_EXPR_NODE


#include "Binary_Expr_Node.h"
#include "Expr_Node_Visitor.h"

class Subtract_Expr_Node : public Binary_Expr_Node
{
	public:
		Subtract_Expr_Node(void);
		virtual ~Subtract_Expr_Node(void);
		
		//does subtraction on operands
		virtual int calculate(int num1, int num2);
	
		//visits the node
		virtual void accept (Expr_Node_Visitor & v);
};
#endif