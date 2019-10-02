// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
#ifndef BUBBLESORT_H
#define BUBBLESORT_H
#include "Sort.h"
class BubbleSort: public Sort
{
	public:
		// constructor
		BubbleSort();
		//destructor
		~BubbleSort();
		//virtual sort method
		void sort(int *, int);
		 
};

#endif//BUBBLESORT_H
