// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk

import java.io.*;
import java.util.Scanner;
public class Driver
{
	public static void main (String[] args)
	{	
		//create stack of Student type with size of 10
		Student menu = new Student();
		Stack<Student> stackStudent = new Stack<Student>(10);
		//allows user to enter the input
		menu.printMenu();
		Scanner input = new Scanner(System.in);
		int decision = input.nextInt();
		
		// while decision does not equal 3
		while(decision != 3){
			
			//if decision equals 1, load students from file
			if(decision == 1){
				try{
					// create buffered reader to read each line of students.txt
					BufferedReader buffer = new BufferedReader(new FileReader("students.txt"));
				
					//read first line in file
					String line = buffer.readLine();
								
					while (line != null)
					{
						
						
						//breaks the parts of the line by using the delim ',' 
						String[] part = line.split( ",");
						// pass each parts to the constructor for what they represent
						Student stud = new Student(part[0],part[1],part[2],part[3],part[4],part[5],part[6],part[7],part[8]);
						
						stackStudent.push(stud);
						//read the next line
						line = buffer.readLine();
						
				
					}
				//close the buffer to stop reading  the file
				buffer.close();
				}
				//if file is not found, print IO Exception
				catch(IOException e) {
					System.out.print("IO Exception ");
				}
				//ask for new decision for input
				menu.printMenu();
				decision = input.nextInt();
			}
			//if decision equals 2, print students from stack
			if(decision == 2){
				//pop the contents of the line when is not full.
				while (!stackStudent.isEmpty()){
					System.out.println(stackStudent.pop().format());
				}
					
				// enter new input
				menu.printMenu();
				
				decision = input.nextInt();
			}
			//else output "Incorrect input", ask user to enter again
			else{
				System.out.println("Wrong input, enter correct option");
				menu.printMenu();
				decision = input.nextInt();
			}
			
		}
		
	}
}