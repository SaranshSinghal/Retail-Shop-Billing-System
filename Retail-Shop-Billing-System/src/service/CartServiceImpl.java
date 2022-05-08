package service;

import java.util.List;
import java.util.Optional;

import entity.Cart;
import entity.Product;
import persistence.CartDAO;
import persistence.CartDAOImpl;
import persistence.ProductDAO;
import persistence.ProductDAOImpl;

public class CartServiceImpl implements CartService {
	private CartDAO cartDAO = new CartDAOImpl();

	private ProductDAO productDAO = new ProductDAOImpl();

	@Override
	public boolean addItemsInCart(int productID, int quant, int customerID) {
		Optional<Cart> cartOptional = cartDAO.searchProductInCart(productID);
		Optional<Product> productOptional = productDAO.getProduct(productID);
		if (productOptional.isPresent()) {
			if (!cartOptional.isPresent()) {
				Cart cart = new Cart(customerID, productID, 1, productOptional.get().getPrice());
				cart.setQuantity(quant);
				return cartDAO.addItemInCart(cart, customerID);
			} else {
				Cart cart = cartOptional.get();
				cart.setQuantity(cart.getQuantity() + 1);
				Product product = productOptional.get();
				cart.setTotalAmount(product.getPrice() * cart.getQuantity());
				return cartDAO.updateItemInCart(cart);
			}
		} else
			return false;
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
	public boolean deleteItemFromCart(int productID) {
		Optional<Cart> cartOptional = cartDAO.searchProductInCart(productID);
		if (!cartOptional.isPresent())
			return false;
		return cartDAO.deleteItemFromCart(productID);
	}

}
