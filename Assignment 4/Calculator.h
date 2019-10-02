// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//Calculator Class

//header

#include <string>
#include <iostream>
#include <sstream>
#include <cctype>

#ifndef _CALCULATOR_H
#define _CALCULATOR_H

#include "Expr_Tree_Builder.h"
#include "Eval_Expr_Tree.h"
#include "Expr_Node.h"

class Calculator 
{
	public:
		Calculator (Expr_Tree_Builder & builder, Eval_Expr_Tree & visitor);
		~Calculator (void);
	
		//converts postfix to infix.
		void build_expression(const std::string & infix);
	
		//evaluate postfix
		void postfix_eval();
	
	private:
		Expr_Tree_Builder & build_;
		Eval_Expr_Tree & visitor_;
	
};

#endif