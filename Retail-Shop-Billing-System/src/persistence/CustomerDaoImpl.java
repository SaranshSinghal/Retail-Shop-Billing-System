package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Customer;

public class CustomerDaoImpl implements CustomerDAO {

	@Override
	public boolean login(int customerID, String password) {
		// TODO Auto-generated method stub
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection
					.prepareStatement("Select C_Id, C_Password from Customer where C_Id=? and C_Password=?");
			preparedStatement.setInt(1, customerID);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean register(Customer newCustomer) {
		// TODO Auto-generated method stub
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
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
		if (rows > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean updatePassword(int customerID, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("select C_Password from Customer where C_Password=?");
			preparedStatement.setString(1, oldPassword);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				preparedStatement = connection.prepareStatement("UPDATE Customer SET C_Password=? WHERE C_Id=?");
				preparedStatement.setString(1, newPassword);
				preparedStatement.setInt(2, customerID);
				preparedStatement.executeQuery();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getAllCustomers() {
		// TODO Auto-generated method stub
		return false;
	}

}
