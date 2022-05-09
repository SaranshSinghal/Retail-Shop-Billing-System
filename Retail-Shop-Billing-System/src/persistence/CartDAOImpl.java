package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Cart;

public class CartDAOImpl implements CartDAO {

	@Override
	public boolean addItemInCart(Cart cart, int customerID) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("INSERT INTO Cart VALUES(default,?,?,?,?)");
			preparedStatement.setInt(1, customerID);
			preparedStatement.setInt(2, cart.getProductID());
			preparedStatement.setInt(3, cart.getQuantity());
			preparedStatement.setDouble(4, cart.getTotalAmount());
			rows = preparedStatement.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

	@Override
	public List<Cart> fetchCart(int customerID) {
		List<Cart> cartList = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("SELECT * FROM CART WHERE C_ID=?");
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int custID = resultSet.getInt("C_Id");
				int productId = resultSet.getInt("P_id");
				int quantity = resultSet.getInt("Quantity");
				double totalAmount = resultSet.getDouble("TotalAmount");
				cartList.add(new Cart(custID, productId, quantity, totalAmount));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartList;
	}

	@Override
	public boolean emptyCart(int customerID) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("DELETE FROM Cart WHERE C_Id=?");
			preparedStatement.setInt(1, customerID);
			rows = preparedStatement.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

	@Override
	public Optional<Cart> searchProductInCart(int productID, int customerID) {
		PreparedStatement preparedStatement = null;
		Cart cart = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("SELECT * FROM CART WHERE P_Id=? AND C_Id=?");
			preparedStatement.setInt(1, productID);
			preparedStatement.setInt(2, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int cartid = resultSet.getInt("C_Id");
				int productid = resultSet.getInt("P_id");
				int quantity = resultSet.getInt("Quantity");
				double totalamount = resultSet.getDouble("TotalAmount");
				cart = new Cart(cartid, productid, quantity, totalamount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(cart);
	}

	@Override
	public boolean updateItemInCart(Cart cart, int customerID) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3360/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection
					.prepareStatement("UPDATE CART SET Quantity=?, TotalAmount=? WHERE P_Id=? AND C_Id=?");
			preparedStatement.setInt(1, cart.getQuantity());
			preparedStatement.setDouble(2, cart.getTotalAmount());
			preparedStatement.setInt(3, cart.getProductID());
			preparedStatement.setInt(4, customerID);
			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

	@Override
	public boolean deleteItemFromCart(int productID, int customerID) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("DELETE FROM CART WHERE P_Id=? AND C_Id=?");
			preparedStatement.setInt(1, productID);
			preparedStatement.setInt(2, customerID);
			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

}
