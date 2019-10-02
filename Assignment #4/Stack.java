// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
import java.io.*;
public class Stack<E> 
{
	// Points to the top of the Stack.
	private int top; 
	// The size of our stack.
	private int size;
	// Array to emulate the Stack. 
	private E[] stack ;
	// Define Constructor(s)/Methods here
	// Our Constructor
public Stack(int stackSize)
{ 
	if(size > 0)
{
	size = stackSize; 
	}
else
{
	System.out.println("Invalid Stack size!");
	System.out.println("Using default size of 10");
	size = 10;
	} 
	// Here we create a generic array instance.
	stack = (E[]) new Object[size]; 
	top = -1;
	}
	public boolean isEmpty()
	{ 
		return top == -1; 
	}
	public boolean isFull()
	{
		return top == size;
	}
	public E getTop() 
	{
		return stack[top];
	}
	
public void push(E dataElement){ 
// Check to see if the Stack is full – if not push the element.
	if(isFull())
	{ 
	// We should really throw an exception here...
	System.out.println("Your Stack is full!");
	} 
	else
	{ 
	// “Push” the data element onto the Stack.
	stack[++top] = dataElement; 
	}
 }
 public E pop(){ 
// Check to see if the Stack is empty.
	if(!isEmpty()){
// Return the popped data element. 
		return stack[top--]; 
	}
	else 
	{
	// We should really throw an exception here... 
	System.out.println("Stack is empty – cannot pop!"); 
	return(null);
		}
	}
}