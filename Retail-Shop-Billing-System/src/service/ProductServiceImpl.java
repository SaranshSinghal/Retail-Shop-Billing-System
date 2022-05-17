package service;

import java.util.List;
import java.util.Optional;

import entity.Cart;
import entity.Product;
import persistence.CartDAO;
import persistence.CartDAOImpl;
import persistence.ProductDAO;
import persistence.ProductDAOImpl;

public class ProductServiceImpl implements ProductService {

	ProductDAO productDAO = new ProductDAOImpl();
	CartDAO cartDAO = new CartDAOImpl();

	@Override
	public Optional<Product> searchProduct(int productID) {
		return productDAO.getProduct(productID);
	}

	@Override
	public boolean addProduct(Product product) {
		if (!productDAO.getProduct(product.getProductID()).isPresent())
			return productDAO.addNewProduct(product);
		return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		if (productDAO.getProduct(product.getProductID()).isPresent())
			return productDAO.updateProduct(product);
		return false;
	}

	@Override
	public boolean deleteProduct(int productID) {
		if (productDAO.getProduct(productID).isPresent())
			return productDAO.deleteProduct(productID);
		return false;
	}

	@Override
	public List<Product> getAllProducts() {
		return productDAO.getAllProducts();
	}

	@Override
	public boolean updateProductsQuantity(int customerID) {
		try {
			for (Cart cart : cartDAO.fetchCart(customerID)) {
				Product product = productDAO.getProduct(cart.getProductID()).get();
				product.setQuantity(product.getQuantity() + cart.getQuantity());
				productDAO.updateProduct(product);
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

}
