package it.polito.oop.vaccination;

public class Person {
	String firstName;
	String lastName;
	String ssn;
	int year;
	STATUS st  = STATUS.NOT_ALLOCATED;
	Hub hub;
	String slot;
	
	public enum STATUS {ALLOCATED,NOT_ALLOCATED};
	
	public Person(String firstName, String lastName, String ssn, int year) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.year = year;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public String toString() {
		return ssn +"," + lastName+"," +firstName;
	}
	public STATUS getSt() {
		return st;
	}
	public void setSt(STATUS st) {
		this.st = st;
	}
	public Hub getHub() {
		return hub;
	}
	public void setHub(Hub hub) {
		this.hub = hub;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}

}
