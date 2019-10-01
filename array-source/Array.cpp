// $Id: Array.cpp 820 2011-01-17 15:30:41Z hillj $

// Honor Pledge:
//
// I pledge that I have neither given nor receieved any help
// on this assignment.

#include "Array.h"


Array::Array (void): data_(nullptr),cur_size_(0),max_size_(0)
{
	//initialize constructor	
	//Comment changes made
}

Array::Array (size_t length): data_(nullptr), cur_size_(0), max_size_(length)
{
	//Comment changes made
	
	//initialize constructor
	this->data_ = new char [length];	
		
}

Array::Array (size_t length, char fill): data_(nullptr), cur_size_(length), max_size_(length)
{
	//Comment changes made
	
	//initialize constructor
	//Comment changes made
	
	//set data to initial state
	this->data_= new char[length];
	
	//for every index in array
	for(int i = 0; i < this->max_size_; i++)
	{
		//this index in the array holds fill		
		this->data_[i] = fill;
		
	}
		
}


Array::Array (const Array & array): data_(array.data_), cur_size_(array.cur_size_), max_size_(array.max_size_)
{
	//Comment changes made
	
	//copy constructor
	this->max_size_ = array.max_size_;
	
	//Allocate the array
	this->data_ = new char[this->max_size_];
	
	//for every index in array
	for(int i = 0; i < this->max_size_; i++)
	{
		//pointer newVal holds data of next index
		char newVal = array.data_[i];
		newVal = this->data_[i];
				
	}
	
}

Array::~Array (void)
{
	//destructor
	
	//delete data
	delete [] data_;
	
}

const Array & Array::operator = (const Array & rhs)
{
	//Comment changes made
	
	//Assignment Operation- look over
	
	if (&rhs != this)
	{
		// delete old memory
		delete [] data_;
		
		this->data_ = new char [rhs.max_size_];
		
		//'this' max size holds size of object 'rhs'
		this->max_size_ = rhs.max_size_;
		
		//for every index in array
		for (int x = 0; x < this->max_size_; x++)
		{
			//thisVal holds the character of the index at 'this' array object
			char thisVal = rhs.data_[x];
		
			//this holds the character from the object of right hand size
			this->data_[x] =  thisVal;
				
		}
		
	}
	//return array
	return *this;
}

char & Array::operator [] (size_t index)
{
	//Comment changes made
	
	//Get the character at the specified index. If the index is not
	//within the range of the array, then std::out_of_range exception
	//is thrown.
				
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
		
		throw "Out of range exception. ";
		
}
const char & Array::operator [] (size_t index) const
{
	//Comment changes made
	
	//@overload // look above
	//The returned character is not modifiable.
	//check if index is in range of array
	
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
		//if not out out of range exception
		throw "Out of range exception. ";
	
}

char Array::get (size_t index) const
{
	//Comment changes made
	
	//Get the character at the specified index. If the \a index is not within the range of the array
	//, then std::out_of_range exception is thrown.
	
		//for every index in array
		for (int i = 0; i < this->max_size_; i++)
		{
			//if index is in array
			if(i==index)
			{
				//return the character at index being pointed to.
				return this->data_[i];
				
			}
			else
			{
				throw "Index out of bounds";
			}
			
		}
		// out of range exception
		throw "Out of range exception. ";
		
}

void Array::set (size_t index, char value)
{
	//Comment changes made
	
	//Set the character at the specified \a index. If the \a index is not
    //within range of the array, then std::out_of_range exception is 
    // thrown.
	
	//for every index in array
	for (int i = 0; i < this->max_size_; i++)
	{		
		//if index has a char
		if (i == index)
		{
			//set character at index
			this->data_[i] = value;
						
		}
	}
		
}

void Array::resize (size_t new_size)
{
	//Comment changes made
	
	/* Set a new size for the array. If \a new_size is less than the current
   * size, then the array is truncated. If \a new_size if greater then the
   * current size, then the array is made larger and the new elements are
   * not initialized to anything. If \a new_size is the same as the current
   * size, then nothing happens.
   *
   * The array's original contents are preserved regardless of whether the 
   * array's size is either increased or decreased.
   */
   
	//if new_size is greater than cur_size_
	if (new_size > this->max_size_)
	{
		//then the array is made larger and the new elements are not initialized to anything.
		this->max_size_ = new_size;
			
	}
	//if they are the same, nothing changes
	
	//if less than new size, throw exception
	if (new_size < max_size_)
	{
		throw "Resize can only make larger. Use shrink method. ";
	}
	
}

int Array::find (char ch) const
{
	//Locate the specified character in the array. The index of the first
	//occurrence of the character is returned. If the character is not
	//found in the array, then -1 is returned.
	
	//for every index in array
	for(int x = 0; x < this->max_size_;x++)
	{
		//if the contents of the index is equal to value of ch, then return that index , if not in array, return -1
		if (this->data_[x] = ch)
		{
			return x;
			
		}
		else
		{
			return -1;
			
		}
		
	}
	
}

int Array::find (char ch, size_t start) const
{
	//Comment changes made
	
	//@overload
	
	//This version allows you to specify the start index of the search. If
	//the start index is not within the range of the array, then the
	//std::out_of_range exception is thrown.
	
	//if start is out of index thrwo exception
	if (start > this->max_size_ || start < 0)
	{
		throw "Start index is out of bounds. ";
		
	}
	
	//for indexes from start to end of array
	for(int x = start; x < this->max_size_; x++)
	{
		//if ch is at that index then return it
		if (this->data_[x] = ch)
		{
			return x;
			
		}
		else
		{
			return -1;
			
		}
		
	}
	
}

bool Array::operator == (const Array & rhs) const
{
	//Comment changes made
	
	//Test the array for equality.
	
	//check for if the sizes are the same
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

bool Array::operator != (const Array & rhs) const
{
	//Comment changes made
	
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

void Array::fill (char ch)
{
	//Comment changes made
	
	//Fill the contents of the array.
	
	//for every index in array
	for(int x = 0; x < this->max_size_; x++)
	{
		//the index holds the ch value 
		this->data_[x] = ch;
		
	}
	
	
}

void Array::shrink (void)
{
	//Comment changes made
	
	//Shrink the array to reclaim unused space.
	
	
	if(this->cur_size_ < this->max_size_)
	{
		this->max_size_ = this->cur_size_;
	}
	//if its the same then do nothing
		
}

void Array::reverse (void)
{
	//Reverse the contents of the array such that the first element is now
	// the last element and the last element is the first element.
	
	//create begin pointer that points to the first data element
	int begin = 0;
	
	//create end pointer that points to the last data element
	int end = this->max_size_ -1;
	
		
	//while the begin index is less than the end index
	while (begin < end)
	{
		//swap begin and end index, then increment begin and decrement end to move to the next indexes to swap
		
		//pointer t holds begin
		char t = this->data_[begin];
		//begin then holds end
		this->data_[begin] = this->data_[end];
		//end holds t
		this->data_[end] = t;
		//swap completed
				
		//increment begin moves to next value
		begin++;
		
		//decrement end moves to previous value
		end--;
		
	}
	
}

Array Array::slice (size_t begin) const
{
	/*
	The slice() method returns a shallow copy of a portion of an array into
	a new array object selected from begin to end (end not included). The original
	array will not be modified.
	*/
	
	//var used for keeping track of indexes and inserting elements in to correct spot in new array object
	int newIndex = 0;
	int newSize = this->max_size_ - begin;
	// create new array object, with size being current size of original array minus the number of beginning index
	Array newArray(newSize);
	
	//Take portion of the array from begin to end of the array and store in the new array object
	
	//for (indexes from begin to end of  old array)
	for(int i = begin; i < newSize; i++)
	{
		//store contents in new array
		
		//create pointer that points to address of the new array
		char newVal = this->data_[i];
		
		//this pointer then holds the content of the index of old array
		newArray.data_[newIndex] = newVal;
		newIndex++;
		
	}
	
	//return the new Array
	return newArray;
		
}

Array Array::slice (size_t begin, size_t end) const
{
	//overload - look at first slice method (slice from begin to end)
	
	//var used for keeping track of indexes and inserting elements in to correct spot in new array object
	int newIndex = 0;
	
	int newSize = ((end - begin) + 1);
	// create new array object 
	Array newArray(newSize);
	
	//Take portion of the array from begin to end of the array and store in the new array object
	
	//for (indexes from begin to end of  old array)
	for(int i = begin; i <= newSize; i++)
	{
		//store contents in new array
		
		//create pointer that points to address of the new array
		char newVal = this->data_[i];
		
		//this pointer then holds the content of the index of old array
		newArray.data_[newIndex] = newVal;
		newIndex++;
		
	}
	
	//return the new Array	
	return newArray;
}
