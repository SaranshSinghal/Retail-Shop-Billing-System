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
	public boolean addItemsInCart(Product product, int quant, int customerID) {
		Optional<Cart> cartOptional = cartDAO.searchProductInCart(product.getProductID(), customerID);
		if (!cartOptional.isPresent()) {
			Cart cart = new Cart(customerID, product.getProductID(), quant, product.getPrice() * quant);
			product.setQuantity(product.getQuantity() - quant);
			productDAO.updateProduct(product);
			return cartDAO.addItemInCart(cart, customerID);
		} else {
			Cart cart = cartOptional.get();
			cart.setQuantity(cart.getQuantity() + quant);
			cart.setTotalAmount(product.getPrice() * cart.getQuantity());
			product.setQuantity(product.getQuantity() - quant);
			productDAO.updateProduct(product);
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
		if (cartDAO.searchProductInCart(productID, customerID).isPresent())
			return cartDAO.deleteItemFromCart(productID, customerID);
		return false;
	}

}
