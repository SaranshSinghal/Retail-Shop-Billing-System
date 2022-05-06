package persistence;

import entity.Customer;

public interface CustomerDAO {

	Customer fetchCustomerDetails(int customerID);

	boolean register(Customer newCustomer);

	boolean updateDetails(Customer customer);

	boolean getAllCustomers(); // no password shown.

}
