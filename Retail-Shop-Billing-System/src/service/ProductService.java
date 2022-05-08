package service;

import java.util.Optional;

import entity.Product;

public interface ProductService {

	Optional<Product> getProduct(int productID);

	boolean addProduct(Product product);


	boolean updateProduct(Product product);
	
	boolean deleteProduct(int productID);

}
