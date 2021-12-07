This program demonstrates the use of multithreads and using them to calculate prime numbers from 1 to 10,000

I created three different types of thread classes based on the how many threads are used:
One thread
100 threads
1,000 threads

I created a basic function to also calculate the prime numbers called primeCalc(). This is inside all three thread classes' run functions.

In the main function is where the threads are ran.
For the single thread I just created the object and called it's run method.

For the other two. I created a loop for creating and starting each thread and the loop only ran for as many threads I needed to complete the program
So for the 100 threads, a for loop cycled through 100 times. And for the 1,000 threads it did this 1,000 times. The code currently runs the 1,000 thread version but you can comment and un-comment code in the main function to test the others.

Recorded times:
One thread: It calcualted the numbers almost instantaneously.
100 threads: This completed in about 2.3 seconds
1,000 threads:  This took about 19.6 seconds to compile

It seems as more threads are used the more time the job takes.