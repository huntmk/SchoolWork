/*
   This program reads one character at a time from standard input,
   shifts the character by one place in the ASCII table, and then
   writes the new character to standard output.

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
      if ( c != 10 && c != 13 ) // don't change LF or CR
      {
         c++;
         printf("%c", c);
      }
      else
      {
         printf("%c", c);
         fflush(stdout);  // try commenting this out
      }
   }
   return 0;
}
