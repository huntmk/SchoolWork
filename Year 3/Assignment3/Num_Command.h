// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Expr_Command.h"

#ifndef _NUM_COMMAND_H
#define _NUM_COMMAND_H
class Num_Command: public Expr_Command {
	public:
		Num_Command (Stack <int> & s, int n);
		
		~Num_Command(void);
		
		//performs execution on number command
		virtual void execute (void);
		
		//returns precedence
		virtual int prec(void) const;
		
	private:
		Stack<int> & s_;
		int n_;

};

#endif