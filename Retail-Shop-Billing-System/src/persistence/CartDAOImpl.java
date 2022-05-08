package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import entity.Cart;
import entity.Product;

public class CartDAOImpl implements CartDAO {

	@Override
	public boolean addItems(Product product, int customerID) {
		// TODO Auto-generated method stub
		
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			        preparedStatement = connection.prepareStatement("INSERT INTO Cart VALUES(default,?,?,?,?)");
					preparedStatement.setInt(1, customerID);
					preparedStatement.setInt(2, product.getProductID());
					preparedStatement.setInt(3, product.getQuantity());
					preparedStatement.setDouble(4, product.getPrice());
					ResultSet resultSet = preparedStatement.executeQuery();
					resultSet= preparedStatement.executeQuery();
					if(resultSet.next()) {
						return true;
					
					}		
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	

	@Override
	public Optional<Cart> fetchCart(int customerID) {
		// TODO Auto-generated method stub
	     Cart cart = null;
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("SELECT * FROM CART WHERE C_ID=?");
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int cartid = resultSet.getInt("C_Id");
				int productid = resultSet.getInt("P_id");
				int quantity = resultSet.getInt("Quantity");
				double totalamount = resultSet.getDouble("TotalAmount");
			
				cart = new Cart(cartid, productid, quantity, totalamount);	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Optional<Cart> optionalCart = Optional.ofNullable(cart);
		return optionalCart;
	}

	@Override
	public boolean emptyCart(int customerID) {
		// TODO Auto-generated method stub
		
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("DELETE FROM Cart WHERE C_Id=?");
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
