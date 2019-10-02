// Honor Pledge:
//
// I pledge that I have neither given nor receieved any help
// on this assignment.

#include "Expr_Command_Factory.h"

#ifndef _STACK_EXPR_COMMAND_FACTORY_
#define _STACK_EXPR_COMMAND_FACTORY_
class Stack_Expr_Command_Factory : public Expr_Command_Factory
{
	public:
		
		//default constructor
		Stack_Expr_Command_Factory(Stack <int> stack);
		
		//destructor
		~Stack_Expr_Command_Factory(void);
		
		//create function that returns answer from expression
		virtual int answer(void);
		
		//return number command object	
		virtual Num_Command * create_num_command (int num);
		
		//return add command object
		virtual Add_Command * create_add_command (void);
		
		//return subtract command object
		virtual Subtract_Command * create_subtract_command (void);
		
		//return multiplication command object
		virtual Multiply_Command * create_multiply_command (void);
		
		//return division command object
		virtual Division_Command * create_division_command (void);
		
		//return modulus command object
		virtual Modulus_Command * create_modulus_command (void);
		
		//return parenthesis command object
		virtual Parenthesis_Command * create_parenthesis_command(void);
		
	private:
		Stack <int> & stack_;
};

#endif