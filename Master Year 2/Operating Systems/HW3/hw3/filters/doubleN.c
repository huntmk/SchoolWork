/*
   This program reads one character at a time from standard input,
   and then writes each character N times to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[])
{
   int i;
   char c;
   int n = 2;  // default value for n
   // Check for a command line argument.
   if (argc > 1)
   {
      n = atoi(argv[1]);
      if (n <= 0) n = 2;
   }

   while ( (c = getchar()) != EOF )
   {
      if ( c != 10 && c != 13 )  // don't double LF or CR
      {
         for (i = 0; i < n; i++)
         {
            printf("%c", c);
         }
      }
      else
      {
         printf("%c", c);
         fflush(stdout);  // try commenting this out
      }
   }
   return 0;
}