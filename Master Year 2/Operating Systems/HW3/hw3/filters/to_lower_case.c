/*
   This program reads lines from standard input, converts all
   the letters to lower case, and writes them to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
#include <stdio.h>

int main()
{
   char c;
   while ( (c = getchar()) != EOF )
   {
      if ( 'A' <= c && c <= 'Z' )
      {
         printf("%c", c + 32);
      }
      else
      {
         printf("%c", c);
         fflush(stdout);  // try commenting this out
      }
   }
   return 0;
}
