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
			Optional<Product> productOptional = productDAO.getProduct(cart.getProductID());
			Product product = productOptional.get();
			if (product.getCategory().equalsIgnoreCase("cd")) {
				double totalAmountAfterTax = cart.getTotalAmount() + cart.getTotalAmount() * BillService.CD_TAX;
				cart.setTotalAmount(totalAmountAfterTax);
			} else if (product.getCategory().equalsIgnoreCase("cosmetics")) {
				double totalAmountAfterTax = cart.getTotalAmount() + cart.getTotalAmount() * BillService.COSMETICS_TAX;
				cart.setTotalAmount(totalAmountAfterTax);
			} else if (product.getCategory().equalsIgnoreCase("book")) {
				double totalAmountAfterTax = cart.getTotalAmount() + cart.getTotalAmount() * BillService.BOOKS_TAX;
				cart.setTotalAmount(totalAmountAfterTax);
			}
		}
		return billDAO.pushBill(customerID, cartProducts);
	}

	@Override
	public List<Bill> displayBill(int customerID) {
		return billDAO.fetchBill(customerID);
	}

}
