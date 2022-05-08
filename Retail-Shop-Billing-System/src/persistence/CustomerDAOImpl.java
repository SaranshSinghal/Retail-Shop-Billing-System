package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Customer;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public Optional<Customer> fetchCustomerDetails(int customerID) {
		Customer customer = null;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("SELECT * FROM Customer WHERE C_Id=?");
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int cID = resultSet.getInt("C_Id");
				String name = resultSet.getString("C_Name");
				String password = resultSet.getString("C_Password");
				String phoneNo = resultSet.getString("PhoneNumber");
				String address = resultSet.getString("Address");
				customer = new Customer(cID, name, password, phoneNo, address);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(customer);
	}

	@Override
	public boolean registerNewCustomer(Customer newCustomer) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?,?)");
			preparedStatement.setInt(1, newCustomer.getCustomerID());
			preparedStatement.setString(2, newCustomer.getName());
			preparedStatement.setString(3, newCustomer.getPassword());
			preparedStatement.setString(4, newCustomer.getPhoneNo());
			preparedStatement.setString(5, newCustomer.getAddress());
			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

	@Override
	public boolean updateCustomerDetails(Customer customer) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("SELECT * FROM Customer WHERE C_Id=?");
			preparedStatement.setInt(1, customer.getCustomerID());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				preparedStatement = connection.prepareStatement(
						"UPDATE Customer SET C_Name=?,C_Password=?,PhoneNumber=?,Address=? WHERE C_Id=?");
				preparedStatement.setString(1, customer.getName());
				preparedStatement.setString(2, customer.getPassword());
				preparedStatement.setString(3, customer.getPhoneNo());
				preparedStatement.setString(4, customer.getAddress());
				preparedStatement.setInt(5, customer.getCustomerID());
				rows = preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

	@Override
	public List<Customer> getAllCustomers() {
		Statement statement = null;
		List<Customer> customersList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer");
			while (resultSet.next()) {
				int customerID = resultSet.getInt("C_Id");
				String name = resultSet.getString("C_Name");
				String password = resultSet.getString("C_Password");
				String phoneNo = resultSet.getString("PhoneNumber");
				String address = resultSet.getString("Address");
				customersList.add(new Customer(customerID, name, password, phoneNo, address));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return customersList;
	}

}
