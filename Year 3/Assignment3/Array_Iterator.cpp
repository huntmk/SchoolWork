// $Id: Array.cpp 827 2011-02-07 14:20:53Z hillj $

// Honor Pledge:
//
// I pledge that I have neither given nor receieved any help
// on this assignment.

#include "Array_Iterator.h"

template <typename T>
Array_Iterator<T>::Array_Iterator(Array <T> & a):
a_(a),
curr_(0)
{
	//default constructor
}

template <typename T>
~Array_Iterator::Array_Iterator (void)
{
	//default constructor
}

template <typename T>
bool Array_Iterator <T>::is_done (void)
{
	//checks if done
	return this->curr_ >= this->a_.cur_size_;
}

template <typename T>
bool Array_Iterator<T>::advance(void)
{
	++this->curr_;
}

template <typename T>
T & Array_Iterator<T>:: operator * (void)
{
	//return data at current size index
	return this->a.data_[this->curr_];
}

template <typename T>
T * Array_Iterator<T>:: operator & (void)
{
	//return address at current size index
	return &this->a_.data_this->curr_];
}


