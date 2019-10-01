// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
// Queue
//
template <typename T>
Queue<T>::Queue (void): 
front(0), 
back(0)
{
	//default constructor
	//other members initialized in base class
	
	//set values of members
	front = -1;
	back = -1;
	
}

 template <typename T>
Queue<T>::Queue(const Queue & q):
queue(q.queue),
front(0), 
back(0)
{
	//copy constructor
	//set members of this object to those of the q object
	front = q.front;
	back = q.back;
	
}

template <typename T>
Queue<T>::~Queue(void)
{
	//destructor called in base class
}
  
template <typename T>
void Queue<T>::enqueue(T element)
{
	//Enqueue - insert element into queue
	
  // COMMENT The queue should grow if it out of space.

  // COMMENT The queue will fail if the array runs out of space. Since this
  // is an unboudned queue, you need to resize the array to make space for
  // new elements. Also, make sure you do not waste any space if you must
  // resize the array to accommodate new elements. This will require updates
  // to your dequeue method.
  
  //RESPONSE: I resize array to 10 more than the original size and removed
  //the else statement so after the resize it can go back to adding an element
  //to the stack without any problem and indexes properly for dequeue.
  
  
	//if queue is empty, resize the array by adding 10 more spaces. , else // increment back and insert data at rear
	if (back == queue.max_size() -1)
	{
		queue.resize(queue.max_size() + 10);
		
	}
	//if front isn't in array then set it to 0 which is the first value that would be removed
	if (front == -1)
	{
		front = 0;
		
	}
	//this value is incremented and put in the back of the queue
	back++;
	queue.set(back, element);
	
}
	

  
template <typename T>
T Queue<T>::dequeue(void)
{
	//Dequeue - delete element at the front of the queue
	//if queue is empty(front equals back), throw an exception, else just increment front
	if (is_empty())
	{
		throw "Queue is empty. ";
		
	}
	
	//if front and back have same value(pointing to last value), then set both to -1 because now it would be empty after this call
	else if (front == back)
	{
		front = -1;
		back = -1;
		
	}
	//return data at front of the queue and then implement	
	else
	{
		return queue.get(front);
		front++;
		
	}
	
}
  
template <typename T>
bool Queue<T>::is_empty(void)
{
	//return true if front and back values are -1
	return (this->front == -1 && this->back==-1);
	
}

template <typename T>
void Queue<T>::clear(void)
{
  // COMMENT This works, but is a very expensive operation. You should find a
  // why to just reset the state without having the dequeue every element.
  
  // COMMENT Your queue will no compile if T cannot be assigned the value 0.
  
  //RESPONSE call array destructor to delete memory and then create an array newQueue and assign it to queue
  
	queue.~Array<T>();
	Array<T> newQueue;
	
	queue = newQueue;
	//reset front and back to value of "-1"
	this->front = -1;
	this->back = -1;
	
}

