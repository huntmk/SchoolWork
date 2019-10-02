#include <iostream>
#include "QuickSort.h"

//QuickSort.cpp
//default constructor
QuickSort::QuickSort(){
	
}
QuickSort::~QuickSort(){
}

//sort method for Insertion sorting
//pass array for it to be sorted, low is the first index, high is the last index
int QuickSort::breakFunct(int array[], int high, int low)
{
	int part = array[high];
	int x =  low-1;
	
	for (int y = low; y <= high-1;y++)
	{
		
		if (array[y] <= part)
		{
			x++;
			int temp = array[x];
			array[x] = array[y];
			array[y] = temp;
		}
	}
	int temp = array[x+1];
	array[x+1] = array[high];
	array[high] = temp;
	
	return (x+1);
	
}

void QuickSort::sort(int array[], int high, int low)
{
	if (low < high)
	{
		int z = breakFunct(array,high, low);
		
		//sort elements before & after breakFunction 
		sort(array,z-1, low);
		sort(array,high,z +1);
	}
}