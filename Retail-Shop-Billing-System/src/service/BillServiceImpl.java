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
			double billAmount = 0,taxAmount=0;
			List<Cart> cartProducts = cartDAO.fetchCart(customerID);
			for (Cart cart : cartProducts) {
				Product product = productDAO.getProduct(cart.getProductID()).get();
				taxAmount = getTaxAmount(product.getCategory(),1,cart.getTotalAmount());
				billAmount += cart.getTotalAmount() + taxAmount;
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

	@Override
	public double getTaxRate(String productCategory) {
		double taxRate=0;
		if (productCategory.equalsIgnoreCase("cd"))
			taxRate = BillService.CD_TAX;
		else if (productCategory.equalsIgnoreCase("cosmetics"))
			taxRate = BillService.COSMETICS_TAX;
		else if (productCategory.equalsIgnoreCase("book"))
			taxRate = BillService.BOOKS_TAX;
		return taxRate;
	}

	@Override
	public double getTaxAmount(String productCategory,int quantity,double amount) {
		
		return (getTaxRate(productCategory)/100)*amount*quantity;
	}

	@Override
	public double totalAmountAfterTax(double amount, double tax) {
		
		return amount+tax;
	}

}
