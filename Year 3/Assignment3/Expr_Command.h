#include "Stack.h"

#ifndef _EXPR_COMMAND
#define _EXPR_COMMAND

//Command Class
class Expr_Command {
	public:
		//interface used to execute 
		virtual void execute(void) = 0;
		
		//return precedence of value
		virtual int prec(void) const = 0;
};
#endif