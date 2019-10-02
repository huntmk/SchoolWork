// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//

#include "Expr_Tree_Builder.h"

// COMMENT The algorithm below seems to work, but will fail once
// you have an expression with parenthesis.

//RESPONSE: I create build parenthesis methods that will handle the parenthesis and also fixed the start expression so that it works properly with it

Expr_Tree_Builder::Expr_Tree_Builder(void)
{
	
}

Expr_Tree_Builder::~Expr_Tree_Builder(void)
{
	delete this->sub_;
	delete this->tree_;
}
		
//starts a new expression
void Expr_Tree_Builder::start_expression (void)
{
		
	//push current sub tree to sub tree stack
	while (this->sub_ != nullptr )
	{
		this->sub_expression.push(this->sub_);
	}
	
	//this creates a new sub tree
	Expr_Node * sub_tree;
	this->sub_ = sub_tree;
	
			
}
	
//methods for building types of nodes
void Expr_Tree_Builder::build_number (int n)
{
	Num_Expr_Node * newNum = new Num_Expr_Node(n);
	
	this->temp.push(newNum);
	
	//if there are two operands
	while (temp.size() == 2)
	{
		//check if left or right is null and put into the null one first (from left to right)
		if(this->sub_->left_leaf == nullptr)
		{
			this->sub_->left_leaf = temp.top();
			temp.pop();
		}
	
		else if(this->sub_->right_leaf == nullptr)
		{
			this->sub_->right_leaf = temp.top();
			temp.pop();
		}
	}
	
}

void Expr_Tree_Builder::build_add_operator (void)
{
	//build add node
	//if tree is not null then tree root is the binary node, and set its protected members to the elements of the tree leaves
	Add_Expr_Node * newAdd = new Add_Expr_Node();
	
	
	if(this->sub_ == nullptr)
	{
		this->sub_ = newAdd;
	}
	//else start new expression and make operator the root
	else
	{
		this->start_expression();
	}
	
}

void Expr_Tree_Builder::build_subtract_operator(void)
{
	//build subtract node
	//if tree is not null then tree root is the binary node, and set its protected members to the elements of the tree leaves
	
	Subtract_Expr_Node * newSubtract = new Subtract_Expr_Node();
	
	if(this->sub_ == nullptr)
	{
		this->sub_ = newSubtract;
	}
	//else start new expression and make operator the root
	else
	{
		this->start_expression();
			
	}
}

void Expr_Tree_Builder::build_multiply_operator(void)
{
	//build  multiply node
	//if tree is not null then tree root is the binary node, and set its protected members to the elements of the tree leaves
	Multiply_Expr_Node * newMultiply = new Multiply_Expr_Node();
	
	if (this->sub_ == nullptr)
	{
		this->sub_ = newMultiply;
		
	}
	//else start new expression and make operator the root
	else
	{
		this->start_expression();
		
	}
	
}
void Expr_Tree_Builder::build_division_operator(void)
{
	//build division node
	//if tree is not null then tree root is the binary node, and set its protected members to the elements of the tree leaves
	Division_Expr_Node * newDivision = new Division_Expr_Node();
	
	if (this->sub_ == nullptr)
	{
		this->sub_ = newDivision;
	}
	//else start new expression and make operator the root
	else
	{
		this->start_expression();
	}
	
}

void Expr_Tree_Builder::build_modulus_operator(void)
{
	//build modulus node
	//if tree is not null then tree root is the binary node, and set its protected members to the elements of the tree leaves
	Modulus_Expr_Node * newModulus = new Modulus_Expr_Node();
		
	if(this->sub_ == nullptr)
	{
		this->sub_ = newModulus;
	}
	//else start new expression and make operator the root
	else
	{
		this->start_expression();
	}
	
}

//build a left parenthesis
void Expr_Tree_Builder::build_left_parenthesis(void)
{
	//start_expression
		//if the stack is not empty, empty it and set the the whatever operand (if any) to one of the leaves and the leaf that contains nullptr, I will create a new root ptr_fun
		while (!temp.is_empty())
		{
			//check if left or right is null and put into the null one first (from left to right)
			if(this->sub_->left_leaf == nullptr)
			{
				this->tree_->left_leaf = temp.top();
				temp.pop();
			}
	
			else if(this->tree_->right_leaf == nullptr)
			{
				this->tree_->right_leaf = temp.top();
				temp.pop();
			}
		}
		
		this->start_expression();
	
}

//build a right parenthesis
void Expr_Tree_Builder::build_right_parenthesis(void)
{
	//once again I'm emptying the stack but this time I want to go back to the tree above that node.
		while (!this->temp.is_empty())
		{
			//check if left or right is null and put into the null one first (from left to right)
			if(this->tree_->left_leaf == nullptr)
			{
				this->tree_->left_leaf = temp.top();
				temp.pop();
			}
	
			else if(this->tree_->right_leaf == nullptr)
			{
				this->tree_->right_leaf = temp.top();
				temp.pop();
			}
		}
		//believe I'll need to pop the last sub expression off the stack to go back to my current expression
		
}

//get current expression
Expr_Node* Expr_Tree_Builder::get_expression (void)
{
	while(!this->sub_expression.is_empty())
	{
		
		if (this->tree_ == nullptr)
		{
			this->tree_ == this->sub_expression.top();
			this->sub_expression.pop();
		}
		
		//check if left or right is null and put into the null one first (from left to right)
		else if(this->tree_->right_leaf == nullptr)
		{
			this->tree_->right_leaf = this->sub_expression.top();
			this->sub_expression.pop();
		}
	
		else if(this->tree_->left_leaf == nullptr)
		{
			this->tree_->left_leaf = this->sub_expression.top();
			this->sub_expression.pop();
		}
	}		

	return this->tree_;
}
