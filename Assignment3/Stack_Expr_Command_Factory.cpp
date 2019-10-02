// Honor Pledge:
//
// I pledge that I have neither given nor receieved any help
// on this assignment.

#include "Stack_Expr_Command_Factory.h"

Stack_Expr_Command_Factory::Stack_Expr_Command_Factory(Stack <int> stack):
stack_(stack)
{
	//default constructor
}


Stack_Expr_Command_Factory:: ~Stack_Expr_Command_Factory(void)
{
	//destructor
}

int Stack_Expr_Command_Factory:: answer (void)
{
	return stack_.top();
}

Num_Command * Stack_Expr_Command_Factory:: create_num_command (int num)
{
	//return number command object
	Num_Command * Num_Object = new Num_Command(stack_,num);
	return Num_Object;
	
}

Add_Command * Stack_Expr_Command_Factory:: create_add_command (void)
{
	//return add command object
	Add_Command * Add_Object = new Add_Command(stack_);
	return Add_Object;
	
}

Subtract_Command * Stack_Expr_Command_Factory::create_subtract_command (void)
{
	//return subtract command object
	Subtract_Command * Subtract_Object = new Subtract_Command(stack_);
	return Subtract_Object;
	
}

Multiply_Command * Stack_Expr_Command_Factory:: create_multiply_command (void)
{
	//return multiply command object
	Multiply_Command * Multiply_Object = new Multiply_Command(stack_);
	return Multiply_Object;
	
}

Division_Command * Stack_Expr_Command_Factory::create_division_command (void)
{
	//return division command object
	Division_Command * Division_Object = new Division_Command(stack_);
	return Division_Object;
}

Modulus_Command * Stack_Expr_Command_Factory::create_modulus_command (void)
{
	//return modulus command object
	Modulus_Command * Modulus_Object = new Modulus_Command(stack_);
	return Modulus_Object;
	
}

Parenthesis_Command * Stack_Expr_Command_Factory::create_parenthesis_command(void)
{
	//return parenthesis command object
	Parenthesis_Command * Parenthesis_Object = new Parenthesis_Command(stack_);
	return Parenthesis_Object;
}