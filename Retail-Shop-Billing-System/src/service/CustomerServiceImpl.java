package service;

import java.util.List;
import java.util.Optional;

import entity.Customer;
import persistence.CustomerDAO;
import persistence.CustomerDAOImpl;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDAO customerDAO = new CustomerDAOImpl();

	@Override
	public boolean loginCustomer(int customerID, String password) {
		Optional<Customer> customerOptional = customerDAO.fetchCustomerDetails(customerID);
		if (!customerOptional.isPresent() || !customerOptional.get().getPassword().equals(password))
			return false;
		return true;
	}

	@Override
	public boolean registerCustomer(Customer customer) {
		if (!customerDAO.fetchCustomerDetails(customer.getCustomerID()).isPresent())
			return customerDAO.registerNewCustomer(customer);
		return false;
	}

	@Override
	public boolean updatePasssword(int customerID, String oldPassword, String newPassword) {
		Optional<Customer> customerOptional = customerDAO.fetchCustomerDetails(customerID);
		if (!customerOptional.isPresent() || !customerOptional.get().getPassword().equals(oldPassword))
			return false;
		Customer customer = new Customer(customerOptional.get().getCustomerID(), customerOptional.get().getName(),
				newPassword, customerOptional.get().getPhoneNo(), customerOptional.get().getAddress());
		customerDAO.updateCustomerDetails(customer);
		return true;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerDAO.getAllCustomers();
	}

	@Override
	public Optional<Customer> searchCustomer(int customerID) {
		return customerDAO.fetchCustomerDetails(customerID);
	}

	@Override
	public boolean deleteCustomer(int customerID) {
		if (customerDAO.fetchCustomerDetails(customerID).isPresent())
			customerDAO.deleteCustomerByID(customerID);
		return false;
	}

}
