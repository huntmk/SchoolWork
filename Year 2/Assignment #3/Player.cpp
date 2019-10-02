// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk

#include "Player.h"


Player::Player() {

}
//showPlayer function
void Player::showPlayer()
{
	//prints player in this format
	std::cout << this->jerseyNum;
	std::cout << (")" + this->fName + " " + this->lName + " ");
	std::cout << std::endl;

}
//crate addPlayer function
void Player::addPlayer()
{
		// ask user to enter attributes for Player being created
		std::cout << "Please enter a first name: ";
		std::cin >> this->fName;
		std::cout << std::endl;
		std::cout << "Please enter a last name: ";
		std::cin >> this->lName;
		std::cout << std::endl;
		std::cout << "Please enter a number (1-99)";
		std::cin >>this->jerseyNum;
	}


//create printMenu function
void Player::printMenu()
{
	//show user what options they have in this program
	std::cout << "1) Add New Player" << std::endl;
	std::cout << "2) View Player(s)" << std::endl;
	std::cout << "3) End Program" << std::endl;
	std::cout << std::endl;
	std::cout << "Please enter your selection: ";
}