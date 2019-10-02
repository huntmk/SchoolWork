// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk

import java.util.Scanner;
//Main Function
public class Driver {
	public static void main(String [] args)
	{
		//Declare array of objects to store Players
		Player[]team = new Player[11];
		// Declare input to enter what User wants to do with the Program
		Scanner input = new Scanner(System.in);
		//Num represents number of times a Player has been created
		int num = 0;
		//i represents index in team array
		int i = 0;
		
		//Decision is what will hold the input of user
		String decision;
		//Prints menu
		System.out.println("Welcome to our CSCI 240 Roster");
		team[i] = new Player();
		team[i].printMenu();
		decision = input.nextLine();
		
		// while the decision doesn't equal three,
		while(!decision.equals("3")){
			//if decision equals 1 call addPlayer function to create new player for that spot
			if(decision.equals("1")){
				// check if array of objects is full
				if (num >= 11)
				{
					System.out.println("Sorry you can only have a max of 11 players, Enter another option: ");
					decision = input.nextLine();
				}
				else{
				team[i].addPlayer();
				// increase index and num by 1 after a player is created
				i++;
				num++;
				team[i] = new Player();
				System.out.println();
				//Ask what user wants to do after that
				team[i].printMenu();
				decision = input.nextLine();
				}
			}
			//if decision equals 2 then call showPlayer function to display the team created so far 
			if(decision.equals("2")){
				//for loop will print out the players created
				System.out.println(" *** Roster ***");
				for (i = 0; i < num; i++) {
					//call function to show player
					System.out.println(team[i].showPlayer());
					}
				System.out.println();
				team[i].printMenu();
				decision = input.nextLine();
			}
			// if users enters wrong input tell them to enter correct option
			else{
				System.out.println("Wrong input, enter correct option");
				decision = input.nextLine();
			}
			
		}
		
	}	
			


}

		
			
		

