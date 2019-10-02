// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//Calculator Class

//header
#include "Stack_Expr_Command_Factory.h"
#include "Expr_Command.h"
#include <cstring>
#include <iostream>
#include <sstream>

#ifndef _CALCULATOR_H
#define _CALCULATOR_H

class Calculator 
{
	public:
		Calculator (void);
		~Calculator (void);
	
	//converts postfix to infix.
		void infix_to_postfix(const std::string & infix, Expr_Command_Factory & factory, Array <Expr_Command *> & postfix, Stack <int> & result);
	
	//evaluate postfix
		void postfix_eval(Array <Expr_Command *> & postfix,Expr_Command_Factory & fact);
	
};

#endif