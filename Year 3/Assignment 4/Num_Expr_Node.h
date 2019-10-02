// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#ifndef _NUM_EXPR_NODE
#define _NUM_EXPR_NODE

#include "Expr_Node.h"
#include "Expr_Node_Visitor.h"

class Num_Expr_Node : public Expr_Node
{
	public:
		Num_Expr_Node(int n);
		virtual ~Num_Expr_Node(void);
		
		//returns the number
		virtual int eval(void);
		
		//visits the node
		virtual void accept (Expr_Node_Visitor & v);
	private:
		int num;
};
#endif