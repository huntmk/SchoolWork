// Honor Pledge:
//
// I pledge that I have neither given nor receieved any help
// on this assignment.

#include "Add_Command.h"
#include "Subtract_Command.h"
#include "Multiply_Command.h"
#include "Division_Command.h"
#include "Modulus_Command.h"
#include "Num_Command.h"
#include "Parenthesis_Command.h"

#ifndef _EXPR_COMMAND_FACOTRY_H
#define _EXPR_COMMAND_FACOTRY_H

class Expr_Command_Factory
{
	public:
	
		virtual ~Expr_Command_Factory (void) = 0;
		
		//returns top of stack which holds the result of the operation
		virtual int answer(void) = 0;
		
		//create the commands needed to perform operations
		virtual Num_Command * create_num_command (int num) = 0;
		
		virtual Add_Command * create_add_command (void) = 0;
		
		virtual Subtract_Command * create_subtract_command (void) = 0;
		
		virtual Multiply_Command * create_multiply_command (void) = 0;
		
		virtual Division_Command * create_division_command (void) = 0;
		
		virtual Modulus_Command * create_modulus_command (void) = 0;
		
		virtual Parenthesis_Command * create_parenthesis_command (void) = 0;
		
};
#endif 