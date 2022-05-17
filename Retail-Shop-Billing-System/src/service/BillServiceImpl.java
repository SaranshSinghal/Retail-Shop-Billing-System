package service;

import java.util.List;

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
	public double generateBill(int customerID) {
		try {
			double billAmount = 0, taxRate = 0, taxAmount;
			List<Cart> cartProducts = cartDAO.fetchCart(customerID);
			for (Cart cart : cartProducts) {
				Product product = productDAO.getProduct(cart.getProductID()).get();
				if (product.getCategory().equalsIgnoreCase("cd")) {
					double totalAmountAfterTax
							= cart.getTotalAmount() + cart.getTotalAmount() * BillService.CD_TAX / 100;
					cart.setTotalAmount(totalAmountAfterTax);
					taxRate = BillService.CD_TAX;
				} else if (product.getCategory().equalsIgnoreCase("cosmetics")) {
					double totalAmountAfterTax
							= cart.getTotalAmount() + cart.getTotalAmount() * BillService.COSMETICS_TAX / 100;
					cart.setTotalAmount(totalAmountAfterTax);
					taxRate = BillService.COSMETICS_TAX;
				} else if (product.getCategory().equalsIgnoreCase("book")) {
					double totalAmountAfterTax
							= cart.getTotalAmount() + cart.getTotalAmount() * BillService.BOOKS_TAX / 100;
					cart.setTotalAmount(totalAmountAfterTax);
					taxRate = BillService.BOOKS_TAX;
				}
				taxAmount = taxRate * cart.getTotalAmount() / 100 * cart.getQuantity();
				billAmount += cart.getTotalAmount() + taxAmount;
				System.out.println("Product ID: " + cart.getProductID() + "\t\tQuantity: " + cart.getQuantity()
						+ "\t\tTax Rate: " + taxRate + "%" + "\t\tTax Amount: " + taxAmount + "\t\tTotal Amount: "
						+ (cart.getTotalAmount() + taxAmount));
			}
			cartDAO.emptyCart(customerID);
			billDAO.pushBill(customerID, cartProducts);
			return billAmount;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	@Override
	public List<Bill> displayBill(int customerID) {
		return billDAO.fetchBill(customerID);
	}

}
