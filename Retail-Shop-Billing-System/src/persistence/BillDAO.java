package persistence;

import java.util.Optional;

import entity.Bill;

public interface BillDAO {

	boolean pushBill(Bill newTransaction);

	Optional<Bill> fetchBill(int billID);

}
