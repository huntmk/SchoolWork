#include "Array.h"

int main (int argc, char * argv [])
{
  // TODO Add code here to test your Array class.
	//construct an array
	Array nArray;
	Array wArray(5);
	Array zArray(10 , 'a');
	//Array wArray = zArray
	//wArray[3] = 'C';
	
		
	//zArray.get(11);
	
	
	
	
	//zArray[3];
	
	//zArray.set (4, 'J');
	//assignment operation (works)
	//nArray = wArray;
	
	zArray.resize(12);
	//set value at specified index
	
	//zArray.find('a');
	
	
		
	//zArray.find('a',12);
	
	
	
	//Get seg fault error
	
	//zArray == wArray;
	
	//wArray.fill('b');
	
	zArray.shrink();
	
	//zArray.reverse();
	
	//FIX SLICE METHODS
	//zArray.slice(1);
	
	//wArray.slice(1,4);
	std::cout << "worked" << std::endl;
	
	
	//set method (works)
		
  
  return 0;
}
