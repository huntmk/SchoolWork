// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//

#ifndef _EXPR_BUILDER
#define _EXPR_BUILDER

#include "Num_Expr_Node.h"
#include "Add_Expr_Node.h"
#include "Subtract_Expr_Node.h"
#include "Multiply_Expr_Node.h"
#include "Division_Expr_Node.h"
#include "Modulus_Expr_Node.h"
#include "Expr_Node.h"
 
class Expr_Builder
{
	public:
		virtual ~Expr_Builder(void);
		
		//starts a new expression
		virtual void start_expression (void) = 0;
		
		//methods for building types of nodes
		virtual void build_number (int n) = 0;
		virtual void build_add_operator (void) = 0;
		virtual void build_subtract_operator(void) = 0;
		virtual void build_multiply_operator(void) = 0;
		virtual void build_division_operator(void) = 0;
		virtual void build_modulus_operator(void) = 0;
		virtual void build_left_parenthesis(void) = 0;
		virtual void build_right_parenthesis(void) = 0;
				
		//get current expression
		virtual Expr_Node * get_expression (void) = 0;
		
};
#endif