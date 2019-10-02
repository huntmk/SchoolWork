// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>

#include "Sort.h"
#include "InsertionSort.h"
#include "BubbleSort.h"


int num [50];

// use as a variable to close programs when the user chooses the option to
bool bothGoin = true;

//LOAD INTS
	void loadInts()
	{
		//line to read in file
		std::string line;
		std::stringstream ss;
		std::stringstream ss2;
		std::string stringnum;
		//read data.txt
		std::ifstream inputFile("data.txt");
			//if file is open
		if (inputFile.is_open())
		{
			ss.clear();
			ss.str("");
			int i = 0;
				//Read line
			std::getline(inputFile,line);
			ss.str(line);
				//Tokenize Line
			while(std::getline(ss,stringnum, ','))
			{	
				//Parse 
				ss2.clear();
				ss2.str("");
				ss2.str(stringnum);
				ss2>>num[i];	
				std::cout <<num[i]<< ",";
				i++;		
			}
			inputFile.close();
		}
		else
		{
			std::cout << "Unable to open file" << std::endl;
		}
	}
	
	//MENU 2 (sub menu)
	void menu2()
	{
		bool keepGoing = true;
		while (keepGoing)
		{
			//input for sub menu
			std::string subDecision;
			//print menu
			std::cout<<std::endl;
			std::cout << "1. Insertion Sort " << std::endl;
			std::cout << "2. Bubble Sort " << std::endl;
			std::cout << "3. Exit Program " << std::endl;
			std::cout << "Please enter your selection: ";
			std::cin >> subDecision;
			// if equals 1 then do insertion sort
			if (subDecision == "1")
			{
				//insertion object
				Sort * insert = new InsertionSort;
				
				//insert sort
				insert -> sort(num, 50);
				
				//prints out array
				for (int m = 0; m < 50; m++)
				{
					std::cout << num[m]<< ", ";
				}
				keepGoing = false;	
				delete insert;
				
			}
			//if equals 2 then do bubble sort
			else if (subDecision == "2")
			{
				//bubble object
				Sort * bubble = new BubbleSort;
				
				//bubble sort
				bubble -> sort(num, 50); 
				
				//prints out arrray
				for (int b = 0; b < 50; b++)
				{
					std::cout << num[b]<<", ";
				
				}
				keepGoing = false;
				
				delete bubble;
			}
				
			else if(subDecision == "3")
			{
				keepGoing = false;
				bothGoin = false;
			}				
		
		}
		
	}
	//MENU1
	void menu1()
	{
		bool keepGoing = true;
		
		//do loop
		//bool keepGoing = true;
		while (keepGoing || bothGoin)
		{
			//input for main menu
			std::string decision;
			//main menu
			std::cout << std::endl;
			std::cout << "1. Load Integers (From File) " << std::endl;
			std::cout << "2. Exit Program" << std::endl;
			std::cout << "Please enter your selection: ";
			std::cin >> decision;
			// if equals 1, load the integers and call menu 2 function
			if (decision == "1")
			{
				loadInts();
				menu2();
				keepGoing = false;
			}
			//if equals 2, end program
			else if(decision == "2")
			{
				keepGoing = false;
				bothGoin = false;
			}
				
		}
	}
int main()
{
	//Start Program
	menu1();
	
	
}
