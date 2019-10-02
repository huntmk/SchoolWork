#include <ctime>
#include <iostream>
#include<stdlib.h>
#include "Tour.h"
#include "Stack.h"

using namespace std;
int main()
{
	srand(time(NULL));
	Tour * run = new Tour();
	cout << "Welcome to the Knight's Tour!! Lets Begin." << endl;
	
	run->tourFunct();
	
	return 0;
}
