#include <iostream>
#include "matrixfun.h"
using namespace std;


int **makeMatrix(std::string data, int version)
{
    int dim = (((version-1)*4)+21);
    int **matrix;
	matrix = new int*[dim];
	for(int i = 0; i < dim; i++)
		matrix[i] = new int[dim];

    for (int i = 0; i < dim; i++)
    {
        for (int j = 0; j < dim; j++)
        {
            matrix[i][j] = 0;
        }
    }
	int **patterns;
	patterns = new int*[dim];
	for(int j = 0; j < dim; j++)
		patterns[j] = new int[dim];

    for (int i = 0; i < dim; i++)
    {
        for (int j = 0; j < dim; j++)
        {
            patterns[i][j] = 0;
        }
    }
    
	//Set finder patterns
	addFinder(matrix, patterns, 0, 0);
	addFinder(matrix, patterns, (((version-1)*4)+21) - 7, 0);
	addFinder(matrix, patterns, 0, (((version-1)*4)+21) - 7);
	for(int i = 0; i < 8; i++)
	{
	    for(int j = 0; j < 8; j++)
	    {
	        patterns[i][j] = 1;
	        patterns[(((version-1)*4)+21) - 8 + i][j] = 1;
	        patterns[i][(((version-1)*4)+21) - 8 + j] = 1;
	    }
	}
    if (version == 2)
	{
		for(int i= 0; i < 5; i++)
		{
			matrix[16 + i][16] = 1;
			matrix[16][16 + i] = 1;
			matrix[20][16 + i] = 1;
			matrix[16 + i][20] = 1;
		}
        matrix[18][18] = 1;
		for(int i= 0; i < 5; i++)
		{
			for(int j= 0; j < 5; j++)
		    {
			    patterns[16 + i][16 + j] = 1;
		    }
		}
	}
	//Add timing patterns
	int timePat = dim - 7;
	for(int i = 8; i < dim - 7; i = i+2)
	{
	    matrix[i][6] = 1;
	    matrix[6][i] = 1;
	}
	for(int i = 8; i < dim - 7; i++)
	{
	    patterns[i][6] = 1;
	    patterns[6][i] = 1;
	}
	//Add dark module
	int darkx = (4 * version) + 9;
	matrix[darkx][8] = 1;
	patterns[darkx][8] = 1;
	//Reserve format info with value 2 (format)
	setInfoAreas(matrix, patterns, dim - 1);
    	layoutData(matrix, patterns, data, dim - 1, version);

	
	return matrix;
}
void addFinder(int **matrix, int **patterns, int cornerx, int cornery)
{
	//map[cornerx][cornery] = 1;
	for(int i=0; i < 7; i++)
	{
		matrix[cornerx + i][cornery] = 1;
		matrix[cornerx][cornery + i] = 1;
		matrix[cornerx + 6][cornery + i] = 1;
		matrix[cornerx + i][cornery + 6] = 1;
	}
	for(int j=2; j < 5; j++)
	{
		for(int k=2; k < 5; k++)
		{
			matrix[cornerx + j][cornery + k] = 1;
		}
	}
	//Designate area of the patterns as a patterns in patterns array
	for(int i=0; i < 7; i++)
	{
		for(int j=0; j < 7; j++)
		{
		    patterns[cornerx + i][cornery + j] = 1;
		}
	}
}
void setInfoAreas(int **matrix, int **patterns, int dimval)
{
    for(int i = 0; i < 7; i++)
	{
	   matrix[dimval - i][8] = 0;
	   patterns[dimval - i][8] = 1;
	}
	for(int k = 0; k < 8; k++)
	{
	   matrix[8][dimval - k] = 0;
	   patterns[8][dimval - k] = 1;
	}
	for(int i = 0; i < 6; i++)
	    {
	        matrix[i][8] = 0;
	        matrix[8][i] = 0;
	        patterns[i][8] = 1;
	        patterns[8][i] = 1;
	    }
	for(int j = 7; j < 9; j++)
	{
	    matrix[j][8] = 0;
	    matrix[8][j] = 0;
	    patterns[j][8] = 1;
	    patterns[8][j] = 1;
	}
}
void layoutData(int **matrix, int **patterns, std::string data, int dim, int ver)
{
    int leftx = dim - 1;
    int rightx = dim;
    int currenty = dim;
    //Direction - -1 if sorting upwards, 1 if sorting downwards
    int direction = -1;
    int totalBits;
    if(ver == 1)
    {
        totalBits = 128;
    }
    else
    {
        totalBits = 224;
    }
    int dataBits = 0;
    totalBits = data.length() - 1;


    int col = 0;
    while (dataBits < totalBits)
    {


        if(patterns[rightx][currenty] == 0)
        {
            matrix[currenty][rightx] = int(data.at(dataBits));
            dataBits++;
        }
        if(patterns[leftx][currenty] == 0)
        {
            matrix[currenty][leftx] = int(data.at(dataBits));
            dataBits++;
        }
        if(currenty == 0 && direction == -1)
        {
            direction = 1;
            rightx = rightx - 2;
            leftx = leftx - 2;
            col++;
        }
        else if(currenty == dim && direction == 1)
        {
            direction = -1;
            rightx = rightx - 2;
            leftx = leftx - 2;
            col++;
        }
        else
        {
            currenty = currenty + direction;
        }
        if (rightx == 6)
        {
            rightx -= 1;
            leftx -= 1;
        }
        if (leftx < 0)
        {
            dataBits = totalBits;
        }
    }
    for (int i = 0; i < dim + 1; i++)
    {
        for (int j = 0; j < dim + 1; j++)
        {
            if (matrix[i][j] == 48 || matrix[i][j] == 49)
            {
                matrix[i][j] -= 48;
            }
        }
    }

}
