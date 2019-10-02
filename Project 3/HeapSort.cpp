//HeapSort.cpp
#include <iostream>
#include "HeapSort.h"


//default constructor
HeapSort::HeapSort(){
	
}

HeapSort::~HeapSort(){
}
void HeapSort::rearrange(int array[], int size, int f)
{
	int large = f;
	int left = 2*f + 1;
	int right = 2*f + 2;
	
	//if left is larger than root
	if (left < size && array[left] > array[large])
	{
		large = left;
	}
	// if right is larger
	if (right < size && array[right] > array[large])
	{
		large = right;
	}
	//if large is not the root
	if (large != f)
	{
		int temp = array[f];
		array[f] = array[large];
		array[large] = temp;
		
		rearrange(array,size,large);
	}
}
//sort method for Insertion sorting
void HeapSort::sort(int array[], int size)
{
	//build heap
	for (int a = size / 2 -1; a>= 0; a--)
	{
		rearrange(array,size,a);
	}
	
	//traverse through heap
	for (int a = size -1; a>=0; a--)
	{
		int temp = array[a];
		array[a] = array[0];
		array[0] = temp;
		
		rearrange(array,a, 0);
	}
}


