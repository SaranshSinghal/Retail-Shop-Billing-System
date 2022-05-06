package service;

import entity.Product;

public interface ProductService {

	boolean searchProduct(int productID);

	boolean addProduct(Product product);

	boolean updateQuantity();

}
