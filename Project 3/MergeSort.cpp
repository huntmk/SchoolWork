//MergeSort.cpp

#include <iostream>
#include "MergeSort.h"


//default constructor
MergeSort::MergeSort(){
	
}

MergeSort::~MergeSort(){
}

void MergeSort::merge(int array[], int left, int mid, int right)
{
	int x;
	int y; 
	int p;
	
	int a1 = mid - left +1;
	int a2 = right - mid;
	int Left[a1];
	int Right[a2];
	
	//copy to temporary arrays L[] and R[]
	for(x = 0; x < a1; x++)
		Left[x] = array[left + x];
	for(y = 0; y < a2; y++)
		Right[y] = array[mid + left + y];
	
	// merge temporary arrays back into main array
	x = 0;
	y = 0;
	p = left;
	
	while (x < a1 && y < a2)
	{
		if (Left[x] <= Right[y])
		{
			array[p] = Left[x];
			x++;
		}
		else
		{
			array[p] = Right[y];
			y++;
		}
		p++;
	}
	
	while (x < a1)
	{
		array[p] = Left[x];
		x++;
		p++;
	}
	
	while(y < a2)
	{
		array[p] = Right[y];
		y++;
		p++;
	}
}
	

//sort method for Insertion sorting
void MergeSort::sort(int array[], int left, int right)
{
	if (left < right)
	{
		int z = left+(right-left)/2;
		
		sort(array,left,z);
		sort(array,z+1,right);
		
		//call function to merge subarrays
		merge(array,left,z,right);
		
	}
}