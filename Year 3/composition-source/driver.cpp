#include "Array.h"
#include "Fixed_Array.h"
#include "Stack.h"
#include "Queue.h"


int main (int argc, char * argv [])
{
/******REMEMBER DELETE STAMENETS IN SOURCE FILE USED FOR TESTING*******/

//ARRAY TESTING
	Array <int> array(10,5);
	array.size();
	array.max_size();
	//array.find(1);
	//array[3];
	array.resize(20);
	//array.get(2);
	array.set(19,3);
  	
 
/*
//FIXED_ARRAY TESTING
	Fixed_Array <int,10> newArray;
	newArray.size();
	newArray.max_size();
	
	
		
//STACK TESTING
	
	Stack <int> s; 
	s.push(1);
	Stack <int> z (s);
	s.size();
	s.pop();
	
//Queue Testing
	
	Queue<int> q;
	q.enqueue(2);	
	Queue<int> m(q);
	q.size();
	q.dequeue();
	q.clear();
  */
  return 0;
}
