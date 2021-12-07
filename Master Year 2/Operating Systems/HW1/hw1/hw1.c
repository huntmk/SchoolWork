/*
   Do NOT modify this file.
*/
#include "hw1.h"

int main()
{
   struct StructA structA = {41, 42.43, "cats"};
   struct StructB structB = {"pear", 51, 52.53};
   struct StructC structC = {62.63, "dogs", 61};

   printf("structA = {%d, %f, %s}\n", structA.x, structA.y, structA.z);
   printf("structB = {%s, %d, %f}\n", structB.x, structB.y, structB.z);
   printf("structC = {%f, %s, %d}\n", structC.x, structC.y, structC.z);

   struct StructB  structA2B = convertAtoB(structA);
   struct StructC *structA2C = convertAtoC(structA);
   struct StructC  structB2C;
   convertBtoC(&structB, &structB2C);
   struct StructA *structC2A;
   convertCtoA(structC, &structC2A);

   printf("structA2B =  {%s, %d, %f}\n", structA2B.x,  structA2B.y,  structA2B.z);
   printf("structA2C -> {%f, %s, %d}\n", structA2C->x, structA2C->y, structA2C->z);
   printf("structB2C =  {%f, %s, %d}\n", structB2C.x,  structB2C.y,  structB2C.z);
   printf("structC2A -> {%d, %f, %s}\n", structC2A->x, structC2A->y, structC2A->z);

   free(structA2C);
   free(structC2A);

   struct StructA arrayA[] = {{1, 11.12, "abcd"},
                              {2, 12.13, "efgh"},
                              {3, 13.14, "ijkl"},
                              {4, 14.15, "mnop"}};
   struct StructB arrayB[] = {{"qrst", 5, 15.16},
                              {"uvwx", 6, 16.17},
                              {"yz@#", 7, 17.18}};

   struct StructC * arrayC;
   bundleIntoC(arrayA, 4, arrayB, 3, &arrayC);

   for (int i = 0; i < 7; ++i)
   {
      printf("arrayC[%d] = {%f, %s, %d}\n", i, arrayC[i].x, arrayC[i].y, arrayC[i].z);
   }

   free(arrayC);

   return 0;
}
