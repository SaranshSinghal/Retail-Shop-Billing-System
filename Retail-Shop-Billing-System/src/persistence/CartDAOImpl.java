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
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("INSERT INTO Cart VALUES(default,?,?,?,?)");
			preparedStatement.setInt(1, customerID);
			preparedStatement.setInt(2, cart.getProductID());
			preparedStatement.setInt(3, cart.getQuantity());
			preparedStatement.setDouble(4, cart.getTotalAmount());
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
				int cartid = resultSet.getInt("C_Id");
				int productid = resultSet.getInt("P_id");
				int quantity = resultSet.getInt("Quantity");
				double totalamount = resultSet.getDouble("TotalAmount");
				cartList.add(new Cart(cartid, productid, quantity, totalamount));
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
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("DELETE FROM Cart WHERE C_Id=?");
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Optional<Cart> searchProductInCart(int productID) {
		PreparedStatement preparedStatement = null;
		Cart cart = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3360/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("SELECT * FROM Cart WHERE P_Id=?");
			preparedStatement.setInt(1, productID);
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
	public boolean updateItemInCart(Cart cart) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3360/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("UPDATE Cart SET Quantity=?, TotalAmount=? WHERE P_Id=?");
			preparedStatement.setInt(1, cart.getQuantity());
			preparedStatement.setDouble(2, cart.getTotalAmount());
			preparedStatement.setInt(3, cart.getProductID());
			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

	@Override
	public boolean deleteItemFromCart(int productID) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3360/retailshop", "root",
				"wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("DELETE FROM Cart WHERE P_Id=?");
			preparedStatement.setInt(1, productID);
			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rows > 0 ? true : false;
	}

}
