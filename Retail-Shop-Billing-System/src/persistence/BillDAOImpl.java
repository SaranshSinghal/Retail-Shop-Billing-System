package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Bill;
import entity.Cart;

public class BillDAOImpl implements BillDAO {

	@Override
	public boolean pushBill(int customerID, List<Cart> cartList) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			for (Cart cart : cartList) {
				preparedStatement = connection.prepareStatement("INSERT INTO BILL VALUES(DEFAULT,?,?,?,?,?)");
				preparedStatement.setObject(1, LocalDateTime.now());
				preparedStatement.setInt(2, cart.getCustomerID());
				preparedStatement.setInt(3, cart.getProductID());
				preparedStatement.setInt(4, cart.getQuantity());
				preparedStatement.setDouble(5, cart.getTotalAmount());
				rows = preparedStatement.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

	@Override
	public List<Bill> fetchBill(int customerID) {
		List<Bill> billList = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("SELECT * FROM BILL WHERE C_ID=?");
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int custId = resultSet.getInt("C_Id");
				int productID = resultSet.getInt("P_Id");
				LocalDateTime timeStamp = LocalDateTime.parse(resultSet.getObject("Time_Stamp").toString());
				int quantity = resultSet.getInt("Quantity");
				double billAmount = resultSet.getDouble("TotalAmount");
				billList.add(new Bill(custId, productID, timeStamp, quantity, billAmount));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billList;
	}

}
