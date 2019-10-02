// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
//InsertionSort.cpp
#include <iostream>
#include "InsertionSort.h"


//default constructor
InsertionSort::InsertionSort(){
	
}
InsertionSort::~InsertionSort(){
}

//sort method for Insertion sorting
void InsertionSort::sort(int array[], int size)
{
	//sort the array from i to i-1.
	int i,sortedSet, k;
	for( i = 1; i < size; i++)
	{
		sortedSet = array[i];
		k = i-1;
		//while index[k] is greater than sorted, 
		while(k>=0 && array[k] < sortedSet)
		{
			// k+1 holds k if k is greater, 
			array[k +1] = array[k];
			k = k -1;
		}
		
		//add that [k+1] to the sorted set
		array[k +1 ] = sortedSet;
		
	}
	
}
	
