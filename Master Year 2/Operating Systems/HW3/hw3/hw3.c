#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

#define  STDIN_PIPE  0 /* pipe index for the fd to read from */
#define STDOUT_PIPE  1 /* pipe index for the fd to write to  */
/*
The parent process creates both pipe objects, the first, second, and third
 child processes.
   
*/

int main(int argc, char **argv)
{
	//must be exactly 3 command line arguments
	if(argc == 4)
	{
		
		int pipe_one[2]; /* pipe_one */
		int pipe_two[2]; /* pipe_two */
		int fork_rv1;
		
		/* Step 1: create a pipe_one */
		if ( pipe(pipe_one) == -1 )
		{
		  perror("pipe");
		}
		printf("pid=%d pipe one! It is file descriptors: { %d %d }\n", getpid(), pipe_one[0], pipe_one[1]);
		
		/* Step 1: create pipe_two */
		if ( pipe(pipe_two) == -1 )
		{
		  perror("pipe");
		}
		printf("pid=%d pipe two! It is file descriptors: { %d %d }\n", getpid(), pipe_two[0], pipe_two[1]);
		
		
		
		fork_rv1 = fork();    /* Step 2: create process_one */
		if ( fork_rv1 == -1 ) /* check for error */
		{
		  perror("fork");
		}
		else if ( fork_rv1 > 0 )  /* parent */
		{
			//second prcoess
			int fork_rv2;

			printf("pid=%d is the parent of child 1 with pid=%d\n", getpid(), fork_rv1);
			fork_rv2 = fork();    /* Step 3: create process_2 */
			if ( fork_rv2 == -1 ) /* check for error */
			{
			 perror("fork");
			}
			else if ( fork_rv2 > 0 ) /* parent */
			{
				//third prcoess
				int fork_rv3;

				printf("pid=%d is the parent of child 2 with pid=%d\n", getpid(), fork_rv2);
				fork_rv3 = fork();    /* Step 3: create another new process */
				if ( fork_rv3 == -1 ) /* check for error */
				{
					perror("fork");
				}
				else if(fork_rv3 > 0) /* parent */
				{
					int wait_rv;

					printf("pid=%d is the parent of child 3 with pid=%d\n", getpid(), fork_rv3);
					/* Step 4: parent calls close() on its pipe descriptors 3 & 4, and 5 & 6 */
					close(3);
					close(4);
					close(5);
					close(6);
					
					
					wait_rv = wait(NULL);
					printf("pid=%d is done waiting for %d. Wait returned: %d\n", getpid(), fork_rv1, wait_rv);
					wait_rv = wait(NULL);
					printf("pid=%d is done waiting for %d. Wait returned: %d\n", getpid(), fork_rv2, wait_rv);
					wait_rv = wait(NULL);
					
					printf("pid=%d is done waiting for %d. Wait returned: %d\n", getpid(), fork_rv3, wait_rv);
					printf("The pipelines is done with its work.\n");

				}
				else /* child 3 */
				{
					//close 3 & 4 since only want second pipe
					close(3);
					close(4);
					
					char *arglist[2];

					printf("pid=%d is child 3\n", getpid());
					/* child 3 calls close(6), close(0), dup(5), close(5) */
					close(6);
					close(0);
					dup(5);
					close(5);

					//get arguments
					arglist[0] = argv[1];
					arglist[1] = 0;
															
					execvp( argv[1], arglist );
					perror("execvp prog1");
					exit(1);
								
				}
			  }
			  else /* child 2 */
			  {
				char *arglist[2];

				printf("pid=%d is child 2\n", getpid());
				
				//handles input from first pipe
				/* child 2 calls close(4), close(0), dup(3), close(3) */
				close(4);
				close(0);
				dup(3);
				close(3);
				
				//handles output to second pipe
				/* child 2 calls close(5), close(1), dup(6), close(6) */
				close(5);
				close(1);
				dup(6);
				close(6);

				//get arguments
				arglist[0] = argv[2];
				arglist[1] = 0 ;
				execvp( argv[2] , arglist );
				perror("execvp prog2");
				exit(1);
				
				
			  }
		}
		else /* child 1 */
		{
			//close 5 & 6 since only want first pipe
			close(5);
			close(6);
			char *arglist[2];

			printf("pid=%d is child 1\n", getpid());
			
			/* Step 5: child 1 calls close(3), close(1), dup(4), close(4) */
			close(3);
			close(1);
			dup(4);
			close(4);
			
			//get arguments
			arglist[0] = argv[3];
			arglist[1] = 0;
			execvp( argv[3] , arglist );
			perror("execvp prog3");
			exit(1);
			
		}
		
	
	}
	else
	{
		//output error
		printf("must pass 3 command line arguments");
		
	}

	return 0;
}