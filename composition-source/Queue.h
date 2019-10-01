//==============================================================================
/**
 * Honor Pledge:
 *
 * I pledge that I have neither given nor received any help
 * on this assignment.
 */
//==============================================================================

#ifndef _CS507_QUEUE_H_
#define _CS507_QUEUE_H_

#include "Array.h"

#include <exception>

// COMMENT You should use aggregation instead of inheritance since logically
// a queue is not an array.

//RESPONSE: Instead of inheriting. I give the Queue class an object of array type called queue

/**
 * @class Queue
 *
 * Basic queue for abitrary elements.
 */
template <typename T>
class Queue
{
	public:
  /// Type definition of the type.
  typedef T type;

   /// Default constructor.
  Queue (void);

  /// Copy constructor.
  Queue (const Queue & q);

  /// Destructor.
  ~Queue (void);
  
  //Enqueue
  void enqueue(T element);
  
  //Dequeue
  T dequeue(void);
  
  //is_empty
  bool is_empty(void);
  
  //size
  size_t size(void)const;
  
  //clear
  void clear(void);
  
  private:
	Array<T> queue;
	size_t front;
	size_t back;
	
	
};

// include the inline files
#include "Queue.inl"

// include the source file since template class
#include "Queue.cpp"

#endif  // !defined _CS507_FIXED_ARRAY_H_
