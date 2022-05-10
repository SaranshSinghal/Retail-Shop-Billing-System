package presentation;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import entity.Customer;
import entity.Product;
import service.CustomerService;
import service.CustomerServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;

public class AdminPresentationImpl implements AdminPresentation {

	private final String USERNAME = "team4";
	private final String PASSWORD = "123456789";

	private ProductService productService = new ProductServiceImpl();
	private CustomerService customerService = new CustomerServiceImpl();

	@Override
	public void showMenu() {
		System.out.println("\nHello Admin!");
		System.out.println("\n1. List All Products");
		System.out.println("2. Add new Product");
		System.out.println("3. Delete product");
		System.out.println("4. Update product");
		System.out.println("5. Search product by productID");
		System.out.println("6. List All Customers");
		System.out.println("7. Exit");
	}

	@Override
	public void performMenu(int choice) {
		Scanner scanner = new Scanner(System.in);
		int productID = 0;
		try {
			switch (choice) {
			case 1:
				List<Product> productsList = productService.getAllProducts();
				for (Product product : productsList)
					System.out.println("Product ID: " + product.getProductID() + "\t\tProduct Name: "
							+ product.getName() + "\t\tCategory: " + product.getCategory() + "\t\tQuantity: "
							+ product.getQuantity() + "\t\tPrice: " + product.getPrice());
				break;
			case 2:
				Product newProduct = new Product();
				System.out.print("Enter productID: ");
				newProduct.setProductID(scanner.nextInt());
				System.out.print("Enter product name: ");
				scanner.nextLine();
				newProduct.setName(scanner.nextLine());
				System.out.print("Enter product category: ");
				newProduct.setCategory(scanner.nextLine());
				System.out.print("Enter product price: ");
				newProduct.setPrice(scanner.nextDouble());
				System.out.print("Enter product quantity: ");
				newProduct.setQuantity(scanner.nextInt());
				if (productService.addProduct(newProduct))
					System.out.println("Product added successfully");
				else
					System.out.println("Product was not added, please enter correct details!");
				break;
			case 3:
				System.out.print("Enter productID: ");
				if (productService.deleteProduct(scanner.nextInt()))
					System.out.println("Product deleted successfully");
				else
					System.out.println("Product deletion failed, no such product exists!");
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
					System.out.println("Product with " + productID + " does not exist!");
				break;
			case 5:
				System.out.print("Enter productID: ");
				productID = scanner.nextInt();
				Optional<Product> productopOptional = productService.getProduct(productID);
				if (productopOptional.isPresent()) {
					Product product = productopOptional.get();
					System.out.println("Product ID: " + product.getProductID() + "\t\tProduct Name: "
							+ product.getName() + "\t\tCategory: " + product.getCategory() + "\t\tQuantity: "
							+ product.getQuantity() + "\t\tPrice: " + product.getPrice());
				} else
					System.out.println("Product with " + productID + " does not exist!");
				break;
			case 6:
				List<Customer> customers = customerService.getAllCustomers();
				if (customers.size() > 0)
					for (Customer customer : customers) {
						System.out.print("Customer ID: " + customer.getCustomerID() + "\t\tName: " + customer.getName()
								+ "\t\tPassword: ");
						for (int i = 1; i <= customer.getPassword().length(); i++)
							System.out.print("*");
						System.out.println(
								"\t\tAddress: " + customer.getAddress() + "\t\tPhone Number: " + customer.getPhoneNo());
					}
				break;
			case 7:
				scanner.close();
				System.out.println("||  Thank you for using Retail Management Services ||");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice, please enter valid choice!");
			}
		} catch (InputMismatchException ex) {
			System.out.println("Please Enter valid input!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean validate(String userName, String password) {
		if (userName.equals(USERNAME) && password.equals(PASSWORD))
			return true;
		return false;
	}

}
