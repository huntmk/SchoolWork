// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
#include <iostream>
#include <stdlib.h> 
#include <ctime>
#include <iomanip>

#define GAME_LENGTH 50



void advancePlayerA(int* ptrPlayerA);
void advancePlayerB(int* ptrPlayerB);
void printPosition(int* ptrPlayerA, int* ptrPlayerB);


int main()
{
//needs fixing
//The first player that reaches space 50, should trigger a message to notify the player who won the game.

	srand(time(NULL));
//Both players will start at space 0.

	int indexA = 0;
	int indexB = 0;
// Keep running program until one of the players reach space 50.
while(indexA< GAME_LENGTH && indexB < GAME_LENGTH){
	advancePlayerA(&indexA);
	advancePlayerB(&indexB);
// if playerA and playerB at the same spot, move playerA back 1 spot
	if (indexA == indexB){
	    indexA = indexA - 1;
	}
// if playerA and playerB values are less than 0, they hold the value 0;	
	if (indexA < 0){
		indexA = 0;
	}
	if (indexB < 0){
		indexB = 0;
	}
// Print position of playerA and playerB
	printPosition(&indexA, &indexB);
//If position of playerA or playerB are greater or equal to 50 then print //message saying who won
	if (indexA >= 50){
		indexA = 50;
		std:: cout << "You have won the game!";	
	}
	if (indexB >= 50){
		std:: cout << "Your friend has won the game, goodluck next time.";	
	
	}
}	
	return 0;
}
//Function advances playerA 
void advancePlayerA(int* indexA)
{
	
	
	// use randomGenerator for cards drawn.
	int num = (rand()%100) +1;
// Forward 1 card
	if (num >= 1 && num < 40){
		*indexA= *indexA + 1;
//Forward 2 card
	}else if(num >= 40 && num < 60){
		*indexA = *indexA + 2;
//Mountain card(Forward 3)
	}else if(num >= 60 && num < 70){
		*indexA = *indexA + 3;
//Rainbow card(Forward 5)
	}else if(num >= 70 && num<80){
		*indexA = *indexA + 5;
//Cherry card (Back 3)
	}else if(num >= 80 && num<90){
		*indexA = *indexA - 3;
//Molasses card (back 5)
	}else if(num >=90 && num<100){
		*indexA = *indexA - 5;
	}
	
	
}
//Advances playerB
void advancePlayerB(int* indexB)
{
// use randomGenerator for cards drawn.
	int num = (rand()%100) +1;
// Forward 1 card
	if (num >= 1 && num < 30){
		*indexB = *indexB + 1;
//Forward 2 card
	}else if(num >= 30 && num < 40){
		*indexB = *indexB + 2;
//Mountain card(Forward 3)
	}else if(num >= 40 && num < 60){
		*indexB = *indexB + 3;
//Rainbow card(Forward 5)
	}else if(num >= 60 && num<70){
		*indexB = *indexB + 5;
//Cherry card (Back 3)
	}else if(num >= 70 && num<90){
		*indexB = *indexB - 3;
//Molasses card (back 5)
	}else if(num >=90 && num<100){
		*indexB = *indexB - 5;
	}
	
	
	
}
// prints position of playerA and playerB after every turn
void printPosition(int* playerA, int* playerB)
{	
	
	int i;
	std::cout<< std::endl;
//for each line and each line less than or equal to size of GAME_LENGTH, //increment
	for (i=0;i <= GAME_LENGTH; i++){
// if player == the value on the line, print at that position			
		if (*playerA==i){
			std::cout <<std::setw(i);
			std::cout << "A";
		}
		if (*playerB==i){
			std::cout <<std::setw(i);
			std::cout << "B";
			
		}
		
				
	}
}

