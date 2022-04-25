package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import entity.Product;

public class ProductDAOImpl implements ProductDAO {

	@Override
	public Optional<Product> searchProduct(int productID) {
		Product product = null;
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCTS WHERE PRODUCTID=?");
			preparedStatement.setInt(1, productID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int productid = resultSet.getInt("productid");
				String name = resultSet.getString("name");
				String category = resultSet.getString("category");
				double price = resultSet.getDouble("price");
				int quantity = resultSet.getInt("quantity");
				product = new Product(productid, name, category, price, quantity);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Optional<Product> optionalProduct = Optional.ofNullable(product);
		return optionalProduct;
	}

	@Override
	public boolean deleteProduct(int productID) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("DELETE FROM PRODUCTS WHERE PRODUCTID=?");
			preparedStatement.setInt(1, productID);
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
	public boolean addProduct(Product product) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("INSERT INTO PRODUCTS VALUES(?,?,?,?,?)");
			preparedStatement.setInt(1, product.getId());
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(3, product.getCategory());
			preparedStatement.setDouble(4, product.getPrice());
			preparedStatement.setInt(5, product.getQuantity());
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
	public boolean updateQuantity(int productID, int quantity) {
		int rows = 0;
		PreparedStatement preparedStatement = null;
		try (Connection connection
				= DriverManager.getConnection("jdbc:mysql://localhost:3306/retailshop", "root", "wiley");) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			preparedStatement = connection.prepareStatement("UPDATE PRODUCTS SET QUANTITY=? WHERE PRODUCTID=?");
			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, productID);
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

}
