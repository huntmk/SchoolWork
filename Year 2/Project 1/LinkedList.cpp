#include "LinkedList.h"
#include "Tour.h"
#include <iostream>
using namespace std;

	//adds node to the linked list
	void LinkedList:: addNode(int n,int m)
	{
		node *temp = new node;
		temp->data = (n,m);
		
		temp->next = NULL;

		if (head == NULL)
		{
			head = temp;
			tail = temp;
		}
		else
		{
			tail->next = temp;
			tail = tail->next;
		}
	}
	
	
	//traverses through list and prints the data
	void LinkedList:: displayList()
	{ 
			
		node * temp = new node;
		temp = head;
		while (temp != NULL)
		{
			cout << temp->data + " " << endl;
			
			temp = temp->next;
		}
	}
	



