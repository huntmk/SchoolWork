//Andrew Chittick
//Error Correction takes data codewords
//and produces error correction codewords

#include "errorCorrection.h"

void errorCorrection(std::string * codewords, int numCodeWords, int* errorCorrectionWords, int numErrorWords){
  //define functions
  //void codeToDecimal(std::string *, int, int *);
  //void getErrorWords(int*, int, int*, int);
  //run functions produces errorCorrectionWords
  int * decimalCodewords = new int[numCodeWords];
  codeToDecimal(codewords, numCodeWords, decimalCodewords);
  getErrorWords(decimalCodewords, numCodeWords, errorCorrectionWords, numErrorWords);
  delete [] decimalCodewords;
  return;
}

//Converts an 8 char string to a decimal size times; writes to decimalCodewords
void codeToDecimal(std::string * code, int size, int * decimalCodewords){
  int count, decimal;
  for (int i=0; i<size; i++){
    count = 7;
    decimal = 0;
    for(std::string::iterator it=code[i].begin(); it!=code[i].end(); ++it){
      if (*it == '1'){
        decimal = decimal + pow(2,count);
      }
      count--;
    }
    decimalCodewords[i] = decimal;
  }
  return;
}

//Generates errorCorrectionWords array
void getErrorWords(int * decimalCodewords, int numCodeWords, int * errorCorrectionWords, int numErrorWords){
  //initialize functions
  //int convertToAlpha(int);
  //int convertToInteger(int);
  //initialize variables
  int generatorOne[11] = {0, 251, 67, 46, 61, 118, 70, 64, 94, 32, 45};
  int generatorTwo[17] = {0, 120, 104, 107, 109, 102, 161, 76, 3, 91, 191, 147, 169, 182, 194, 225, 120};
  int generatorThree[27] = {0, 173, 125, 158, 2, 103, 182, 118, 17, 145, 201, 111, 28, 165, 53, 161, 21, 245, 142, 13, 102, 48, 227, 153, 145, 218, 70};
  int generatorSize = numErrorWords+1;
  int * generator;
  if (generatorSize == 11){
    generator = generatorOne;
  }
  else if (generatorSize == 17){
    generator = generatorTwo;
  }
  else{
    generator = generatorThree;
  }
  int steps = numCodeWords;
  int temp = 0;
  int alph = 0;
  //generate error correction words
  for (int index=0; index<numCodeWords; index++){
    //convert lead term to alpha
    alph = convertToAlpha(decimalCodewords[0]);
    //1a
    //multiply generator by lead term of result from prev step a
    for (int i=0; i<generatorSize; i++){
      temp = generator[i] + alph;
      //maintain Galois Field
      if (temp > 255){
        temp = temp % 255;
      }
      //1b XOR with result from prev step b
      temp = convertToInteger(temp);
      decimalCodewords[i] = temp ^ decimalCodewords[i];
    }
    //XOR overrun (result bigger than generator polynomial)
    for (int i = generatorSize; i<steps; i++){
      //step 1b: XOR
      decimalCodewords[i] = 0 ^ decimalCodewords[i];
    }
    //prepare the result
    for (int i = 0; i < steps; i++){
      //discard lead term
      decimalCodewords[i]= decimalCodewords[i+1];
    }
    //steps shrinks until it is same size as generator
    if (steps >= generatorSize){
      steps--;
    }
    else{
      decimalCodewords[numErrorWords] = 0;
    }
  }
  //save result to errorCorrectionWords
  for (int i=0; i<numErrorWords; i++){
    errorCorrectionWords[i] = decimalCodewords[i];
  }
  return;
}
int intIndex[] = {0,1,25,2,50,26,198,3,223,51,238,27,104,199,75,4,100,224,14,52,141,239,129,28,193,105,248,200,8,76,113,5,138,101,47,225,36,15,33,53,147,142,218,240,18,130,69,29,181,194,125,106,39,249,185,201,154,9,120,77,228,114,166,6,191,139,98,102,221,48,253,226,152,37,179,16,145,34,136,54,208,148,206,143,150,219,189,241,210,19,92,131,56,70,64,30,66,182,163,195,72,126,110,107,58,40,84,250,133,186,61,202,94,155,159,10,21,121,43,78,212,229,172,115,243,167,87,7,112,192,247,140,128,99,13,103,74,222,237,49,197,254,24,227,165,153,119,38,184,180,224,17,68,146,217,35,32,137,46,55,63,209,91,149,188,207,205,144,135,151,178,220,252,190,97,242,86,211,171,20,42,93,158,132,60,57,83,71,109,65,162,31,45,67,216,183,123,164,118,196,23,73,236,127,12,111,246,108,161,59,82,41,157,85,170,251,96,134,177,187,204,62,90,203,89,95,176,156,169,160,81,11,245,22,235,122,117,44,215,79,174,213,233,230,231,173,232,116,214,244,234,168,80,88,175};
int alphaIndex[] = {1,2,4,8,16,32,64,128,29,58,116,232,205,135,19,38,76,152,45,90,180,117,234,201,143,3,6,12,24,48,96,192,157,39,78,156,37,74,148,53,106,212,181,119,238,193,159,35,70,140,5,10,20,40,80,160,93,186,105,210,185,111,222,161,95,190,97,194,153,47,94,188,101,202,137,15,30,60,120,240,253,231,211,187,107,214,177,127,254,225,223,163,91,182,113,226,217,175,67,134,17,34,68,136,13,26,52,104,208,189,103,206,129,31,62,124,248,237,199,147,59,118,236,197,151,51,102,204,133,23,46,92,184,109,218,169,79,158,33,66,132,21,42,84,168,77,154,41,82,164,85,170,73,146,57,114,228,213,183,115,230,209,191,99,198,145,63,126,252,229,215,179,123,246,241,255,227,219,171,75,150,49,98,196,149,55,110,220,165,87,174,65,130,25,50,100,200,141,7,14,28,56,112,224,221,167,83,166,81,162,89,178,121,242,249,239,195,155,43,86,172,69,138,9,18,36,72,144,61,122,244,245,247,243,251,235,203,139,11,22,44,88,176,125,250,233,207,131,27,54,108,216,173,71,142,1};

int convertToAlpha(int index){
   return intIndex[index-1];
}
int convertToInteger(int index){
   return alphaIndex[index];
}
