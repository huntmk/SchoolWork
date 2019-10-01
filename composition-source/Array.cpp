// $Id: Array.cpp 827 2011-02-07 14:20:53Z hillj $

// Honor Pledge:
//
// I pledge that I have neither given nor receieved any help
// on this assignment.

#include "Array.h"
#include <stdexcept>         // for std::out_of_bounds exception

//
// Array
//
template <typename T>
Array <T>::Array (void):
data_(nullptr),
cur_size_(0),
max_size_(0)
{
	//default constructor (default size to 10 to start off with)
	this->data_ = new T [10];
	
}

//
// Array (size_t)
//
template <typename T>
Array <T>::Array (size_t length): 
data_(nullptr), 
cur_size_(0), 
max_size_(0)
{
	
	//initialize constructor
	this->max_size_ = length;
	
	//create new T array
	this->data_ = new T [length];	
}

//
// Array (size_t, char)
//
template <typename T>
Array <T>::Array (size_t length, T fill): 
data_(nullptr), 
cur_size_(0), 
max_size_(0)
{
	
	//initialize constructor
	this->max_size_ = length;
	this->cur_size_ = length;
	
	//create new T array
	this->data_ = new T [length];

	//for every index in array
	//this index in the array holds fill	
	for(int i = 0; i < this->max_size_; i++)
	{
		this->data_[i] = fill;
		
	}	
	
}

//
// Array (const Array &)
//
template <typename T>
Array <T>::Array (const Array & array): 
data_(nullptr), 
cur_size_(0), 
max_size_(0)
{
	
	//copy constructor
	this->max_size_ = array.max_size_;
	this->cur_size_ = array.cur_size_;
	
	//Allocate the array
	this->data_ = new T[this->max_size_];
	
	//for every index in array
	for(int i = 0; i < this->max_size_; i++)
	{
		//pointer newVal holds data of next index
		T newVal = array.data_[i];
		this->data_[i] = newVal;
				
	}
	
}

//
// ~Array
//
template <typename T>
Array <T>::~Array (void)
{
	delete [] data_;
}

//
// operator =
//
template <typename T>
const Array <T> & Array <T>::operator = (const Array & rhs)
{
	
	if (&rhs == this)
	{
		return *this;
	}
	
		this->data_ = new T [rhs.max_size_];
		
		//'this' max size holds size of object 'rhs'
		this->max_size_ = rhs.max_size_;
		
		//for every index in array
		for (int x = 0; x < this->max_size_; x++)
		{
			//thisVal holds the character of the index at 'this' array object
			T thisVal = rhs.data_[x];
		
			//this holds the character from the object of right hand size
			this->data_[x] =  thisVal;
				
		}	
		return *this;
	
}

//
// operator []
//
template <typename T>
T & Array <T>::operator [] (size_t index)
{
	
	//for every index in array
	for (int i = 0; i < this->max_size_; i++)
	{
		//if index give is in array
		if(i==index)
		{
			//return  character of index being pointed to
			return this->data_[i];
			
		}
												
	}
	throw std::out_of_range("Index not in array");
}

//
// operator [] 
//
template <typename T>
const T & Array <T>::operator [] (size_t index) const
{
	for (int i = 0; i < this->max_size_; i++)
		{
			//if index is in array
			if(i==index)
			{
				//return the value at index being pointed to.
				return this->data_[i];
												
			}
						
		}
		//if not out out of range exception
		throw std::out_of_range("Index not in array");
}

//
// get
//
template <typename T>
T Array <T>::get (size_t index) const
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

//
// set
//
template <typename T>
void Array <T>::set (size_t index, T value)
{
	//if index is not valid then throw exception.
	if (index > this->max_size_ - 1 || index < 0)
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
			//increment value of cur_size_
			this->cur_size_++;
						
		}
		
	}
	
}

//
// resize
//
template <typename T>
void Array <T>::resize (size_t new_size)
{
	
	//if new_size is greater than cur_size_
	if (new_size > this->max_size_)
	{
		//Have max size equal new size      
		this->max_size_ = new_size;
        
		T * temp = new T [this->max_size_];      

		// Copy the old elements to the new array.      
		for (size_t i = 0; i < this->max_size_; ++ i)       
			temp[i] = this->data_[i]; 
		
		//add data back to data array	
		for (size_t a = 0; a < this->max_size_; ++ a)
			this->data_[a] = temp[a];
		
		
		
		// Safely delete the old data. ;-) 
		delete [] temp;   
					
	}
	//if they are the same, nothing changes
	
	//if less than new size, throw exception
	if (new_size < this->max_size_)
	{
		throw "Resize can only make larger. Use shrink method. ";
	}
			
}

//
// find (char)
//
template  <typename T>
int Array <T>::find (T element) const
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

//
// find (char, size_t) 
//
template <typename T>
int Array <T>::find (T val, size_t start) const
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
		if (this->data_[x] == val)
		{
			return x;
			
		}
				
	}
	
	return -1;
}

//
// operator ==
//
template <typename T>
bool Array <T>::operator == (const Array & rhs) const
{
	
	//Test the array for equality.
	
	//check for if objects are the same
	if (&rhs  != this)
	{
		return false;
	}
	
	//if contents are the same
	for (int i = 0; i < this->max_size_;i++)
	{
		if (this->data_[i] != rhs.data_[i])
		{
			
			return false;
			
		}
	}
	//if conditionals return false then the arrays are equal
	return true;
	
}

//
// operator !=
//
template <typename T>
bool Array <T>::operator != (const Array & rhs) const
{
	
	//Test the array for inequality.
		
	//check for if the sizes are the same
	if (&rhs == this)
	{
		return false;
	}
	
	//if contents are the same
	for(int i = 0; i < this->max_size_; i++)
	{
		if (this->data_[i] == rhs.data_[i])
		{
			//return false
			return false;
			
		}
	}
	
	return true;
	
}

//
// fill
//
template <typename T>
void Array <T>::fill (T value)
{

	//Fill the contents of the array.
	
	//for every index in array
	for(int x = 0; x < this->max_size_; x++)
	{
		//the index holds the value 
		this->data_[x] = value;
		
	}
	
	this->cur_size_ = this->max_size_;
}

template <typename T>
void Array<T>::shrink()
{
	//Shrink the array to reclaim unused space.
		
	if(this->cur_size_ < this->max_size_)
	{
		this->max_size_ = this->cur_size_;
	}
	//if its the same then do nothing
	
}
