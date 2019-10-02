// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//

#ifndef _EXPR_NODE_VISITOR
#define _EXPR_NODE_VISITOR

#include "Expr_Node.h"

class Add_Expr_Node;
class Subtract_Expr_Node;
class Num_Expr_Node;
class Multiply_Expr_Node;
class Modulus_Expr_Node;
class Division_Expr_Node;

class Expr_Node_Visitor 
{
	public:
		virtual ~Expr_Node_Visitor(void);
		
		//methods for visiting nodes
		virtual void Visit_Add_Node (Add_Expr_Node & node) = 0;
		virtual void Visit_Subtract_Node(Subtract_Expr_Node & node)= 0;
		virtual void Visit_Number_Node(Num_Expr_Node & node) = 0;
		virtual void Visit_Multiply_Node(Multiply_Expr_Node & node) = 0;
		virtual void Visit_Modulus_Node(Modulus_Expr_Node & node) = 0;
		virtual void Visit_Division_Node (Division_Expr_Node & node) = 0;
		
		
};
#endif