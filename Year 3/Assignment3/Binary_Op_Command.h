// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Expr_Command.h"
#ifndef _BINARY_OP_COMMAND_H
#define _BINARY_OP_COMMAND_H

class Binary_Op_Command : public Expr_Command {


public:
	Binary_Op_Command(Stack <int>  & s);
	
	~Binary_Op_Command(void);
	
	//algorithm used to operate on the numbers using the stack
	virtual void execute (void);
	
	//evaluation of ints based on type of operator
	virtual int evaluate (int, int ) const = 0;
	
	//precedence object
	virtual int prec(void)const  = 0;
	
private:
	Stack <int> & s_;
	
};

#endif