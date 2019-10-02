#ifndef STACK_H_
#define STACK_H_

class Stack {
	int topElement;
public:
	//size of stack
	int stack[64];

	Stack() { topElement = -1; }
	//push method(add to the stack)
	bool push(int p[]);
	//pop method(remove from the stack)
	int pop();
	//check if the stack is empty
	bool isEmpty();
}; 
#endif //STACK_H