// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//

#include "Eval_Expr_Tree.h"
#include "Add_Expr_Node.h"
#include "Subtract_Expr_Node.h"
#include "Num_Expr_Node.h"
#include "Multiply_Expr_Node.h"
#include "Division_Expr_Node.h"
#include "Modulus_Expr_Node.h"

Eval_Expr_Tree::Eval_Expr_Tree(void)
{
	//default constructor
}
Eval_Expr_Tree::~Eval_Expr_Tree(void)
{
	//destructor
}

void Eval_Expr_Tree::Visit_Add_Node (Add_Expr_Node & node)
{
	//visit left node, visit right node, then do addition
	//visit two other nodes
	
	node.left_leaf->accept (*this);
	node.right_leaf->accept(*this);
		
}
void Eval_Expr_Tree::Visit_Subtract_Node(Subtract_Expr_Node & node)
{
  // COMMENT: You are not using the visitor pattern correctly.
  // Instead, you have mixed the visitor with the composite version
  // of evaluate. You are to visit the left and right node via the
  // accept method to correctly implement the visitor pattern.
  // (e.g., left->accept (*this))
  
//RESPONSE: Instead of calling the eval method, I call the accept method that then visits the node
  
	//visit left node, visit right node, then do subtraction
	node.left_leaf->accept (*this);
	node.right_leaf->accept(*this);
		
}
void Eval_Expr_Tree::Visit_Number_Node(Num_Expr_Node & node)
{
  // COMMENT: You are not using the visitor pattern correctly.
  // Instead, you have mixed the visitor with the composite version
  // of evaluate. You are to visit the left and right node via the
  // accept method to correctly implement the visitor pattern.
  // (e.g., left->accept (*this))
  
  //RESPONSE: Instead of calling the eval method, I call the accept method that then visits the node
	//return number
	
	//node.left_leaf->accept (*this);
	//node.right_leaf->accept(*this);
	
}
void Eval_Expr_Tree::Visit_Multiply_Node(Multiply_Expr_Node & node)
{
  // COMMENT: You are not using the visitor pattern correctly.
  // Instead, you have mixed the visitor with the composite version
  // of evaluate. You are to visit the left and right node via the
  // accept method to correctly implement the visitor pattern.
  // (e.g., left->accept (*this))
  
  //RESPONSE: Instead of calling the eval method, I call the accept method that then visits the nodes
  
  
  
	//visit left node, visit right node, then do multiplication
	node.left_leaf->accept (*this);
	node.right_leaf->accept(*this);
	
}
void Eval_Expr_Tree::Visit_Division_Node(Division_Expr_Node & node)
{
  // COMMENT: You are not using the visitor pattern correctly.
  // Instead, you have mixed the visitor with the composite version
  // of evaluate. You are to visit the left and right node via the
  // accept method to correctly implement the visitor pattern.
  // (e.g., left->accept (*this))
  
  //RESPONSE: Instead of calling the eval method, I call the accept method that then visits the node
  
	//visit left node, visit right node, then do multiplication
	node.left_leaf->accept (*this);
	node.right_leaf->accept(*this);
	
}
void Eval_Expr_Tree::Visit_Modulus_Node(Modulus_Expr_Node & node)
{
  // COMMENT: You are not using the visitor pattern correctly.
  // Instead, you have mixed the visitor with the composite version
  // of evaluate. You are to visit the left and right node via the
  // accept method to correctly implement the visitor pattern.
  // (e.g., left->accept (*this))
  
  //RESPONSE: Instead of calling the eval method, I call the accept method that then visits the node
  
	//visit left node, visit right node, then do modulus
	node.left_leaf->accept (*this);
	node.right_leaf->accept(*this);
	
}

int Eval_Expr_Tree::result (void) const
{
	//returns result of evaluation
	return this->result_;
	
}
