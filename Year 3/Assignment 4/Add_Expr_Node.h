// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#ifndef _ADD_EXPR_NODE
#define _ADD_EXPR_NODE

#include "Binary_Expr_Node.h"
#include "Expr_Node_Visitor.h"

class Add_Expr_Node : public Binary_Expr_Node
{
	public:
		Add_Expr_Node(void);
		virtual ~Add_Expr_Node(void);
		
		//does calculation of the ints		
		virtual int calculate(int num1, int num2);
		
		//visits the add node
		virtual void accept (Expr_Node_Visitor & v);
	
};
#endif