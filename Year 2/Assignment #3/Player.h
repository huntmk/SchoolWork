// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk

#include <iostream>
#include <string>

//create instance of class
class Player
{
	//attributes of player
	private:
		std::string fName;
		std::string lName;
		int jerseyNum;
	 
	
	public:
		//Default (void) Constructor
		Player();
		// add player to team
		void addPlayer();
		// print players on the team
		void showPlayer();
		//prints menu when called
		void printMenu();
};
		