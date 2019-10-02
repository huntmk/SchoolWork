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
public class Student
{
	// declare private attributes
	private String first;
	private String last;
	protected Address home;
	private String line1;
	private String line2;
	private String newCity;
	private String newState;
	private String zipCode;
	private String id;
	private String gpa;
	// fix addresses
	//default Constructor
	public Student(){
		
	}
	
	//overloaded constructor
	public Student(String first, String last,String line1,String line2, String newCity, String newState, String zipCode, String id, String gpa)
	{
		//intialize fields
		this.first = first;
		this.last = last;
		this.line1 = line1;
		this.line2 = line2;
		this.newCity = newCity;
		this.newState = newState;
		this.zipCode = zipCode;
		this.id = id;
		this.gpa = gpa;
		//create new Address using fields passed to the student constructor
		home = new Address(this.line1,this.line2,this.newCity, this.newState, this.zipCode);
		
	}
	
	//format method
	public String format()
	{
		// return the Students in this format
		return ("ID: " + id + "\t" + "Name: " + first + " " + last + "\t" + "Address: " + home.getAddress() + "\t" + "GPA: " + gpa);
		
	}
	public void printMenu()
	{
		//shows user what to enter for options
		System.out.println("1. Load Students (From File)" );
		System.out.println("2. Print Stack") ;
		System.out.println("3. Exit Program");
		System.out.println();
		System.out.println( "Enter your selection: ");
	}
	
}
	//methdod for the menu??