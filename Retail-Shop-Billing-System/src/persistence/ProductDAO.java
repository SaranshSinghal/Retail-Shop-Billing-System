package persistence;

import java.util.Optional;

import entity.Product;

public interface ProductDAO {

	boolean deleteProduct(int productID);

	boolean addNewProduct(Product product);

	boolean updateProduct(Product product);

	Optional<Product> getProduct(int productID);

}
