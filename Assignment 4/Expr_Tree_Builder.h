// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//


#ifndef _EXPR_TREE_BUILDER
#define _EXPR_TREE_BUILDER

#include "Expr_Builder.h"
#include "Stack.h"

class Expr_Tree_Builder : public Expr_Builder
{
	public:
		Expr_Tree_Builder(void);
		virtual ~Expr_Tree_Builder(void);
		
		//starts a new expression
		virtual void start_expression (void);
		
		//methods for building types of nodes
		virtual void build_number (int n);
		virtual void build_add_operator (void);
		virtual void build_subtract_operator(void);
		virtual void build_multiply_operator(void);
		virtual void build_division_operator(void) ;
		virtual void build_modulus_operator(void);
		virtual void build_left_parenthesis(void);
		virtual void build_right_parenthesis(void);
				
		//get current expression
		virtual Expr_Node * get_expression (void);
		
	private:
		//current state of expression tree
		Expr_Node * tree_;
		
		//subexpression of tree that will be pushed on stack
		Expr_Node * sub_;
		
		//create stack to put the operands when doing build process
		Stack <Expr_Node *> temp;
		Stack<Expr_Node *> sub_expression;
	};
#endif