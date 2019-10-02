//MergeSort.h

#ifndef MERGESORT_H
#define MERGESORT_H
#include "newSort.h"
class MergeSort: public newSort
{
	public:
		//constructor
		MergeSort();
		//destructor
		~MergeSort();
		//create function to merge  two subarrays
		void merge(int *,int, int, int);
		//virtual sort method
		void sort(int *, int,int);
};
#endif//MERGESORT_H