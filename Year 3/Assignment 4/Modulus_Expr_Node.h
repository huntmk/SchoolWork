// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#ifndef _MODULUS_EXPR_NODE
#define _MODULUS_EXPR_NODE

#include "Binary_Expr_Node.h"
#include "Expr_Node_Visitor.h"

class Modulus_Expr_Node : public Binary_Expr_Node
{
	public:
		Modulus_Expr_Node(void);
		virtual ~Modulus_Expr_Node(void);
		
		//does modulus operation on the operands
		virtual int calculate(int num1, int num2);
		
		//visits the node
		virtual void accept (Expr_Node_Visitor & v);
	
};
#endif