// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
#ifndef _EXPR_NODE
#define _EXPR_NODE

class Expr_Node_Visitor;

//Command Class
class Expr_Node {
	public:
	
		Expr_Node(void);
		virtual ~Expr_Node(void);
		
		virtual int eval(void) = 0;
		
		//Used to traverse the tree
		virtual void accept (Expr_Node_Visitor & v) = 0;
		
		//every node has a right and left leaf
		Expr_Node * right_leaf;
		Expr_Node * left_leaf;
			
};
#endif