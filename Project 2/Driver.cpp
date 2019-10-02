//Driver.cpp
//Will execute the program

#include <iostream>
#include "CrossReference.h"
#include "BinaryTree.h"


int main()
{

	CrossReference * run = new CrossReference();
	std::cout << "Welcome to the cross reference index program! Lets begin" << std::endl;

	run->parseWords();
	run->printTable();
	
	return 0;
}
