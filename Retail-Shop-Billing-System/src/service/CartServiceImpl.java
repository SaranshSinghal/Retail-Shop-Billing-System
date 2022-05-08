package service;
import java.util.Optional;
import entity.Cart;
import entity.Product;
import persistence.CartDAOImpl;


public class CartServiceImpl implements CartService {
	private CartDAOImpl cartDAO = new CartDAOImpl();
	@Override
	public boolean addItemsInCart(Product product, int customerID) {
		// TODO Auto-generated method stub
		return cartDAO.addItems(product, customerID);
	}

	@Override
	public Optional<Cart> getCart(int customerID) {
		// TODO Auto-generated method stub
		return cartDAO.fetchCart(customerID);
	
	}

	@Override
	public boolean deleteCart(int customerID) {
		// TODO Auto-generated method stub
		Optional<Cart> cartOptional = cartDAO.fetchCart(customerID);
		if (!cartOptional.isPresent())
			return false;
		return cartDAO.emptyCart(customerID);
	}

}
