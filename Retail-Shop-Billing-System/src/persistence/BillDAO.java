package persistence;

import java.util.List;

import entity.Bill;
import entity.Cart;

public interface BillDAO {

	boolean pushBill(int customerID, List<Cart> cartList);

	List<Bill> fetchBill(int customerID);

}
