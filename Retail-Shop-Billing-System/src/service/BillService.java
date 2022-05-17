package service;

import java.util.List;

import entity.Bill;

public interface BillService {

	double CD_TAX = 10;
	double COSMETICS_TAX = 12;
	double BOOKS_TAX = 0;

	double generateBill(int customerID);

	List<Bill> displayBill(int customerID);

	double getTaxRate(String productCategory);

	double getTaxAmount(String productCategory, int quantity, double amount);

}
