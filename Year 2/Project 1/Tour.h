/*
* Tour.h
*
*  Created on: Feb 7, 2018
*      Author: Cellus
*/

#ifndef TOUR_H_
#define TOUR_H_

class Tour {
public:
		
	bool limit(int, int);
	bool isEmpty( int [], int, int);
	int emptyNextTo(int[], int, int);
	bool nextMove(int [], int * , int *);
	bool warnsdoffFunct(int [], int, int);
	
	bool tourFunct();
};

#endif /* TOUR_H_ */


