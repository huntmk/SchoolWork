//Binary Tree.cpp
//
#include <iostream>
#include <string>
#include "BinaryTree.h"


BinaryTree::BinaryTree() {
	mainRoot = NULL;
}

 node* BinaryTree::insert(node * &root,std::string word, int line)
{
	
	//create new node
	node * temp = new node;
	//node * root;
	temp -> key = word;
	temp -> num.push_back(line);
	
	temp->left = temp->right = NULL;
	
	
	if (root == NULL)
		//create new binary node
	
		root = temp;
		
		
	// if keys are equal then
	
	if (root ->key == temp-> key)
	{
		if (root->num != temp->num)
			root->num.push_back(line);
	}
	
	// if word is larger
	else if (root-> key < temp->key){
		//insert that node
		//if it is not null, insert
		if(root->right){
			
			root -> right  = insert(root->right,word, line);
		}
		
		else{

			root-> right = temp;
		}
		
		
	}
	//if word is smaller
	else if (root-> key > temp->key){
		//insert that node
		if (root->left){
			
			root->left = insert(root->left,word, line);
		}
		else{
			
			root->left = temp;
		}
		
	}
	
	return root;
	
}



void BinaryTree::sortTree(node *mainRoot)
{
	if (mainRoot != NULL)
		{
			//print out left subtree, which is smaller elements then print out the root followed by the right side which hold larger elements
			sortTree(mainRoot ->left);
			std::cout << mainRoot -> key ;
			std::cout << '\t';
			std::vector <int>:: iterator i;
			for (i = mainRoot->num.begin(); i != mainRoot->num.end(); ++i)
				std::cout << *i << '\t';
 
			std::cout << std::endl;
			
		
			sortTree(mainRoot -> right);
		}
}




