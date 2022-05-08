package service;

import java.util.Optional;

import entity.Cart;
import entity.Product;

public interface CartService {
	
	
	boolean addItemsInCart(Product product, int customerID);

	Optional<Cart> getCart(int customerID);

	boolean deleteCart(int customerID);

}
