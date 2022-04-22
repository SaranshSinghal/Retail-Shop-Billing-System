package persistence;

import java.util.Optional;

import entity.Transaction;

public interface TransactionDAO {

	boolean pushBill(Transaction newTransaction);

	Optional<Transaction> fetchBill(int billID);

}
