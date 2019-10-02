#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include "CrossReference.h"
#include "BinaryTree.h"

//Cross Reference.cpp


BinaryTree *tree = new BinaryTree();


//prints out sorted strings
void CrossReference::printTable()
{
	tree->sortTree(tree->mainRoot);
}

//parses the text file being read and inserts into binary tree
void CrossReference::parseWords()
{
	//keep track of line number;
	int lineNum = 0;

	//used to keep track of what line number is currently being looked at
	std::string line;
	
	std::string word;
	std::fstream file;
	//set word to only 10 characters
	

	//read data of text file
	std::ifstream inputFile("example.txt");
	
	

	//READ LINE
	if (inputFile.is_open())
	{
		
		//Will need while loop to continuously read multiple lines, research on this later.
		while (std::getline(inputFile,line))
		{
			//keeps track of line
			lineNum++;
			std::stringstream ssWord (line);
			//TOKENIZE LINE
			
			while ( ssWord>> word)
			{
					
					
					std:: string firstTen = word.substr(0, 10);
					std:: string firstChar = firstTen.substr(0,1);
					std:: string lastChar = firstTen.substr(firstTen.size()-1);
					
					if (firstChar == "1" || firstChar == "2" || firstChar == "3" || firstChar == "4" || firstChar == "5" || firstChar == "6" || firstChar == "7" || firstChar == "8" || firstChar == "9" || firstChar == "0" )
					{
						firstTen.erase(0,1);
					}
					
					if(lastChar == "."|| lastChar == "!" || lastChar == ","|| lastChar == "#")
					{
						//deletes string
						firstTen.erase(firstTen.size()-1);
					}
					
					if (!firstTen.empty())
						tree->insert(tree->mainRoot,firstTen,lineNum);
					
					
					
			}
				
			
			
			
		}
		inputFile.close();
		
	
	}
	else
	{
		std::cout << "Unable to open file" << std::endl;
	}
	
}






