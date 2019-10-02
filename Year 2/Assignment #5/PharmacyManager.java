// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
//Pharmacy Manager
public class PharmacyManager extends StaffPharmacist
{
	//trait of pharmacy managers
	protected boolean leadership = true;
	
	//default constructor
	public PharmacyManager(int id, String first, String last)
	{
		//call and use default constructor from StaffPharmacist
		super(id,first,last);
		
	}
	//overridden method, set Pharmacy manager hourlyRate to $50 an hour
	public double getHourlyRate(){
		
		hourlyRate = 50.0;
		return hourlyRate;
	}
	//call format method from StaffPharmacist and add leadership 
	public String format(){
		return(super.format() + "Has Leadership: " + leadership);
		
	}	
}
	