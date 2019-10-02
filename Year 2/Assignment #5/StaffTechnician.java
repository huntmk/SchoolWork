// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
//Staff Technician
public class StaffTechnician extends Employee
{
	//degree is a trait of staff technicians
	protected boolean degree = true;
	//default constructor
	public StaffTechnician(int id, String first, String last){
		super(id,first,last);
	}
	//overridden method, set hourlyRate to $20 an hour
	public double getHourlyRate(){
		
		hourlyRate = 20.0;
		return hourlyRate;
	}
	//call format method from Employee and add degree
	public String format(){
		return (super.format() + " Has Degree: " + degree + "\t");
	}
	
}