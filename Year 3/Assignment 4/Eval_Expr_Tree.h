// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//

#ifndef _EVAL_EXPR_TREE
#define _EVAL_EXPR_TREE

#include "Expr_Node_Visitor.h"

class Eval_Expr_Tree : public Expr_Node_Visitor 
{
	public:
		Eval_Expr_Tree(void);
		virtual ~Eval_Expr_Tree(void);
		
		//methods for visiting nodes
		virtual void Visit_Add_Node (Add_Expr_Node & node);
		virtual void Visit_Subtract_Node(Subtract_Expr_Node & node);
		virtual void Visit_Number_Node(Num_Expr_Node & node);
		virtual void Visit_Multiply_Node(Multiply_Expr_Node & node);
		virtual void Visit_Division_Node(Division_Expr_Node & node);
		virtual void Visit_Modulus_Node(Modulus_Expr_Node & node);
		
		int result (void) const;
		
	private:
		int result_;
		//other state for calculating result		
};
#endif
