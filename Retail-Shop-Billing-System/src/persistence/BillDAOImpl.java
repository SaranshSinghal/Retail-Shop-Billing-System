package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import entity.Bill;

public class BillDAOImpl implements BillDAO {

	@Override
	public boolean pushBill(int customerID, Bill newTransaction) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("INSERT INTO BILL VALUES(?,?,?,?)");
			preparedStatement.setInt(1, newTransaction.getBillID());
			preparedStatement.setInt(2, newTransaction.getCustomerID());
			preparedStatement.setObject(3, newTransaction.getTimestamp());
			preparedStatement.setDouble(4, newTransaction.getTotalAmount());
			rows = preparedStatement.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

	@Override
	public Optional<Bill> fetchBill(int billID) {
		Bill transaction = null;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("SELECT * FROM BILL WHERE BILL_ID=?");
			preparedStatement.setInt(1, billID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int billId = resultSet.getInt("Bill_Id");
				int customerID = resultSet.getInt("C_Id");
				LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getObject("Time_Stamp").toString());
				double billAmount = resultSet.getDouble("TotalAmount");
				transaction = new Bill(billId, customerID, localDateTime, billAmount);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Optional<Bill> optionalTransaction = Optional.ofNullable(transaction);
		return optionalTransaction;
	}

}
