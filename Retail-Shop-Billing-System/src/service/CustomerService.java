package service;

import java.util.List;
import java.util.Optional;

import entity.Customer;

public interface CustomerService {

	boolean loginCustomer(int customerID, String password);

	boolean registerCustomer(Customer customer);

	boolean updatePasssword(int customerID, String oldPassword, String newPassword);

	List<Customer> getAllCustomers();

	boolean deleteCustomer(int customerID);

	Optional<Customer> searchCustomer(int customerID);

}
