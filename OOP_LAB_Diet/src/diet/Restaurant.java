package diet;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant in the take-away system
 *
 */
public class Restaurant {
	
	private String name;
	private Food food;
	private String[] hours;
	private LinkedList<Order> orders = new LinkedList<>();
	
	/**
	 * Constructor for a new restaurant.
	 * 
	 * Materials and recipes are taken from
	 * the food object provided as argument.
	 * 
	 * @param name	unique name for the restaurant
	 * @param food	reference food object
	 */
	public Restaurant(String name, Food food) {
		this.name = name;
		this.food = food;
	}
	
	/**
	 * gets the name of the restaurant
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Define opening hours.
	 * 
	 * The opening hours are considered in pairs.
	 * Each pair has the initial time and the final time
	 * of opening intervals.
	 * 
	 * for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00, 
	 * is thoud be called as {@code setHours("08:15", "14:00", "19:00", "00:00")}.
	 * 
	 * @param hm a list of opening hours
	 */
	public void setHours(String ... hm) {
		if (hm.length %2 == 0) {
			hours = hm;
		}else {
			System.err.println("Restaurant hours size was odd");
			System.exit(1);
		}
	}
	
	public Menu getMenu(String name) {
		for (Menu m:food.getMenus()) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		return food.createMenu(name);
	}

	/**
	 * Find all orders for this restaurant with 
	 * the given status.
	 * 
	 * The output is a string formatted as:
	 * <pre>
	 * Napoli, Judi Dench : (19:00):
	 * 	M6->1
	 * Napoli, Ralph Fiennes : (19:00):
	 * 	M1->2
	 * 	M6->1
	 * </pre>
	 * 
	 * The orders are sorted by name of restaurant, name of the user, and delivery time.
	 * 
	 * @param status the status of the searched orders
	 * 
	 * @return the description of orders satisfying the criterion
	 */
	public String ordersWithStatus(OrderStatus status) {
		String res = "";
		orders.sort(Comparator.comparing(Order::getUserName).thenComparing(Order::getDelivery));
		for (Order o:orders) {
			if(o.getStatus().equals(status)) {
				res+=o;
			}
		}
		return res;
	}

	public boolean isOpen(String time) {
		int h,m,h1,m1,h2,m2;
		h = Integer.parseInt(time.substring(0,2));
		m = Integer.parseInt(time.substring(3,5));
		for (int i=0;i<hours.length;i += 2) {
			h1 = Integer.parseInt(hours[i].substring(0,2));
			m1 = Integer.parseInt(hours[i].substring(3,5));
			h2 = Integer.parseInt(hours[i+1].substring(0,2));
			m2 = Integer.parseInt(hours[i+1].substring(3,5));
			if (h*60+m >= h1*60+m1 && h*60+m < h2*60+m2) {
				return true;
			}
		}
		return false;
	}

	public String nextOpen(int h, int m) {
		int h1,m1;
		String res;
		//System.out.println("h: "+ h+" m: "+ m);
		for (int i=0;i<hours.length;i += 2) {
			//System.out.println(hours[i] + " " + hours[i+1]);
			h1 = Integer.parseInt(hours[i].substring(0,2));
			m1 = Integer.parseInt(hours[i].substring(3,5));
			//System.out.println("h1: " + h1+ " m1: "+m1);
			if ( h*60+m < h1*60+m1) {
				res = String.format("%02d:%02d", h1, m1);
				return res;
			}
		}
		return "23:59";
	}

	public LinkedList<Menu> getMenus() {
		return food.getMenus();
	}

	public void addOrder(Order order) {
		orders.add(order);
	}

	public LinkedList<Order> getOrders() {
		return orders;
	}

}
