// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
//Senior Technician
public class SeniorTechnician extends StaffTechnician
{
	//trait of senior technicians
	protected boolean serviceAward = true;
	//default constructor
	public SeniorTechnician(int id, String first, String last)
	{
		// call and use default constructor from StaffTechnician
		super(id,first,last);
		
	}
	//overridden method, set seniorTechnician salary to $25 an hour
	public double getHourlyRate(){
		
		hourlyRate = 25.0;
		return hourlyRate;
	}
	//call format method from Staff Technician and add serviceAward
	public String format(){
		return(super.format() + "Has Service Reward: " + serviceAward);
	}
	
}