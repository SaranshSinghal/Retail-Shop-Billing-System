package service;

import java.util.Optional;

import entity.Product;
import persistence.ProductDAO;
import persistence.ProductDAOImpl;

public class ProductServiceImpl implements ProductService {

	ProductDAO productDao=new ProductDAOImpl();
	
	@Override
	public Optional<Product> getProduct(int productID) {
		
	
		return productDao.getProduct(productID);
	}

	@Override
	public boolean addProduct(Product product) {
		
		return productDao.addNewProduct(product);
	}

	@Override
	public boolean updateProduct(Product product) {
		
		return productDao.updateProduct(product);
	}

	@Override
	public boolean deleteProduct(int productID) {
		
		return productDao.deleteProduct(productID);
	}

	

}
