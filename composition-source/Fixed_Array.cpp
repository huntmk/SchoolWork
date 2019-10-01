// $Id: Fixed_Array.cpp 827 2011-02-07 14:20:53Z hillj $

// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
// Fixed_Array
//
#include "Fixed_Array.h"



template <typename T, size_t N>
Fixed_Array <T, N>::Fixed_Array (void):
data_(nullptr),
cur_size_(0),
max_size_(0)
{
	//members initialized in base class
	
	//default constructor
	this->cur_size_ = 0;
	this->max_size_ = N;
	
	this->data_ = new T [this->max_size_];
	
}

//
// Fixed_Array
//
template <typename T, size_t N>
Fixed_Array <T, N>::Fixed_Array (const Fixed_Array <T, N> & arr):
data_(nullptr),
cur_size_(0),
max_size_(0)
{
	//members initialized in base class
	
	//copy constructor
	//need to understand what N is.
	this->max_size_ = arr.max_size_;
	this->cur_size_ = arr.cur_size_;
	
	this->data_ = new T[this->max_size_];
	
	//for every index in array
	for(int i = 0; i < this->max_size_; i++)
	{
		//newVal from arr object is stored in data.
		T newVal = arr.data_[i];
		this->data_[i] = newVal;
				
	}
	
}


//
// Fixed_Array
//
template <typename T, size_t N>
Fixed_Array <T, N>::Fixed_Array (T fill):
data_(nullptr),
cur_size_(0),
max_size_(0)
{
	//members initialized in base class
	
	this->cur_size_ = N;
	this->max_size_ = N;
	
	this->data_ = new T[this->max_size_];
	
	//Fill the contents of the array.
		
	//for every index in array
	for(int x = 0; x < this->max_size_; x++)
	{
		//the index holds the fill
		this->data_[x] = fill;
		
	}
	
}

//
// ~Fixed_Array
//
template <typename T, size_t N>
Fixed_Array <T, N>::~Fixed_Array (void)
{
	//destructor called in base class
	delete [] data_;
}

//
// operator =
//
template <typename T, size_t N>
const Fixed_Array <T, N> & Fixed_Array <T, N>::operator = (const Fixed_Array <T, N> & rhs)
{
	//self assignment check if its same object
	if (&rhs == this)
	{
		return *this;
	}
	
	//else set members of this object rhs's members
	this->data_ = new T[N];
	
	this->max_size_ = rhs.max_size_;
	this->cur_size_ = rhs.cur_size_;
	
	//for every index in array
	for (int x = 0; x < N; x++)
	{
		//thisVal from rhs object is stored in this object
		T thisVal = rhs.data_[x];
		this->data_[x] =  thisVal;
			
	}	
	//return this object
	return *this;
	
}

template <typename T, size_t N>
T Fixed_Array<T,N>::get(size_t index) const
{
	//for every index in array
	for (int i = 0; i < this->max_size_; i++)
	{
		//if index is in array
		if(i==index)
		{
			//return the character at index being pointed to.
			return this->data_[i];
			
		}
					
	}
	// out of range exception
	throw std::out_of_range("Index not in array");
}

template <typename T, size_t N>
void Fixed_Array<T,N>::set(size_t index, T value)
{
	//if index is not valid then throw exception.
	if (index > this->max_size_ - 1|| index < 0)
	{
		throw std::out_of_range("Index not in array");
	}
	
	//for every index in array
	for (int i = 0; i < this->max_size_; i++)
	{		
		//if index is in array
		if (i == index)
		{
			//set character at index
			this->data_[i] = value;
		
		}
		
	}
		
}

template <typename T, size_t N>
int Fixed_Array<T,N>::find(T element) const
{
	//for every index in array
	for(int x = 0; x < this->max_size_;x++)
	{
		//if the contents of the index is equal to value, then return that index , if not in array, return -1
		if (this->data_[x] == element)
		{
			return x;
			
		}
			
	}
	
	return -1;
	
}

template <typename T, size_t N>
int Fixed_Array<T,N>::find(T element, size_t start) const
{
	//if start is out of index throw exception
	if (start >= this->max_size_ || start < 0)
	{
		throw std::out_of_range("Start index is out of bounds. ");
		
	}
	
	//for indexes from start to end of array
	for(int x = start; x < this->max_size_; x++)
	{
		//if value is at that index then return it
		if (this->data_[x] == element)
		{
			return x;
			
		}
			
	}
	
	return -1;
}
 
template <typename T, size_t N>
void Fixed_Array<T,N>::fill (T element)
{
	//Fill the contents of the array.
	
	//for every index in array
	for(int x = 0; x < this->max_size_; x++)
	{
		//the index holds the value 
		this->data_[x] = element;
		
	}
	
}
