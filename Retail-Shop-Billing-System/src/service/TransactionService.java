package service;

import java.util.List;

import entity.Product;

public interface TransactionService {

	double CD_TAX = 10;
	double COSMETICS_TAX = 12;
	double BOOKS_TAX = 0;

	void generateBill(int customerID, List<Product> products);

	void displayBill(int customerID, List<Product> products);

}
