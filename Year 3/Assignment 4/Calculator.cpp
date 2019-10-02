// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//Calculator Class

//source file

#include "Calculator.h"


Calculator::Calculator(Expr_Tree_Builder & builder, Eval_Expr_Tree & visitor):
build_(builder),
visitor_(visitor)
{
	//constructor
}

Calculator::~Calculator()
{
	//destructor
}

void Calculator::postfix_eval()
{
	//Visit nodes
	Expr_Node * expr_tree = build_.get_expression();
	
	expr_tree->accept (visitor_);
	std::cout << "Visit. " << std::endl;
	int result = visitor_.result();
		
	//STDOUT
	std::cout << result << std::endl;
		
}

// COMMENT The name of this method is misleading since you are building
// the expression, and not convert it from infix to postfix.

//RESPONSE: Changed the name of the method from infix_to_postfix to 'build_expression'


void Calculator::build_expression(const std::string & infix)
{
	//create stream parser
	std::istringstream input(infix);
	
	//current token
	std::string token;
	
	//start expression
	build_.start_expression();
	
	//while the input is being read, 
	while (!input.eof())
	{
				
		//read each token from input
		input >> token;
		
			///if the token is a add operator 
			if (token == "+")
			{
				//build add operator
				this->build_.build_add_operator();
				std::cout << "Build Add. " << std::endl;
					
			}
			
			///if the token is a subtraction operator 
			else if (token == "-")
			{
				//build subtract operator
				this->build_.build_subtract_operator();
								
			}
			
			///if the token is a multiplication operator 
			else if (token == "*")
			{
				//build multiply operator
				this->build_.build_multiply_operator();
								
			}
			
			///if the token is a division operator 
			else if (token == "/")
			{
				//build division operator
				this->build_.build_division_operator();
				
			}
			
			///if the token is a modulus operator 
			else if (token == "%")
			{
				//build modulus operator
				this->build_.build_modulus_operator();
								
			}
			
			//else if the token is a left parenthesis
			else if (token == "(")
			{
				//call method to handle parentheses
				build_.build_left_parenthesis();
			}
			
			//else if token is right parenthesis
			else if (token == ")")
			{
				//call method to handle parentheses
				build_.build_right_parenthesis();
			}
			
			//if its a digit
			else if (std::isdigit(token[0]))
			{
				//build num operator
				int num = std::stoi(token);
				std::cout << "Build Num. " << std::endl;
				this->build_.build_number(num);
				
				
			}
			//else throw exception
			else 
			{
				throw("Invalid input. ");
			}
	
	}
		
}
