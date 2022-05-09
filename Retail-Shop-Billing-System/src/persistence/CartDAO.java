package persistence;

import java.util.List;
import java.util.Optional;

import entity.Cart;

public interface CartDAO {

	boolean addItemInCart(Cart cart, int customerID);

	Optional<Cart> searchProductInCart(int productID, int customerID);

	boolean updateItemInCart(Cart cart);

	boolean deleteItemFromCart(int productID, int customerID);

	List<Cart> fetchCart(int customerID);

	boolean emptyCart(int customerID);

}
