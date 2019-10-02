//Driver File
#include "Sort.h"
#include "newSort.h"
#include "InsertionSort.h"
#include "QuickSort.h"
#include "HeapSort.h"
#include "MergeSort.h"
#include <stdlib.h>
#include <iostream>


int randomFunct()
{
	int randNum = rand() %20000 + 1;
	return randNum;
}

void printArray(int a[], int size)
{
	int t;
	for (t = 0; t < size;t++)
		std::cout << a[t] << ", ";
	std::cout << std::endl;
}

void hundred()
{
	Sort * insert = new InsertionSort;
	newSort * quick = new QuickSort;
	Sort * heap = new HeapSort;
	newSort * merge = new MergeSort;
	
	int *array = new int[100];
	
	//store numbers from random funct in array
	for (int i = 0; i < 100; i++)
		array[i] = randomFunct();
	//use temp array for sorting
	int temp[100];
	
	for (int b = 0; b < 100; b++)
		temp[b] = array[b];
	//do unsorted
	insert->sort(temp,100);
	//printArray(temp,100);
	
	//do sorted
	insert->sort(temp,100);
	//printArray(temp,100);
	
	//QUICK SORT
	//do unsorted
	for (int b = 0; b < 100; b++)
		temp[b] = array[b];
	quick ->sort(temp,99,0);
	//printArray(temp,100);
	
	//do sorted
	quick->sort(temp,99,0);
	
	//HEAP SORT
	//do unsorted
	for (int b = 0; b < 100; b++)
		temp[b] = array[b];
	heap -> sort(temp,100);
	//printArray(temp,100);
		
	//do sorted
	heap ->sort(temp,100);
	
	//MERGESORT
	//do unsorted
	for (int b = 0; b < 100; b++)
		temp[b] = array[b];
	merge ->sort(temp,0,99);
	//printArray(temp,100);
	//do sorted
	merge ->sort(temp,0,99);
	
	std::cout << "Array size of 100 sorted" << std::endl;
	
	delete insert;
	delete quick;
	delete heap;
	delete merge;
	delete [] array;
	
}

void fiveHundred()
{
	Sort * insert = new InsertionSort;
	newSort * quick = new QuickSort;
	Sort * heap = new HeapSort;
	newSort * merge = new MergeSort;
	
	int *array = new int[500];
	
	//store numbers from random funct in array
	for (int i = 0; i < 500; i++)
		array[i] = randomFunct();
	//use temp array for sorting
	int temp[500];
	
	for (int b = 0; b < 500; b++)
		temp[b] = array[b];
	//do unsorted
	insert->sort(temp,500);
	//printArray(temp,500);
	
	//do sorted
	insert->sort(temp,500);
	//printArray(temp,500);
	
	//QUICK SORT
	//do unsorted
	for (int b = 0; b < 500; b++)
		temp[b] = array[b];
	quick ->sort(temp,499,0);
	//printArray(temp,500);
	
	//do sorted
	quick->sort(temp,499,0);
	
	//HEAP SORT
	//do unsorted
	for (int b = 0; b < 500; b++)
		temp[b] = array[b];
	heap -> sort(temp,500);
	//printArray(temp,500);
	//do sorted
	heap ->sort(temp,500);
	
	//MERGESORT
	//do unsorted
	for (int b = 0; b < 500; b++)
		temp[b] = array[b];
	merge ->sort(temp,0,499);
	//printArray(temp,500);
	//do sorted
	merge ->sort(temp,0,499);
	
	std::cout << "Array size of 500 sorted" << std::endl;
	delete insert;
	delete quick;
	delete heap;
	delete merge;
	delete [] array;
}

void thousand()
{
	Sort * insert = new InsertionSort;
	newSort * quick = new QuickSort;
	Sort * heap = new HeapSort;
	newSort * merge = new MergeSort;
	
	int* array = new int[1000];
	
	//store numbers from random funct in array
	for (int i = 0; i < 1000; i++)
		array[i] = randomFunct();
	//use temp array for sorting
	int temp[1000];
	
	for (int b = 0; b < 1000; b++)
		temp[b] = array[b];
	//do unsorted
	insert->sort(temp,1000);
	//printArray(temp,1000);
	
	//do sorted
	insert->sort(temp,1000);
	//printArray(temp,1000);
	
	
	//QUICK SORT
	//do unsorted
	for (int b = 0; b < 1000; b++)
		temp[b] = array[b];
	quick ->sort(temp,999,0);
	//printArray(temp,1000);
	
	//do sorted
	quick->sort(temp,999,0);
	
	//HEAP SORT
	//do unsorted
	for (int b = 0; b < 1000; b++)
		temp[b] = array[b];
	heap -> sort(temp,1000);
	//printArray(temp,1000);
	//do sorted
	heap ->sort(temp,1000);
	
	//MERGESORT
	//do unsorted
	for (int b = 0; b < 1000; b++)
		temp[b] = array[b];
	merge ->sort(temp,0,999);
	//printArray(temp,1000);
	//do sorted
	merge ->sort(temp,0,999);
	
	
	std::cout << "Array size of 1,000 sorted" << std::endl;
	delete insert;
	delete quick;
	delete heap;
	delete merge;
	delete [] array;
}

void twoThousand()
{
	Sort * insert = new InsertionSort;
	newSort * quick = new QuickSort;
	Sort * heap = new HeapSort;
	newSort * merge = new MergeSort;
	
	int* array = new int[2000];
	
	//store numbers from random funct in array
	for (int i = 0; i < 2000; i++)
		array[i] = randomFunct();
	//use temp array for sorting
	int* temp = new int[2000];
	
	for (int b = 0; b < 2000; b++)
		temp[b] = array[b];
	//do unsorted
	insert->sort(temp,2000);
	
	//printArray(temp,2000);
	//do sorted
	
	insert->sort(temp,2000);
	//printArray(temp,2000);
	
	
	//QUICK SORT
	//do unsorted
	for (int b = 0; b < 2000; b++)
		temp[b] = array[b];
	quick ->sort(temp,1999,0);
	//printArray(temp,2000);
	
	//do sorted
	quick->sort(temp,1999,0);
	
	//HEAP SORT
	//do unsorted
	for (int b = 0; b < 2000; b++)
		temp[b] = array[b];
	heap -> sort(temp,2000);
	//printArray(temp,2000);
	//do sorted
	heap ->sort(temp,2000);
	
	//MERGESORT
	//do unsorted
	for (int b = 0; b < 2000; b++)
		temp[b] = array[b];
	merge ->sort(temp,0,1999);
	//printArray(temp,2000);
	//do sorted
	merge ->sort(temp,0,1999);
	
	std::cout << "Array size of 2,000 sorted" << std::endl;
	
	delete insert;
	delete quick;
	delete heap;
	delete merge;
	delete [] array;
	delete [] temp;
}

void fiveThousand()
{
	Sort * insert = new InsertionSort;
	newSort * quick = new QuickSort;
	Sort * heap = new HeapSort;
	newSort * merge = new MergeSort;
	
	int* array = new int[5000];
	
	//store numbers from random funct in array
	for (int i = 0; i < 5000; i++)
		array[i] = randomFunct();
	//use temp array for sorting
	int* temp = new int[5000];
	
	for (int b = 0; b < 5000; b++)
		temp[b] = array[b];
	//do unsorted
	insert->sort(temp,5000);
	//printArray(temp,5000);
	
	//do sorted
	insert->sort(temp,5000);
	//printArray(temp,5000);
	
	//QUICK SORT
	//do unsorted
	for (int b = 0; b < 5000; b++)
		temp[b] = array[b];
	quick ->sort(temp,4999,0);
	//printArray(temp,5000);
	
	//do sorted
	quick->sort(temp,4999,0);
	
	//HEAP SORT
	//do unsorted
	for (int b = 0; b < 5000; b++)
		temp[b] = array[b];
	heap -> sort(temp,5000);
	//printArray(temp,5000);
	//do sorted
	heap ->sort(temp,5000);
	
	//MERGESORT
	//do unsorted
	for (int b = 0; b < 5000; b++)
		temp[b] = array[b];
	merge ->sort(temp,0,4999);
	//printArray(temp,5000);
	//do sorted
	merge ->sort(temp,0,4999);
	
	std::cout << "Array size of 5,000 sorted" << std::endl;
	delete insert;
	delete quick;
	delete heap;
	delete merge;
	delete [] array;
	delete [] temp;
	
}
void eightThousand()
{
	Sort * insert = new InsertionSort;
	newSort * quick = new QuickSort;
	Sort * heap = new HeapSort;
	newSort * merge = new MergeSort;
	
	int *array= new int[8000];
	
	//store numbers from random funct in array
	for (int i = 0; i < 8000; i++)
		array[i] = randomFunct();
	//use temp array for sorting
	int *temp = new int[8000];
	
	for (int b = 0; b < 8000; b++)
		temp[b] = array[b];
	//do unsorted
	insert->sort(temp,8000);
	//printArray(temp,8000);
	
	//do sorted
	insert->sort(temp,8000);
	//printArray(temp,8000);
	
	//QUICK SORT
	//do unsorted
	for (int b = 0; b < 8000; b++)
		temp[b] = array[b];
	quick ->sort(temp,7999,0);
	//printArray(temp,8000);
	
	//do sorted
	quick->sort(temp,7999,0);
	
	//HEAP SORT
	//do unsorted
	for (int b = 0; b < 8000; b++)
		temp[b] = array[b];
	heap -> sort(temp,8000);
	//printArray(temp,8000);
	//do sorted
	heap ->sort(temp,8000);
	
	//MERGESORT
	//do unsorted
	for (int b = 0; b < 8000; b++)
		temp[b] = array[b];
	merge ->sort(temp,0,7999);
	//printArray(temp,8000);
	//do sorted
	merge ->sort(temp,0,7999);
	
	
	std::cout << "Array size of 8,000 sorted" << std::endl;
	delete insert;
	delete quick;
	delete heap;
	delete merge;
	delete [] array;
	delete [] temp;
}

void tenThousand()
{
	Sort * insert = new InsertionSort;
	newSort * quick = new QuickSort;
	Sort * heap = new HeapSort;
	newSort * merge = new MergeSort;
	
	int* array= new int[10000];
	
	//store numbers from random funct in array
	for (int i = 0; i < 10000; i++)
		array[i] = randomFunct();
	//use temp array for sorting
	int *temp = new int[10000];
	
	for (int b = 0; b < 10000; b++)
		temp[b] = array[b];
	//do unsorted
	insert->sort(temp,10000);
	//printArray(temp,10000);
	
	//do sorted
	insert->sort(temp,10000);
	//printArray(temp,10000);
	
	//QUICK SORT
	//do unsorted
	for (int b = 0; b < 10000; b++)
		temp[b] = array[b];
	quick ->sort(temp,9999,0);
	//printArray(temp,10000);
	
	//do sorted
	quick->sort(temp,9999,0);
	
	//HEAP SORT
	//do unsorted
	for (int b = 0; b < 10000; b++)
		temp[b] = array[b];
	heap -> sort(temp,10000);
	//printArray(temp,10000);
	//do sorted
	heap ->sort(temp,10000);
	
	//MERGESORT
	//do unsorted
	for (int b = 0; b < 10000; b++)
		temp[b] = array[b];
	merge ->sort(temp,0,9999);
	//printArray(temp,10000);
	//do sorted
	merge ->sort(temp,0,9999);
	
	std::cout << "Array size of 10,000 sorted" << std::endl;
	delete insert;
	delete quick;
	delete heap;
	delete merge;
	delete [] array;
	delete [] temp;
}

//implement a random number generator function from numbers 1 - 20,000.
//and store in array to be sorted


//execute sorting algorithms in input sizes: 100,500,2000,5000,8000,& 10000
//create a function for ech inp./ut size
	//inside the function create a dynamic array to do the sorting algorithms
	

//main function
int main()
{
	
	hundred();
	
	fiveHundred();
	
	
	thousand();
	
		//array size too large
			//declare temp as dynamic
	twoThousand();
	
	fiveThousand();
	eightThousand();
	tenThousand();
	
	
}
