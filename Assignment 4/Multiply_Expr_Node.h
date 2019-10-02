// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//



#ifndef _MULTIPLY_EXPR_NODE
#define _MULTIPLY_EXPR_NODE

#include "Binary_Expr_Node.h"
#include "Expr_Node_Visitor.h"

class Multiply_Expr_Node : public Binary_Expr_Node
{
	public:
		Multiply_Expr_Node(void);
		virtual ~Multiply_Expr_Node(void);
		
		//does multiplication on operands
		virtual int calculate(int num1, int num2);
		
		//visits the node
		virtual void accept (Expr_Node_Visitor & v);
	
};
#endif