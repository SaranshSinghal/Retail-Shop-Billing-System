package service;

import java.util.List;
import java.util.Optional;

import entity.Product;

public interface ProductService {

	Optional<Product> getProduct(int productID);

	List<Product> getAllProducts();

	boolean addProduct(Product product);

	boolean updateProduct(Product product);

	boolean deleteProduct(int productID);

}
