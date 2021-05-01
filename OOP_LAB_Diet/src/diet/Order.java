package diet;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an order in the take-away system
 */
public class Order {
	
	String delivery;
	String restaurantName;
	String userName;
	LinkedList<Menu> referenceMenus;
	Map <String,Integer> menus = new TreeMap<>();
 
	/**
	 * Defines the possible order status
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	/**
	 * Defines the possible valid payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
	
	private OrderStatus status = OrderStatus.ORDERED;
	private PaymentMethod payment = PaymentMethod.CASH;
		
	
	/**
	 * Total order price
	 * @return order price
	 */
	public double Price() {
		return -1.0;
	}
	
	/**
	 * define payment method
	 * 
	 * @param method payment method
	 */
	public void setPaymentMethod(PaymentMethod method) {
		this.payment = method;
	}
	
	/**
	 * get payment method
	 * 
	 * @return payment method
	 */
	public PaymentMethod getPaymentMethod() {
		return payment;
	}
	
	/**
	 * change order status
	 * @param newStatus order status
	 */
	public void setStatus(OrderStatus newStatus) {
		this.status = newStatus;
	}
	
	/**
	 * get current order status
	 * @return order status
	 */
	public OrderStatus getStatus(){
		return status;
	}
	
	/**
	 * Add a new menu with the relative order to the order.
	 * The menu must be defined in the {@link Food} object
	 * associated the restaurant that created the order.
	 * 
	 * @param menu     name of the menu
	 * @param quantity quantity of the menu
	 * @return this order to enable method chaining
	 */
	public Order addMenus(String menuName, int quantity) {
		menus.put(menuName,quantity);
		return this;
	}
	
	/**
	 * Converts to a string as:
	 * <pre>
	 * RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : HH:MM:
	 * 	MENU_NAME_1->MENU_QUANTITY_1
	 * 	...
	 * 	MENU_NAME_k->MENU_QUANTITY_k
	 * </pre>
	 */
	@Override
	public String toString() {
		String res = restaurantName + ", "+userName + " : (" + delivery+"):\n";
		for (Map.Entry<String,Integer> e: menus.entrySet()) {
			res = res+ "\t" + e.getKey() +"->"+e.getValue()+"\n";
		}
		return res;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public void setReferenceMenus(LinkedList<Menu> menus) {
		this.referenceMenus = menus;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public void setUserName(String string) {
		userName = string;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getDelivery() {
		return delivery;
	}
	
}
