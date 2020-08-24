#ifndef MATRIXFUN
#define MATRIXFUN

#include <string>

int **makeMatrix(std::string data, int version);
void addFinder(int **matrix, int **patterns, int cornerx, int cornery);
void setInfoAreas(int **matrix, int **patterns, int dimval);
void layoutData(int **matrix, int **patterns, std::string data, int dim, int ver);

#endif
