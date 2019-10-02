// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk

import java.util.Scanner;
import java.io.*;
public class Player
{
	// declare private attributes
	private String first;
	private String last;
	private int jerseyNum;
	public Player()
	{
	
	}
	//addPlayer functionn
	public void addPlayer()
	{
		// create input for user to enter attributes for Player
		Scanner user_input = new Scanner(System.in);
		
		// each time called create new player
		System.out.println("Please enter a first name: ");
		this.first = user_input.next();
		System.out.println();
		
		System.out.println("Please enter a last name: ");
		this.last = user_input.next();
		System.out.println();
		
		System.out.println("Please enter a number (1-99)");
		this.jerseyNum = user_input.nextInt();
		
	}
	//showPlayer function
	public String showPlayer()
	{
		// return string format for Player
		return (jerseyNum + ")" + " " + first + " " + last);
	}
	//printMenu function
	public void printMenu()
	{
		//shows user what to enter for options
		System.out.println("1) Add New Player" );
		System.out.println("2) View Player(s)") ;
		System.out.println("3) End Program");
		System.out.println();
		System.out.println( "Please enter your selection: ");
	}
}