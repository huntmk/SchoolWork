// -*- C++ -*-

// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

#ifndef _CS507_FIXED_ARRAY_H_
#define _CS507_FIXED_ARRAY_H_

#include "Array.h"

/**
 * @class Fixed_Array
 *
 * Implementation of a fixed size array, i.e., one that is not
 * resizeable. It is derived from Array so it can inherit of 
 * the Array class's methods.
 */
template <typename T, size_t N>
class Fixed_Array : public Array_Base <T>
{
public:
  /// Default constructor.
  Fixed_Array (void);

	/**
	 * Copy constructor.
	 *
	 * @param[in]      arr        Source array
	 */
  Fixed_Array (const Fixed_Array <T, N> & arr);
	
  // COMMENT This method should not be here since it was part of the original
  // bad design.
  
  //RESPONSE: Deleted method 

  /**
   * Initializing constructor. Fills the contents of the 
   * array with the specified \a fill value.
   *
   * @param[in]      fill       The file value.
   */
  Fixed_Array (T fill);

  /// Destructor.
  ~Fixed_Array (void);

  /**
   * Assignment operator
   *
   * @param[in]      rhs        Right-hand side of operator.
   */
  const Fixed_Array & operator = (const Fixed_Array <T, N> & rhs);
  
  // COMMENT This method should not be here since it was part of the original
  // bad design.
  
  //RESPONSE: Deleted method
  
  
  //COMMENT This method is a violation of LSP.

  //RESPONSE: Deleted method
  
  //redefine get function
  virtual T get (size_t index) const;
  
  //redefine set function
  virtual void set (size_t index, T value);
  
  //redefine find functions
  virtual int find (T element) const;
  
  virtual int find (T element, size_t start) const;
  
  //redefine fill method
  virtual void fill (T element);
  
   //return current size of array
  virtual size_t size (void) const;
  
  //return max size of array
  virtual size_t max_size (void) const;
  
  // COMMENT This method is a violation of LSP.
  
  //RESPONSE: Deleted method
   
 
  // COMMENT This should not be here since you are inheriting from the
  // base array class.
  
 //RESPONSE: I did not have these members in the array base class before. The array base has pure virtual methods.
 
 private:
	/// Pointer to the actual data.
	T * data_;

	/// Current size of the array.
	size_t cur_size_;

	/// Maximum size of the array.
	size_t max_size_;

};

// include the inline files
#include "Fixed_Array.inl"

// include the source file since template class
#include "Fixed_Array.cpp"

#endif  // !defined _CS507_FIXED_ARRAY_H_
