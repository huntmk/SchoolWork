// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
//BubbleSort.cpp
#include "BubbleSort.h"
#include <iostream>

//default constructor
BubbleSort::BubbleSort()
{
	
}
BubbleSort::~BubbleSort(){
}
//sort method for BubbleSort
void BubbleSort::sort(int array[], int size)
{
	int f;
	int s;
	int temp;
	
	for (f = 0; f < size - 1; f++)
	{
		//std::cout << f << std::endl;
		
		for (s = 0; s < size - f - 1; s++)
		{
			//if array[s] is greater than array[s+1], switch there places
			if (array[s] < array[s + 1])
			{
				temp = array[s];
				array[s] = array[s+1];
				array[s+1] = temp;
				
			}
			
		
		}
		
		
	}
	
}
				

