package persistence;

import java.util.List;
import java.util.Optional;

import entity.Customer;

public interface CustomerDAO {

	Optional<Customer> fetchCustomerDetails(int customerID);

	boolean register(Customer newCustomer);

	boolean updateDetails(Customer customer);

	List<Customer> getAllCustomers(); // no password shown.

}
