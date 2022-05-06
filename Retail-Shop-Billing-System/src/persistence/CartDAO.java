package persistence;

public interface CartDAO {

	boolean addItems(int productID, int customerID);

	boolean fetchCart(int customerID);

	boolean emptyCart(int customerID);

}
