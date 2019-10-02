
#include "Calculator.h"

#include <iostream>
int main (int argc, char * argv [])
{


//unless infix says QUIT, then continue running
std::string infix;

std::cout << "Input expression: ";
std::getline (std::cin,infix);

while (infix != "QUIT")
{
		
	//create the calculator object
	Eval_Expr_Tree eval;
	Expr_Tree_Builder b;
	Calculator calc(b, eval);
	
	
	//catch any exceptions when running the program	
	try
	{
		calc.build_expression(infix);
		calc.postfix_eval();
	
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
