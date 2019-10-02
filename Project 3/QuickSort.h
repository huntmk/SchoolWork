//QuickSort.h
#ifndef QUICKSORT_H
#define QUICKSORT_H
#include "newSort.h"
class QuickSort: public newSort
{
	public:
		//constructor
		QuickSort();
		//destructor
		~QuickSort();
		//virtual sort method
		int breakFunct(int*, int, int);
		void sort(int *, int,int);
};
#endif//QUICKSORT_H