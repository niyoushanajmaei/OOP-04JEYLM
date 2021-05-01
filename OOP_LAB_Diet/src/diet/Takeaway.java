package diet;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Represents the main class in the
 * take-away system.
 * 
 * It allows adding restaurant, users, and creating orders.
 *
 */
public class Takeaway {
	
	private LinkedList<Restaurant> restaurants = new LinkedList<>();
	private LinkedList<User> users = new LinkedList<>();

	/**
	 * Adds a new restaurant to the take-away system
	 * 
	 * @param r the restaurant to be added
	 */
	public void addRestaurant(Restaurant r) {
		restaurants.add(r);
	}
	
	/**
	 * Returns the collections of restaurants
	 * 
	 * @return collection of added restaurants
	 */
	public Collection<String> restaurants() {
		Collection<String> names = new LinkedList<>();
		for (Restaurant r: restaurants) {
			names.add(r.getName());
		}
		return names;
	}
	
	/**
	 * Define a new user
	 * 
	 * @param firstName first name of the user
	 * @param lastName  last name of the user
	 * @param email     email
	 * @param phoneNumber telephone number
	 * @return
	 */
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User u = new User(firstName,lastName,email,phoneNumber);
		users.add(u);
		return u;
	}
	
	/**
	 * Gets the collection of registered users
	 * 
	 * @return the collection of users
	 */

	public Collection<User> users(){
		users.sort(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName));
		return users;
	}
	
	/**
	 * Create a new order by a user to a given restaurant.
	 * 
	 * The order is initially empty and is characterized
	 * by a desired delivery time. 
	 * 
	 * @param user				user object
	 * @param restaurantName	restaurant name
	 * @param h					delivery time hour
	 * @param m					delivery time minutes
	 * @return
	 */
	public Order createOrder(User user, String restaurantName, int h, int m) {
		String delivery = String.format("%02d:%02d", h, m);;
		Order order = new Order();
		Restaurant r = this.findRestaurant(restaurantName);
		order.setReferenceMenus(r.getMenus());
		order.setRestaurantName(restaurantName);
		order.setUserName(user.getFirstName() + " " + user.getLastName());
		if (r.isOpen(delivery)) {
			order.setDelivery(delivery);
		}else {
			order.setDelivery(r.nextOpen(h,m));
		}
		r.addOrder(order);
		user.addOrder(order);
		/*
		System.out.println("created order for user: " + user.getFirstName()+ " restaurant: "+restaurantName);
		System.out.println("Napoli orders: ");
		if(restaurantName.equals("Napoli")) {
			for (Order o : r.getOrders()) {
				System.out.println(o);
			}
		}
		*/		
		return order;
	}
	
	private Restaurant findRestaurant(String restaurantName) {
		for(Restaurant r :restaurants) {
			if(r.getName().equals(restaurantName)) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Retrieves the collection of restaurant that are open
	 * at the given time.
	 * 
	 * @param time time to check open
	 * 
	 * @return collection of restaurants
	 */
	public Collection<Restaurant> openedRestaurants(String time){
		LinkedList<Restaurant> openRestaurants = new LinkedList<Restaurant>();
		for (Restaurant r : restaurants) {
			 if (r.isOpen(time)) {
				 openRestaurants.add(r);
			 }
		}
		openRestaurants.sort(Comparator.comparing(Restaurant::getName));
		return openRestaurants;
	}

	
	
}
