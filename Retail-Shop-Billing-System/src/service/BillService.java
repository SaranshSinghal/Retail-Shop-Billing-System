package service;

import java.util.List;

public interface BillService {

	double CD_TAX = 10;
	double COSMETICS_TAX = 12;
	double BOOKS_TAX = 0;

	void generateBill(int customerID, List<Integer> products);

	void displayBill(int customerID, List<Integer> products);

}
