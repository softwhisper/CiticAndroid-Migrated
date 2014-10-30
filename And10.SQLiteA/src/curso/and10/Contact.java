package curso.and10;

public class Contact {
	
	//private variables
	int id;
	String name;
	String phone_number;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String name, String phone_number){
		this.id = id;
		this.name = name;
		this.phone_number = phone_number;
	}
	
	// constructor
	public Contact(String name, String phone_number){
		this.name = name;
		this.phone_number = phone_number;
	}
	// getting ID
	public int getID(){
		return this.id;
	}
	
	// setting id
	public void setID(int id){
		this.id = id;
	}
	
	// getting name
	public String getName(){
		return this.name;
	}
	
	// setting name
	public void setName(String name){
		this.name = name;
	}
	
	// getting phone number
	public String getPhoneNumber(){
		return this.phone_number;
	}
	
	// setting phone number
	public void setPhoneNumber(String phone_number){
		this.phone_number = phone_number;
	}
}
