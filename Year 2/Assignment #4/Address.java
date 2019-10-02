// 
//Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment
//.
//
// huntmk
import java.io.*;
public class Address
{
	// declare fields for the address
	private String adres1;
	private String adres2;
	private String city;
	private String zip;
	private String state;
	//default constructor
	public Address(String adres1,String adres2,String city, String state, String zip)
	{
		//initialize the variables
		this.adres1 = adres1;
		this.adres2 = adres2;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	public String getAddress(){
	
		//return the address in formatted way
		return (this.adres1 + " " + this.adres2 + " " + this.city + ", " + this.state + " " + this.zip);
	}
}