// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
//Staff Pharmacist
public class StaffPharmacist extends Employee
{
	//trait of all staff pharmacists
	protected boolean licensed = true;
	//default constructor
	public StaffPharmacist(int id, String first, String last){
		super(id,first,last);
	}
	//overridden method, set hourlyRate to $40 an hour
	public double getHourlyRate(){
		 
		hourlyRate = 40.0;
		return hourlyRate;
	}
	//call format method from Employee and add licensed 
	public String format(){
		return (super.format() + " Licensed: " + licensed + "\t");
	}
	
}