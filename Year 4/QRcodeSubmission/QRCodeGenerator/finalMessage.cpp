#include <stdio.h>
#include <iostream>
#include <string>
#include "messagefun.h"

std::string makeFinalMessage(std::string* codewords, std::string* data, int numCode, int numErr)
{
	std::string finalStr = "";
	
	for(int i = 0; i < numCode; i++)
	{
		finalStr += data[i];
	}
	for(int i = 0; i < numErr; i++)
	{
		finalStr += codewords[i];
	}
	if(numCode == 28)
	{
		finalStr += "0000000";
	}
	return finalStr;
	

}
