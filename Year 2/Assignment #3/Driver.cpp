// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk

#include <iostream>
#include "Player.h"


int main()
{	
	
	std::string decision;
	//declare array of objects for the team
	Player * person = new Player[11];
	
	// Print menu and ask for input using string decision
	std::cout << "Welcome to our CSCI 240 Roster!" << std::endl;
	person->printMenu();
	std::cin >> decision;
	//i represents index
	int i = 0;
	//num represents how many times a Player has been called
	int num = 0;
	

			//while decision doesn't equal 3	
			while (decision != "3"){
				// if decision equals 1
				if (decision == "1") {
					//check if array of objects is full
					if(num >=11){
						std::cout << "Sorry you can only have 11 players on the team, Enter another optionn: " << std::endl;
						std::cin >> decision;
					}
					else{
				// add new player to the index
					person[i].addPlayer();
					//increment i and num to represent the index and num times a player has been added
					i++;
					num++;
					std::cout << std::endl;
					//ask for another input
					person[i].printMenu();
					std::cin >> decision;
					}
				}

				// if decision == 2
				else if (decision == "2") {
					// for every array spot, print players created by comparing to number of times a player has been created
					for (i = 0; i < num; i++) {
						//call function to show player
						person[i].showPlayer();
					}
					std::cout << std::endl;
					//ask for another input
					person[i].printMenu();
					std::cin >> decision;

				}

			// else ask for correct number
			else {
				std::cout << "Wrong input, enter correct option";
				std::cin >> decision;
				}
			
			
			}
			//destructor
			delete [] person;
			
			
}
	
