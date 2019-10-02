// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.
#include "Binary_Op_Command.h"

#ifndef _ADD_COMMAND_H
#define _ADD_COMMAND_H

class Add_Command: public Binary_Op_Command {
public: 

	Add_Command(Stack <int> & s);
	
	~Add_Command(void);
	
	//evaluates the addition of the integers popped
	virtual int evaluate (int , int) const;
	
	virtual int prec (void) const;
	
private:
	int precedence;
		
};
#endif