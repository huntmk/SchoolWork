// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//Calculator Class

//source file

#include "Calculator.h"


Calculator::Calculator()
{
	//constructor
}

Calculator::~Calculator()
{
	//destructor
}

void Calculator::postfix_eval(Array <Expr_Command *> & postfix, Expr_Command_Factory & fact)
{
	//take contents of postfix and put evaluate using the Stack.
	//loop through array, and execute each command  in  array
	
	for (int i = 0; i <  postfix.size(); i++)
	{
		postfix[i]->execute();
	}
	
	//STDOUT
	std::cout << fact.answer() << std::endl;
}
void Calculator::infix_to_postfix(const std::string & infix, Expr_Command_Factory & factory, Array <Expr_Command *> & postfix, Stack <int> & result)
{
	//create stream parser
	std::istringstream input(infix);
	
	//current token
	std::string token;
	
	//create Command object
	Expr_Command * cmd = 0;
	
	Stack <Expr_Command *> temp;  //temporarily holds operators
	
	// used for indexing
	int i = 0;
	
	//while the input is being read, 
	while (!input.eof())
	{
		// COMMENT You are not handling parenthesis.
		//RESPONSE: I added statements that handle the parenthesis
		
		//read each token from input
		input >> token;
		
			///if the token is a add operator 
			if (token == "+")
			{
				//create add command
				cmd = factory.create_add_command ();
				
				// if temp has a command of higher precedence. then remove that first and add to postfix
				if(!temp.is_empty() && temp.top()->prec() >= cmd->prec() )
				{
					postfix.set(i,temp.top());
					temp.pop();
					i++;
					
				}
				
				//push command to stack
				temp.push(cmd);
				
			}
			
			///if the token is a subtraction operator 
			else if (token == "-")
			{
				//create command
				cmd = factory.create_subtract_command();
				
				// if temp has a command of higher precedence. then remove that first and add to postfix
				if(!temp.is_empty() && temp.top()->prec() >= cmd->prec() )
				{
					postfix.set(i,temp.top());
					temp.pop();
					i++;
					
				}
				temp.push(cmd);
				
			}
			
			///if the token is a multiplication operator 
			else if (token == "*")
			{
				//create command
				cmd = factory.create_multiply_command();
				
				// if temp has a command of higher precedence. then remove that first and add to postfix
				if(!temp.is_empty() && temp.top()->prec() >= cmd->prec() )
				{
					postfix.set(i,temp.top());
					temp.pop();
					i++;
					
				}
					temp.push(cmd);
				
			}
			
			///if the token is a division operator 
			else if (token == "/")
			{
				//create dlvision
				cmd = factory.create_division_command();
				
				// if temp has a command of higher precedence. then remove that first and add to postfix
				if(!temp.is_empty() && temp.top()->prec() >= cmd->prec() )
				{
					postfix.set(i,temp.top());
					temp.pop();
					i++;
					
				}
				temp.push(cmd);
				
			}
			
			///if the token is a modulus operator 
			else if (token == "%")
			{
				//create modulus command
				cmd = factory.create_modulus_command();
				
				// if temp has a command of higher precedence. then remove that first and add to postfix
				if(!temp.is_empty() && temp.top()->prec() >= cmd->prec() )
				{
					postfix.set(i,temp.top());
					temp.pop();
					i++;
					
				}
				temp.push(cmd);
				
			}
			
			//else if the token is a left parenthesis
			else if (token == "(")
			{
				// push onto stack  (create parenthesis command)
				cmd = factory.create_parenthesis_command();
				temp.push(cmd);
			}
			
			//else if token is right parenthesis
			else if (token == ")")
			{
				//while top of temp doesn't equal parenthesis command
				while(temp.top() != factory.create_parenthesis_command() && !temp.is_empty())
				{
					//remove objects from temp and add to 
					postfix.set(i, temp.top());
					temp.pop();
					std::cout << postfix.size() << std::endl;
					i++;
					
				}
						
			}
			
			//it must be a number command so add to postfix
			else
			{
				int num = std::stoi(token);
				
				cmd = factory.create_num_command(num);
				postfix.set(i,cmd);
				i++;
				
			}
	
	}
	
	//if temp is not empty return top element and add to postfix then pop
	//I  want this to be the last thing done after the last thing is read in.
	if (input.eof())
	{
		while (!temp.is_empty())
		{
			postfix.set(i,temp.top());
			temp.pop();
			i++;
		}
	}
	
	
	//call evaluation function
	
	postfix_eval(postfix, factory);
	
		
}
