//BinaryTree.h
#ifndef BINARYTREE_H_
#define BINARYTREE_H_
#include <vector>

struct node
{
	//All elements of a node in the binary tree
	std::string key;
	std::vector<int> num;
	
	
	node * left;
	node * right;
	
	
};
class BinaryTree
{
public:
	BinaryTree();
	~BinaryTree(){};
	struct node * mainRoot;
	node* insert(struct node *&root ,std::string, int);
	void sortTree(node * mainRoot);
};

#endif/*BinaryTree*/