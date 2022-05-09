package presentation;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import entity.Product;
import service.ProductService;
import service.ProductServiceImpl;

public class AdminPresentationImpl implements AdminPresentation {

	private final String USERNAME = "team4";
	private final String PASSWORD = "123456789";

	private ProductService productService = new ProductServiceImpl();

	@Override
	public void showMenu() {
		System.out.println("\n1. List All Products");
		System.out.println("2. Add new Product");
		System.out.println("3. Delete product");
		System.out.println("4. Update product");
		System.out.println("5. Search product by productID");
		System.out.println("6. Exit");
	}

	@Override
	public void performMenu(int choice) {
		Scanner scanner = new Scanner(System.in);
		int productID = 0;
		switch (choice) {
		case 1:
			List<Product> productsList = productService.getAllProducts();
			for (Product product : productsList)
				System.out.println("Product ID: " + product.getProductID() + "\tProduct Name: " + product.getName()
						+ "\tCategory: " + product.getCategory() + "\tQuantity: " + product.getQuantity() + "\tPrice: "
						+ product.getPrice());
			break;
		case 2:
			Product newProduct = new Product();
			System.out.print("Enter productID: ");
			newProduct.setProductID(scanner.nextInt());
			System.out.print("Enter product name: ");
			newProduct.setName(scanner.next());
			System.out.print("Enter product category: ");
			newProduct.setCategory(scanner.next());
			System.out.print("Enter product price: ");
			newProduct.setPrice(scanner.nextDouble());
			System.out.print("Enter product quantity: ");
			newProduct.setQuantity(scanner.nextInt());
			if (productService.addProduct(newProduct))
				System.out.println("Product added successfully");
			else
				System.out.println("Product was not added! Please enter correct details...");
			break;
		case 3:
			System.out.print("Enter productID: ");
			if (productService.deleteProduct(scanner.nextInt()))
				System.out.println("Product deleted successfully");
			else
				System.out.println("Product deletion failed! No such product exists...");
			break;
		case 4:
			System.out.print("Enter productID: ");
			productID = scanner.nextInt();
			Optional<Product> optionalProduct = productService.getProduct(productID);
			if (optionalProduct.isPresent()) {
				System.out.print("1. Price\n2. Quantity\n3. Both\nSelect field to update: ");
				int updateChoice = scanner.nextInt();
				switch (updateChoice) {
				case 1:
					System.out.print("Enter updated price of the product: ");
					optionalProduct.get().setPrice(scanner.nextDouble());
					break;
				case 2:
					System.out.print("Enter updated quantity of the product: ");
					optionalProduct.get().setQuantity(scanner.nextInt());
					break;
				case 3:
					System.out.print("Enter new price of the product: ");
					optionalProduct.get().setPrice(scanner.nextDouble());
					System.out.print("Enter new quantity of the product: ");
					optionalProduct.get().setQuantity(scanner.nextInt());
					break;
				default:
					System.out.println("Invalid choice!");
					break;
				}
				if (updateChoice >= 1 && updateChoice <= 3 && productService.updateProduct(optionalProduct.get()))
					System.out.println("Product updated successfully");
				else
					System.out.println("Product updation failed!");
			} else
				System.out.println("Product with " + productID + " does not exist.");
			break;
		case 5:
			System.out.print("Enter productID: ");
			productID = scanner.nextInt();
			Optional<Product> productopOptional = productService.getProduct(productID);
			if (productopOptional.isPresent()) {
				Product product = productopOptional.get();
				System.out.println("Product ID: " + product.getProductID() + "\tProduct Name: " + product.getName()
						+ "\tCategory: " + product.getCategory() + "\tQuantity: " + product.getQuantity() + "\tPrice: "
						+ product.getPrice());
			} else
				System.out.println("Product with " + productID + " does not exist.");
			break;
		case 6:
			scanner.close();
			System.out.println("Thank you for using Retail Management Services!");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid choice! Please enter valid choice.");
		}
	}

	@Override
	public boolean validate(String userName, String password) {
		if (userName.equals(USERNAME) && password.equals(PASSWORD))
			return true;
		return false;
	}

}
