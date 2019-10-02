// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Binary_Op_Command.h"

#ifndef _MODULUS_COMMAND_H
#define _MODULUS_COMMAND_H
//Modulus Class
class Modulus_Command : public Binary_Op_Command{
	public:
		
		Modulus_Command(Stack <int> &s);
		
		~Modulus_Command(void);
		
		//evaluate modulus operator between the integers
		virtual int evaluate (int, int) const;
		
		//returns precedence
		virtual int prec (void) const;
		
	private:
		int precedence;
		
};

#endif