package service;

import java.util.List;

import entity.Cart;

public interface CartService {

	boolean addItemsInCart(int productID, int customerID);

	List<Cart> getCart(int customerID);

	boolean deleteItemFromCart(int productID);

	boolean emptyCart(int customerID);

}
