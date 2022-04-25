package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import entity.Transaction;

public class TransactionDaoImpl implements TransactionDAO {

	@Override
	public boolean pushBill(Transaction newTransaction) {
		
		PreparedStatement preparedStatement=null;
		try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root","wiley");){
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement=connection.prepareStatement("insert into transaction values(?,?,?,?,?)");
			preparedStatement.setInt(1, newTransaction.getCustomerID());
			preparedStatement.setInt(2, newTransaction.getTransacID());
			preparedStatement.setInt(3, newTransaction.getNoOfProducts());
			preparedStatement.setObject(4, newTransaction.getTimestamp());
			preparedStatement.setDouble(5, newTransaction.getBillAmount());
			int rows=preparedStatement.executeUpdate();
			if(rows>0)
				return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Optional<Transaction> fetchBill(int billID) {
		Transaction transaction = null;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root","wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");

			preparedStatement = connection.prepareStatement("SELECT * FROM TRANSACTION WHERE billID=?");

			preparedStatement.setInt(1, billID);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int customerID = resultSet.getInt("customerID");
				int transactionID = resultSet.getInt("transactionID");
				int noOfProducts = resultSet.getInt("noOfProducts");
				LocalDateTime localDateTime=(LocalDateTime) resultSet.getObject("TimeStamp");
				double billAmount = resultSet.getDouble("billAmount");
				transaction = new Transaction(customerID, transactionID, noOfProducts, localDateTime, billAmount);

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Optional<Transaction> optionalTransaction = Optional.ofNullable(transaction);
		return optionalTransaction;
	}

}
