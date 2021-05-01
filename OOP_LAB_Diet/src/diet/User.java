package diet;

import java.util.LinkedList;

/**
 * Represent a take-away system user
 *  
 */
public class User {
	
	private String first;
	private String last;
	private String email;
	private String phone;
	private LinkedList<Order> orders = new LinkedList<>();
		
	public User(String firstName, String lastName, String email, String phoneNumber) {
		this.first = firstName;
		this.last = lastName;
		this.email = email;
		this.phone = phoneNumber;
	}

	/**
	 * get user's last name
	 * @return last name
	 */
	public String getLastName() {
		return last;
	}
	
	/**
	 * get user's first name
	 * @return first name
	 */
	public String getFirstName() {
		return first;
	}
	
	/**
	 * get user's email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * get user's phone number
	 * @return  phone number
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * change user's email
	 * @param email new email
	 */
	public void SetEmail(String email) {
		this.email = email;
	}
	
	/**
	 * change user's phone number
	 * @param phone new phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String toString(){
		return first+ " "+ last;
	}

	public void addOrder(Order order) {
		orders.add(order);
	}
	
}
