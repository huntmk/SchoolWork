// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
//Employee class
public class Employee{
	//traits of employees that are passed to employee types
	protected int id;
	protected String first;
	protected String last;
	protected double hourlyRate;
	
	//default constructor
	public Employee(){
		
	}
	//overloaded constructor that pharmacists and technicians will use
	public Employee(int id, String first, String last)
	{
		this.id = id;
		this.first = first;
		this.last = last;
	}
		//return id of Employee
		public int getId()
	{
		return id;
	}
	//this method will be passed on and reset in the employee types
	public double getHourlyRate()
	{
		hourlyRate = 0;
		return hourlyRate;
	}
	//format method for Employees
	public String format(){
		return ("ID: " + id + "\t" + "Name: " + first + " " + last + "\t" + "Rate: " + this.getHourlyRate());
	}
		
	
	}