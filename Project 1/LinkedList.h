#ifndef LINKEDLIST_H_
#define LINKEDLIST_H_

struct node
{
	int data;
	node *next;
};

	class LinkedList
	{
	private:
		node * head, *tail;
	public:
	
	//add method
	void addNode(int, int);
	void displayList();
};
#endif //LINKEDLIST_H