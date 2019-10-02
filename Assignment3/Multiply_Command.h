// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Binary_Op_Command.h"

#ifndef _MUTLIPLY_COMMAND_H
#define _MULTIPLY_COMMAND_H

//Multiplication Class
class Multiply_Command : public Binary_Op_Command{
	public:
	
		Multiply_Command(Stack <int> &s);
		
		~Multiply_Command(void);
		
		//evaluate multiplication between the integers
		virtual int evaluate (int, int) const;
		
		//returns precedence
		virtual int prec (void) const;
		
	private:
		int precedence;
		
};
		
#endif