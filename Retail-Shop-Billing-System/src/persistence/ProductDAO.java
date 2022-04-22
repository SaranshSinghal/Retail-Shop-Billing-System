package persistence;

import java.util.Optional;

import entity.Product;

public interface ProductDAO {

	Optional<Product> searchProduct(int productID);

	boolean deleteProduct(int productID);

	boolean addProduct(Product product);

	boolean updateQuantity(int productID, int quantity);

}
