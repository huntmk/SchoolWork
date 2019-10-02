/*
* Stack.h
*
*  Created on: Feb 7, 2018
*      Author: Cellus
*/
#include "Stack.h"
#include<iostream>
using namespace std;


//if top is greater than or equal to 64 than its a stack over flow
bool Stack::push(int p[])
{
	if (topElement >= 64)
	{
		cout << "Too much in Stack";
		return false;
	}
	else
	{
		stack[topElement++] = *p;
		return true;
	}
}
//if top is less than 0 than there is a stack underflow
int Stack::pop()
{
	if (topElement < 0)
	{
		cout << "Stack has negative number of elements in it";
		return 0;
	}
	else
	{
		int x = stack[topElement--];
		return x;
	}
}

bool Stack::isEmpty()
{
	return (topElement < 0);

}


