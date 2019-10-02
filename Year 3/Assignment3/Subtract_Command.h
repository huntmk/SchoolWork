// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Binary_Op_Command.h"

#ifndef _SUBTRACT_COMMAND_H
#define _SUBTRACT_COMMAND_H

class Subtract_Command : public Binary_Op_Command{
	public:
	
		Subtract_Command(Stack <int> &s);
		
		~Subtract_Command(void);
		
		//does subtraction on the two ints
		virtual int evaluate (int, int) const;
		
		//returns precedence of subtraction
		virtual int prec(void) const;
	
	private:
		int precedence;
		
		
};
		
#endif