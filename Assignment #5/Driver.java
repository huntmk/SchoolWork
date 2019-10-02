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
		//arrays for each type of Employee
		StaffPharmacist managers [] = new StaffPharmacist[1];
		StaffTechnician seniors [] = new StaffTechnician[1];
		Employee staffPhar [] = new Employee[1];
		Employee staffTech [] = new Employee[1];
		
		//StaffPharmacist pharmacists 
		System.out.println("1. Load Employees (From File)" );
		System.out.println("2. Exit Program");
		System.out.println();
		System.out.println( "Enter your selection: ");
		Scanner input = new Scanner(System.in);
		String decision = input.nextLine();
		while(!decision.equals("2")){
			//if decision equals 1, load players
			if(decision.equals("1")){
				//load employees
				try{
					// create buffered reader to read each line of students.txt
					BufferedReader buffer = new BufferedReader(new FileReader("employees.txt"));
				
					//read second line in file
					buffer.readLine();
					String line = buffer.readLine();
					while (line != null)
					{
						//break line into parts
						String[] part = line.split(",");
						//change employeeId in part[1] to int
						int employeeId = Integer.parseInt(part[1]);
						//If the first part of line(role id) equals its specified role, assign it to the type that corresponds with its role
						if (part[0].equals("1")){
							StaffPharmacist employee1 = new PharmacyManager(employeeId,part[2],part[3]);
							managers[0] = employee1;
						}
						else if (part[0].equals("2")){
							Employee employee2 = new StaffPharmacist(employeeId,part[2],part[3]);
							staffPhar[0] = employee2;
						}
						else if (part[0].equals("3")){
							Employee employee3 = new StaffTechnician(employeeId,part[2],part[3]);
							staffTech[0] = employee3;
						}
						else if (part[0].equals("4")){
							StaffTechnician employee4 = new SeniorTechnician(employeeId,part[2],part[3]);
							seniors[0] = employee4;
						}
						line = buffer.readLine();
					}
					//close the buffer to stop reading  the file
					buffer.close();
					System.out.println("File Successfully Loaded! ");
					System.out.println();
				}
				//if file is not found, print IO Exception
				catch(IOException e) {
					System.out.print("File was not found. ");
				}
				//Sub menu
				System.out.println("1. Print Employee Information ");
				System.out.println("2. Enter Hours Worked ");
				System.out.println("3. Calculate Paychecks ");
				System.out.println();
				System.out.println("4. Exit Program ");
				System.out.println("Enter Your Selection: ");
				//Ask for new input for sub menu
				Scanner newInput = new Scanner(System.in);
				String subDecision = newInput.nextLine();
				//Ask for hours
				Scanner hoursInput = new Scanner(System.in);
				int hours = 0;
				
				// while subDecision doesn't equal 4
				while(!subDecision.equals("4"))
				{
					if (subDecision.equals("1")){
						// loop through arrays and print out the employees in each array
						for (int i = 0; i < managers.length; i++) {
							System.out.println(managers[i].format());
						}
						for (int a = 0; a <staffPhar.length; a++) {
							System.out.println(staffPhar[a].format());
						}
						for (int b = 0; b < staffTech.length; b++) {
							System.out.println(staffTech[b].format());
						}
						for (int c = 0; c < seniors.length; c++) {
							System.out.println(seniors[c].format());
						}
						//Sub Menu
						System.out.println("1. Print Employee Information ");
						System.out.println("2. Enter Hours Worked ");
						System.out.println("3. Calculate Paychecks ");
						System.out.println();
						System.out.println("4. Exit Program ");
						System.out.println("Enter Your Selection: ");
						//ask for new input
						subDecision = newInput.nextLine();
					}
					//ask user to enter hours worked for employees
					else if (subDecision.equals("2")){
						System.out.println("Enter Hours for Employees");
						hours = hoursInput.nextInt();
						System.out.println("Hours Entered! ");
						//new input
						System.out.println("1. Print Employee Information ");
						System.out.println("2. Enter Hours Worked ");
						System.out.println("3. Calculate Paychecks ");
						System.out.println();
						System.out.println("4. Exit Program ");
						System.out.println("Enter Your Selection: ");
						subDecision = newInput.nextLine();
						
					}
					//calculate the employees paycheck based on how much they worked
					else if (subDecision.equals("3")){
						//if hours is not a valid number, print error statement and ask to user to put a new decision in
						if (hours <= 0){
							System.out.println("Hours needs to be entered and must be greater than 0. ");
							System.out.println("1. Print Employee Information ");
							System.out.println("2. Enter Hours Worked ");
							System.out.println("3. Calculate Paychecks ");
							System.out.println();
							System.out.println("4. Exit Program ");
							System.out.println("Enter Your Selection: ");
							subDecision = newInput.nextLine();
						//if hours is valid, print out employees paychecks
						}else{
							//loop through arrays to print out all of the employees paychecks
							for(int x = 0; x < managers.length; x++){
								System.out.println("ID: " + managers[x].getId() + "\t" + "Check Amount: " + managers[x].getHourlyRate() * hours);
							}
							for(int y = 0; y < seniors.length; y++){
								System.out.println("ID: " + seniors[y].getId() + "\t" + "Check Amount: " + seniors[y].getHourlyRate() * hours);
							}
							for(int z = 0; z < staffPhar.length; z++){
								System.out.println("ID: " + staffPhar[z].getId() + "\t" + "Check Amount: " + staffPhar[z].getHourlyRate() * hours);
							}
							for(int a = 0; a < staffTech.length; a++){
								System.out.println("ID: " + staffTech[a].getId() + "\t" + "Check Amount: " + staffTech[a].getHourlyRate() * hours);
							}
							System.out.println("1. Print Employee Information ");
							System.out.println("2. Enter Hours Worked ");
							System.out.println("3. Calculate Paychecks ");
							System.out.println();
							System.out.println("4. Exit Program ");
							System.out.println("Enter Your Selection: ");
							subDecision = newInput.nextLine();
						}
						// if input is not 1,2,3, or 4, print error statement and ask for new input
					}else{
					System.out.println("Wrong input, enter correct option: ");
					subDecision = newInput.nextLine();	
							
					}
				}
				//When sub menu breaks loop, break main loop to end program
				decision = "2";
			//if decision is not 1 or 2, print error statement and ask for new decision	
			}else{
					System.out.println("Wrong input, enter correct option: ");
					decision = input.nextLine();
			}
		}	
	}
}