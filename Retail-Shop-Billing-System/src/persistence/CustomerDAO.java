package persistence;

import entity.Customer;

public interface CustomerDAO {

	boolean login(int customerID, String password);

	boolean register(Customer newCustomer);

	boolean updatePassword(int customerID, String oldPassword, String newPassword);

}
