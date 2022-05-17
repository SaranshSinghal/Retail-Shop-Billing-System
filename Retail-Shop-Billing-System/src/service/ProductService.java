package service;

import java.util.List;
import java.util.Optional;

import entity.Product;

public interface ProductService {

	Optional<Product> searchProduct(int productID);

	List<Product> getAllProducts();

	boolean addProduct(Product product);

	boolean updateProduct(Product product);

	boolean deleteProduct(int productID);

	boolean updateProductsQuantity(int customerID);

}
