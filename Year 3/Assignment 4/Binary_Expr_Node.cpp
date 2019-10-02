 // Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//

#include "Binary_Expr_Node.h"
Binary_Expr_Node::Binary_Expr_Node(void):
right_(nullptr),
left_(nullptr)
{
	//default constructor
	this->right_leaf =  right_;
	this->left_leaf = left_;
}
Binary_Expr_Node::~Binary_Expr_Node(void)
{
  // COMMENT You are not deleting the child node here. So, you
  // have a memory leak.
  
  //RESPONSE: Delete the left and right children nodes
  
	//destructor
	delete right_;
	delete left_;
}

void Binary_Expr_Node::accept(Expr_Node_Visitor & v)
{
	
}
		
int Binary_Expr_Node::eval(void)
{
	//result holds the calculation of left and right evaluation and returns the operation on those numbers
	int result = this->calculate(left_leaf->eval(),right_leaf->eval());
	
	return result;
}
