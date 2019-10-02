//HeapSort.h

#ifndef HEAPSORT_H
#define HEAPSORT_H
#include "Sort.h"
class HeapSort: public Sort
{
	public:
		//constructor
		HeapSort();
		//destructor
		~HeapSort();
		//create another function to build heap from array
		void rearrange(int *, int,int);
		//virtual sort method
		void sort(int *, int);
};
#endif//HEAPSORT_H