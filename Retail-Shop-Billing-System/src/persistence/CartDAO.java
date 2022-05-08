package persistence;

import java.util.Optional;

import entity.Cart;
import entity.Product;

public interface CartDAO {

	boolean addItems(Product product, int customerID);

	Optional<Cart> fetchCart(int customerID);

	boolean emptyCart(int customerID);

}
