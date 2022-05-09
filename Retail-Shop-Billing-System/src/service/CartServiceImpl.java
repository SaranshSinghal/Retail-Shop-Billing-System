package service;

import java.util.List;
import java.util.Optional;

import entity.Cart;
import entity.Product;
import persistence.CartDAO;
import persistence.CartDAOImpl;

public class CartServiceImpl implements CartService {
	private CartDAO cartDAO = new CartDAOImpl();

	@Override
	public boolean addItemsInCart(Product product, int quant, int customerID) {
		Optional<Cart> cartOptional = cartDAO.searchProductInCart(product.getProductID(), customerID);
		if (!cartOptional.isPresent()) {
			Cart cart = new Cart(customerID, product.getProductID(), quant, product.getPrice() * quant);
			return cartDAO.addItemInCart(cart, customerID);
		} else {
			Cart cart = cartOptional.get();
			cart.setQuantity(cart.getQuantity() + 1);
			cart.setTotalAmount(product.getPrice() * cart.getQuantity());
			return cartDAO.updateItemInCart(cart, customerID);
		}
	}

	@Override
	public List<Cart> getCart(int customerID) {
		return cartDAO.fetchCart(customerID);
	}

	@Override
	public boolean emptyCart(int customerID) {
		return cartDAO.emptyCart(customerID);
	}

	@Override
	public boolean deleteItemFromCart(int productID, int customerID) {
		Optional<Cart> cartOptional = cartDAO.searchProductInCart(productID, customerID);
		if (cartOptional.isPresent())
			return cartDAO.deleteItemFromCart(productID, customerID);
		return false;
	}

}
