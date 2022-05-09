package service;

import java.util.List;

import entity.Cart;
import entity.Product;

public interface CartService {

	boolean addItemsInCart(Product product, int quantity, int customerID);

	List<Cart> getCart(int customerID);

	boolean deleteItemFromCart(int productID, int customerID);

	boolean emptyCart(int customerID);

}
