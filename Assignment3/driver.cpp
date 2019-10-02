// COMMENT: Instead of using C functions to implement parts of the
// calculator. It would be better suited to use a Wrapper Facade.

//RESPONSE: Made a calculator class that now has the infix_to_postfix method and also an evaluation function.

#include "Calculator.h"

// COMMENT This is an incomplete assignment.
//RESPONSE: My Program now correctly takes in the infix method and converts to postfix. Then evaluates it correctly.

int main (int argc, char * argv [])
{

Calculator calc;

Stack <int> result;
Stack_Expr_Command_Factory factory (result);


std::string infix;
//get input from STDIN
std::cout << "Input expression. " << std::endl;
std::getline (std::cin,infix);

//unless infix says QUIT, then continue running
while (infix != "QUIT")
{
	Stack <int> result;
	Stack_Expr_Command_Factory factory (result);
	Array <Expr_Command *> postfix;
	
	//send infix to infix_to_postfix method
	//calls functions to perform conversion and evaluate (catch and exceptions thrown)
	
	
	try
	{
		calc.infix_to_postfix (infix, factory,postfix,result);
	
	} 
	catch (const char* msg)
	{
		std::cerr << msg << std::endl;
		
	}
		
	
	std::cout << "output another expression. or QUIT. ";
	std::getline (std::cin,infix);
	

}
	

  return 0;
}
