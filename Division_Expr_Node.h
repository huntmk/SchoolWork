// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#ifndef _DIVISION_EXPR_NODE
#define _DIVISION_EXPR_NODE

#include "Binary_Expr_Node.h"
#include "Expr_Node_Visitor.h"

class Division_Expr_Node : public Binary_Expr_Node
{
	public:
		Division_Expr_Node(void);
		virtual ~Division_Expr_Node(void);
		
		//does division on operands
		virtual int calculate(int num1, int num2);
		
		//visits node
		virtual void accept (Expr_Node_Visitor & v);
					
};
#endif