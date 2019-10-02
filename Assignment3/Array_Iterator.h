// $Id: Array.cpp 827 2011-02-07 14:20:53Z hillj $

// Honor Pledge:
//
// I pledge that I have neither given nor receieved any help
// on this assignment.

#ifndef _ARRAY_ITERATOR_H_
#define _ARRAY_ITERATOR_H_

template <typename T>
class Array_Interator 
{
	public:
		//constructor
		Array_Interator(Array <T> & a)
		
		//destructor
		~Array_Interator (void)
		
		bool is_done (void);
		bool advance (void);
		T & operator * (void);
		T * operator -> (void);
	
	private:
		Array <T> & a_;
		size_t curr_;
		
};
#include "Array_Interator.cpp"

#endif   // !defined _ARRAY_ITERATOR_H_