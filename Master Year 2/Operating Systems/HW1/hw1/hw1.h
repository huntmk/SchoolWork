/*
	Course: CS 51520
	Name: Marcellus Hunt
	Email: mkhunt@pnw.edu
	Assignment: 1

*/
#include <stdio.h>
#include <stdlib.h>

struct StructA{
   int x;
   double y;
   char z[5];
};

struct StructB{
   char x[5];
   int y;
   double z;
};

struct StructC{
   double x;
   char y[5];
   int z;
};


struct StructB
convertAtoB(struct StructA structA){
   
   struct StructB b;
   
   for (int i =0; i< 5; i++)
   {
      //char array allocation 
      b.x[i] = structA.z[i];
   }
   
   //int allocation
   b.y = structA.x;
   
   //double allocation
   b.z = structA.y;

   //return copy
   return b;
}


// NOTE: The return structure should be allocated by convertAtoC().
struct StructC *
convertAtoC(struct StructA structA){

   static struct StructC c;
   
   for (int i =0; i< 5; i++)
   {
      //char array allocation
      c.y[i] = structA.z[i];
   }
   //int allocation
   c.z = structA.x;
   
   //double allocation
   c.x = structA.y;

   //return address because return type is a pointer
   return &c;
}


void
convertBtoC(const struct StructB * structB,
                  struct StructC * structC){
   
   //(both parameters are pointers so '->' is used to point to field
   // structB is a const struct so it cannot be altered and is strictly an input
   
   //double allocation 
   structC->x = structB->z;
  
   //char array allocation
   for (int i =0; i< 5; i++)
   {
      structC->y[i] = structB->x[i];    
   }
   
   //int allocation
   structC->z = structB->y;
   
}


// NOTE: The return structure should be allocated by convertCtoA().
void
convertCtoA(struct StructC structC,
            struct StructA ** structA){   
   
   //structA is pointer to pointer, so this must be dereferenced and allocated by the callee (this function)
   *structA = (struct StructA *)malloc(sizeof(struct StructA));
   
   //double allocation
   (*structA)->y = structC.x;
  
   //char array allocation
   for (int i =0; i< 5; i++)
   {
      (*structA)->z[i] = structC.y[i];    
   }
   
   //int allocation
   (*structA)->x = structC.z;
   
}


// NOTE: The return array should be allocated by bundleIntoC().
void
bundleIntoC(const struct StructA *  arrayA, int n,
            const struct StructB *  arrayB, int m,
                  struct StructC ** arrayC_p){
   
   //int n = number of elements in arrayA
   //int m = number of elements in arrayB
      
   //structC is pointer to pointer, so this must be dereferenced and allocated by the callee (this function)
   
   // (*arrayC_p) = pointer to StructC
   *arrayC_p = (struct StructC *)malloc((n+m)*sizeof(struct StructC));
      
   
   //Convert StructA to StructC and allocate to pointer
   for (int x = 0; x < n ; x++)
   {
      //dereference pointer to StructC to hold StructC value at array position , also dereference pointer returned by convertAtoC to get value
      
      *(*arrayC_p+x) = *(convertAtoC(arrayA[x]));
   }
   
   //Convert StructB to StructC and allocate to pointer
   //Loop through remainder of arrayC_p contents
   for (int y = n; y < n+m; y++)
   {      
      //call function convertBtoC
      convertBtoC(&(arrayB[y-n]), (*arrayC_p+y));
      
   }
   
}
