// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Binary_Op_Command.h"

#ifndef _DIVISION_COMMAND_H
#define _DIVISION_COMMAND_H
//Division Class
class Division_Command : public Binary_Op_Command{
	public:
		Division_Command (Stack <int> &s);
		
		~Division_Command(void);
		
		//evaluate division between the integers
		virtual int evaluate (int, int) const;
		
		//returns precedence of division operator
		virtual int prec (void) const;
		
	private:
		int precedence;
		
};
		
#endif
