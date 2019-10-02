// Parenthesis class

// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Expr_Command.h"

#ifndef _PARENTEHSIS_COMMAND_H
#define _PARENTEHSIS_COMMAND_H

class Parenthesis_Command : public Expr_Command
{
	public:
		Parenthesis_Command(Stack <int > & s);
		
		//performs operation on parenthesis
		virtual void execute (void);
		
		//returns precedence
		virtual int prec(void) const;
	private:
		Stack <int> & s_;
	
};

#endif