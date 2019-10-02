// $Id: Stack.cpp 827 2011-02-07 14:20:53Z hillj $

// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
// Stack
//

template <typename T>
Stack <T>::Stack (void):
top_element(0)
{
	//default constructor
	//other members intialized in base class
	
	//set members
	top_element = -1;
		
}

//
// Stack
//
template <typename T>
Stack <T>::Stack (const Stack & stack):
s(stack.s),
top_element(0)
{
	//copy constructor
	//set members of this object to stack's members
	top_element = stack.top_element;
		
}

//
// ~Stack
//
template <typename T>
Stack <T>::~Stack (void)
{
	//destructor called in base class
}

//
// push
//
template <typename T>
void Stack <T>::push (T element)
{
	//if top is equal to max size then it will overflow(so add 10 extra spaces to stack), 
	//else increment top and put on that new spot
	if (top_element >= s.max_size())
	{
		//Resize
		s.resize(s.max_size() + 10);
		
	}
	
	//increment top element to point to next spot on stack. then put element at top_element spot
	top_element++;
	s.set(top_element,element);
		
}

//
// pop
//
template <typename T>
void Stack <T>::pop (void)
{
	//throw empty exception if nothing is on stack
	if (top_element == -1)
	{
		throw empty_exception("Stack is empty. ");
		
	}
	
	//else decrement top_element to remove it
	else
	{
		top_element--;
		
	}
}

//
// operator =
//
template <typename T>
const Stack <T> & Stack <T>::operator = (const Stack & rhs)
{
	//assign rhs top element to current object's top element
	top_element = rhs.top_element;
	
	//this will call operator overload function with these two objects
	s = rhs.s;
}

//
// clear
//
template <typename T>
void Stack <T>::clear (void)
{
  // COMMENT Just reset the variables instead popping each element, which
  // is expensive. Also, your stack will fail if T cannot be assigned the
  // value 0.

  //RESPONSE: I called the destructor to delete the old heap memory then create a new array object and assign it to the previous array object. 
  
	//call destructor to delete any heap memory, then create new stack and assign to the previous stack
	s.~Array<T>();
	Array<T> newStack;
	
	s = newStack;
	
	//top element is set to -1
	top_element = -1;
		
}

