/*
* Tour.cpp
*
*  Created on: Feb 6, 2018
*      Author: Cellus
*/
#include <ctime>
#include <stdlib.h>
#include <string>
#include<iostream>
#include "Tour.h"
#include "Stack.h"
#include "LinkedList.h"


using namespace std;


//define DIMENSION to use for "8 by 8" board
#define DIMENSION 8

/*Board*/ int board[DIMENSION*DIMENSION];

//created stack and linked list objects to store to use methods to store the necessary information
LinkedList list;
Stack stack;
//variables

//state is the number that represents the order in which the knight moved, set x and y to 8 for 8 by 8 array
int moveNum = 0;
//int knight[DIMENSION][DIMENSION];
string decision;

//create constant ints arrays to use for knight moves
static int moveX[8] = { 1,1,2,2,-1,-1,-2,-2 };
static int moveY[8] = { 2,-2,1,-1,2,-2,1,-1 };


//function keeps knight within the board
bool Tour:: limit( int x, int y)
{
	return ((x >= 0 && y >= 0) && (x < DIMENSION && y < DIMENSION));
}

// check if square is empty and hasn't been moved to yet
bool Tour::isEmpty(int knight[], int x, int y)
{
	return (limit(x, y)) && (knight[y*DIMENSION + x] < 0);
}

//returns squares(knight not moved to yet)next to coordinates
int Tour::emptyNextTo(int knight[], int x, int y)
 {
	int numOf = 0;
	for (int i = 0; i < 8; i++)
			if (isEmpty(knight,(x + moveX[i]), (y + moveY[i])))
			numOf++;

	return numOf;

}

//FUNCTIONS TO IMPLEMENT
//...
//Warnsdoff nextMovev() function for algorithm
bool Tour::nextMove(int w[], int *x, int *y)
{
	int min_deg_idx = -1;
	int counter;
	int min_deg = (DIMENSION + 1);
	int newX;
	int newY;

	//Try all adjacent squares adjacent to knights position from the spot.
	int begin = rand() % DIMENSION;
	for (int count = 0; count < DIMENSION; ++count)
	{
		int i = (begin + count) % DIMENSION;
		newX = *x + moveX[i];
		newY = *y + moveY[i];
		if ((isEmpty(w,newX, newY)) &&
			(counter = emptyNextTo(w, newX, newY)) < min_deg)
		{
			min_deg_idx = i;
			min_deg = counter;
		}
	}

	//if we can find next spot
	if (min_deg_idx == -1)
		return false;

	// Store coordinates of the next spot on board
	newX = *x + moveX[min_deg_idx];
	newY = *y + moveY[min_deg_idx];

	//Have the board array hold that position, then push the board to the stack as an element 
	board[DIMENSION*DIMENSION] = w[newY*DIMENSION + newX];
	stack.push(board);
	w[newY*DIMENSION + newX] = moveNum;									 // mark next spot in order

	// Update next point
	*x = newX;
	*y = newY;
	moveNum++;
	return true;
}


    



//Function that does the warnsdoff algorithm for first 32 moves
bool Tour::warnsdoffFunct(int knight[], int x , int y)
{
	//use a warnsdoff algorithm function to keep picking next spots to move to
	for (int b = 0; b < (DIMENSION*DIMENSION)/2; b++)
	{
		if (nextMove(knight, &x, &y) == 0)
		{
			return false;
		}
		
	}
	
}


//Function that runs the whole tour along with other associated operations
bool Tour:: tourFunct()
{
	
	
	int knight[DIMENSION*DIMENSION];//int knight[DIMENSION*DIMENSION];
	for (int i = 0; i < DIMENSION; i++)
		for (int j = 0; j < DIMENSION; j++)
			knight[i*DIMENSION + j] = -1;


	//initial x and intitial y positions
	int initialX = 0;
	int initialY = 0;

	//random intial position to begin with
	//int randomX = rand() % DIMENSION;
	//int randomY = rand() % DIMENSION;

	//positions x and y will hold random positions
	int x;//= randomX;
	int y;//= randomY;
	//IMPORTANT
	//Ask user for starting positions from the user
	//These will be stored in the link lists
	//Use each of the stored coordinates for multiple runs
	//of the program


	
	decision = "Y";
	//ask user for starting positions
	
	while (decision == "Y")
	{
		//ask for row again
		cout << "Enter row: ";
		cin >> initialX;
		if (initialX >= 0 && initialY < DIMENSION)
		{
			x = initialX;
		}
		//if x is not a legal number for the board, ask user to enter it again
		else {
			cout << "Please use a number from 0 to 7. Enter row again: ";
			cin >> initialX;
		}

		cout << "Okay choose the number for the colulmn: ";
		cin >> initialY;
		//Ask for Column
		if (initialY >= 0 && initialY < DIMENSION)
		{
			y = initialY;
		}
		//if y is not a legal number for baord, ask user to enter it again
		else {

			cout << "Please use a number from 0 to 7. Enter again: ";
			cin >> initialY;
		}

		//store these in nodes

		list.addNode(x,y);

		cout << "Would you like to add another node to the linked list? 'Y' for yes, 'N' for no:  ";
		cin >> decision;
	}
	//display the node
	list.displayList();
	//delete node to pop off from list and use for the tour
	
	knight[y*DIMENSION + x] = moveNum;
	
	moveNum++;

	
		//call warnsdoffFunction to execute first 32 moves
		warnsdoffFunct(knight, x, y);

		

		//while state is greater than 32, do exhaustive search(backtracking) for next spot on board 
		
		while (moveNum >= 32 && moveNum < 64)
		{
		
			if (isEmpty(knight, x - 2, y + 1))
			{
				//put the state of the coordinates on the board
				x -= 2;
				y += 1;
				board[DIMENSION*DIMENSION] = knight[y*DIMENSION + x];
				//push knight to stack
				stack.push(board);
				knight[y*DIMENSION + x]= moveNum;
				moveNum++;
			}
			else
				if (isEmpty(knight, x - 1, y + 2))
				{
					x -= 1;
					y += 2;
					board[DIMENSION*DIMENSION] = knight[y*DIMENSION + x];
					//push knight to stack
					stack.push(board);
					knight[y*DIMENSION + x]= moveNum;
					moveNum++;


				}
				else
					if (isEmpty(knight, x + 1, y + 2))
					{
						x += 1;
						y += 2;
						board[DIMENSION*DIMENSION] = knight[y*DIMENSION + x];
					//push knight to stack
					stack.push(board);
					knight[y*DIMENSION + x]= moveNum;
					moveNum++;
					}
					else
						if (isEmpty(knight,x + 2, y + 1))
						{
							x += 2;
							y += 1;
							board[DIMENSION*DIMENSION] = knight[y*DIMENSION + x];
					//push knight to stack
					stack.push(board);
					knight[y*DIMENSION + x]= moveNum;
					moveNum++;
						}
						else
							if (isEmpty(knight, x + 2, y - 1))
							{
								x += 2;
								y -= 1;
								board[DIMENSION*DIMENSION] = knight[y*DIMENSION + x];
					//push knight to stack
					stack.push(board);
					knight[y*DIMENSION + x]= moveNum;
					moveNum++;
							}
							else
								if (isEmpty(knight, x + 1, y - 2))
								{
									x += 1;
									y -= 2;
									board[DIMENSION*DIMENSION] = knight[y*DIMENSION + x];
								//push knight to stack
								stack.push(board);
								knight[y*DIMENSION + x]= moveNum;
								moveNum++;
								}
								else
									if (isEmpty(knight, x - 1, y - 2))
									{
										x -= 1;
										y -= 2;
										board[DIMENSION*DIMENSION] = knight[y*DIMENSION + x];
									//push knight to stack
									stack.push(board);
									knight[y*DIMENSION + x]= moveNum;
									moveNum++;
									}
									else
										if (isEmpty(knight, x - 2, y - 1))
										{
											x -= 2;
											y -= 1;
											board[DIMENSION*DIMENSION] = knight[y*DIMENSION + x];
											//push knight to stack
											stack.push(board);
											knight[y*DIMENSION + x]= moveNum;
											moveNum++;
										}
										else
										{
											//back track
											//Couldn't figure  out intital back track: but process would be popping the previous state of the board (the whole array) from stack element and storing it as current board state, then you would use different move
											//knight[DIMENSION*DIMENSION]= stack.pop();
											
											moveNum--;
										}




		}
		
		for (int i = 0; i < (DIMENSION); i++)
			for (int j = 0; j < DIMENSION; j++)
				cout << knight[i*DIMENSION + j] << "  ";
				


		return true;
		
		
}

