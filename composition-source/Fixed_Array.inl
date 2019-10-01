// -*- C++ -*-
// $Id: Array.inl 828 2011-02-07 14:21:52Z hillj $

// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
// size
//
template <typename T, size_t N>
inline
//return current size of array
size_t Fixed_Array <T,N>::size (void) const
{
	return this->cur_size_;
}


//
// max_size
//
//return max size of array
template <typename T, size_t N>
inline
size_t Fixed_Array <T,N>::max_size (void) const
{
	return this->max_size_;
}


