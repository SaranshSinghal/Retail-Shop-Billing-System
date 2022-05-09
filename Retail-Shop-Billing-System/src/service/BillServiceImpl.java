package service;

import java.util.List;
import java.util.Optional;

import entity.Bill;
import entity.Cart;
import entity.Product;
import persistence.BillDAO;
import persistence.BillDAOImpl;
import persistence.CartDAO;
import persistence.CartDAOImpl;
import persistence.ProductDAO;
import persistence.ProductDAOImpl;

public class BillServiceImpl implements BillService {

	private BillDAO billDAO = new BillDAOImpl();
	private CartDAO cartDAO = new CartDAOImpl();
	private ProductDAO productDAO = new ProductDAOImpl();

	@Override
	public boolean generateBill(int customerID) {
		List<Cart> cartProducts = cartDAO.fetchCart(customerID);
		for (Cart cart : cartProducts) {
			Optional<Product> productoOptional = productDAO.getProduct(cart.getProductID());
			if (productoOptional.get().getCategory().equals("cd"))
				cart.setTotalAmount(cart.getTotalAmount() * 1.1);
			else if (productoOptional.get().getCategory().equals("cosmetics"))
				cart.setTotalAmount(cart.getTotalAmount() * 1.12);
		}
		return billDAO.pushBill(customerID, cartProducts);
	}

	@Override
	public List<Bill> displayBill(int customerID) {
		return billDAO.fetchBill(customerID);
	}

}
