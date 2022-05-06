package service;

public interface CustomerService {

	boolean login(int customerID, String password);

	boolean updatePasssword(int customerID, String oldPassword, String newPassword);

}
