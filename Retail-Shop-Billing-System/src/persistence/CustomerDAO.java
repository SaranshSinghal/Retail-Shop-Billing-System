package persistence;

import java.util.List;
import java.util.Optional;

import entity.Customer;

public interface CustomerDAO {

	Optional<Customer> fetchCustomerDetails(int customerID);

	boolean registerNewCustomer(Customer newCustomer);

	boolean updateCustomerDetails(Customer customer);

	List<Customer> getAllCustomers();

	boolean deleteCustomerByID(int csutomerID);

}
