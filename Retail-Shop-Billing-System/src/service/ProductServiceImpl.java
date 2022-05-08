package service;

import java.util.List;
import java.util.Optional;

import entity.Product;
import persistence.ProductDAO;
import persistence.ProductDAOImpl;

public class ProductServiceImpl implements ProductService {

	ProductDAO productDAO = new ProductDAOImpl();

	@Override
	public Optional<Product> getProduct(int productID) {
		return productDAO.getProduct(productID);
	}

	@Override
	public boolean addProduct(Product product) {
		Optional<Product> productOptional = productDAO.getProduct(product.getProductID());
		if (!productOptional.isPresent())
			return productDAO.addNewProduct(product);
		return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		Optional<Product> productOptional = productDAO.getProduct(product.getProductID());
		if (!productOptional.isPresent())
			return productDAO.updateProduct(product);
		return false;
	}

	@Override
	public boolean deleteProduct(int productID) {
		Optional<Product> productOptional = productDAO.getProduct(productID);
		if (!productOptional.isPresent())
			return false;
		return productDAO.deleteProduct(productID);
	}

	@Override
	public List<Product> getAllProducts() {

		return null;
	}

}
