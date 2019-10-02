// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
#ifndef INSERTIONSORT_H
#define INSERTIONSORT_H
#include "Sort.h"
class InsertionSort: public Sort
{
	public:
		//constructor
		InsertionSort();
		//destructor
		~InsertionSort();
		//virtual sort method
		void sort(int *, int);
};
#endif//INSERTIONSORT_H
