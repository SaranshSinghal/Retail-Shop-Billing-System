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
		System.out.println("\n1. List All Products");
		System.out.println("2. Add New Product");
		System.out.println("3. Delete Product");
		System.out.println("4. Update Product");
		System.out.println("5. Search Product");
		System.out.println("6. List All Customers");
		System.out.println("7. Search Customer");
		System.out.println("8. Delete Customer");
		System.out.println("9. Logout ");
		try {
			Scanner scanner = new Scanner(System.in);
			int productID = 0, customerID = 0;
			System.out.print("Enter your choice: ");
			switch (scanner.nextInt()) {
			case 1:
				List<Product> productsList = productService.getAllProducts();
				System.out.println();
				if (!productsList.isEmpty())
					for (Product product : productsList)
						System.out.println(
								"Product ID: " + product.getProductID() + "\t\tProduct Name: " + product.getName()
										+ "\t\tCategory: " + product.getCategory() + "\t\tQuantity Available: "
										+ product.getQuantity() + "\t\tUnit Price: " + product.getPrice());
				else
					System.out.println("Inventory is empty!");
				break;

			case 2:
				Product newProduct = new Product();
				System.out.print("\nEnter productID: ");
				newProduct.setProductID(scanner.nextInt());
				System.out.print("Enter product name: ");
				scanner.nextLine();
				newProduct.setName(scanner.nextLine());
				System.out.print("Enter product category: ");
				newProduct.setCategory(scanner.nextLine());
				System.out.print("Enter product price: ");
				newProduct.setPrice(scanner.nextDouble());
				System.out.print("Enter product quantity: ");
				int quantity = scanner.nextInt();
				if (quantity <= 0)
					System.out.println("\nQuantity cannot be zero or negative");
				else {
					newProduct.setQuantity(quantity);
					if (productService.addProduct(newProduct))
						System.out.println("\nProduct added successfully");
					else
						System.out.println("\nProduct was not added, please enter correct details!");
				}
				break;

			case 3:
				System.out.print("\nEnter productID: ");
				productID = scanner.nextInt();
				if (productService.deleteProduct(productID))
					System.out.println("\nProduct deleted successfully");
				else
					System.out.println("\nProduct with ID " + productID + " does not exist!");
				break;

			case 4:
				System.out.print("\nEnter productID: ");
				productID = scanner.nextInt();
				Optional<Product> optionalProduct = productService.searchProduct(productID);
				if (optionalProduct.isPresent()) {
					System.out.print("\n1. Price\n2. Quantity\n3. Both\nSelect field to update: ");
					int updateChoice = scanner.nextInt();
					switch (updateChoice) {
					case 1:
						System.out.print("\nEnter updated price of the product: ");
						optionalProduct.get().setPrice(scanner.nextDouble());
						break;

					case 2:
						System.out.print("\nEnter updated quantity of the product: ");
						optionalProduct.get().setQuantity(scanner.nextInt());
						break;

					case 3:
						System.out.print("\nEnter new price of the product: ");
						optionalProduct.get().setPrice(scanner.nextDouble());
						System.out.print("Enter new quantity of the product: ");
						optionalProduct.get().setQuantity(scanner.nextInt());
						break;

					default:
						System.out.println("\nInvalid choice!");
						break;
					}
					if (updateChoice >= 1 && updateChoice <= 3 && productService.updateProduct(optionalProduct.get()))
						System.out.println("\nProduct updated successfully");
					else
						System.out.println("\nProduct updation failed!");
				} else
					System.out.println("\nProduct with ID " + productID + " does not exist!");
				break;

			case 5:
				System.out.print("\nEnter productID: ");
				productID = scanner.nextInt();
				Optional<Product> productopOptional = productService.searchProduct(productID);
				System.out.println();
				if (productopOptional.isPresent()) {
					Product product = productopOptional.get();
					System.out.println("Product ID: " + product.getProductID() + "\t\tProduct Name: "
							+ product.getName() + "\t\tCategory: " + product.getCategory() + "\t\tQuantity Available: "
							+ product.getQuantity() + "\t\tUnit Price: " + product.getPrice());
				} else
					System.out.println("Product with ID " + productID + " does not exist!");
				break;

			case 6:
				List<Customer> customers = customerService.getAllCustomers();
				System.out.println();
				if (customers.size() > 0)
					for (Customer customer : customers) {
						System.out.print("Customer ID: " + customer.getCustomerID() + "\t\tName: " + customer.getName()
								+ "\t\tPassword: ");
						for (int i = 1; i <= customer.getPassword().length(); i++)
							System.out.print("*");
						System.out.println(
								"\t\tAddress: " + customer.getAddress() + "\t\tPhone Number: " + customer.getPhoneNo());
					}
				else
					System.out.println("No customers yet!!");
				break;

			case 7:
				System.out.print("\nEnter Customer ID: ");
				customerID = scanner.nextInt();
				Optional<Customer> customerOptional = customerService.searchCustomer(customerID);
				System.out.println();
				if (customerOptional.isPresent()) {
					Customer customer = customerOptional.get();
					System.out.print("Customer ID: " + customer.getCustomerID() + "\t\tName: " + customer.getName()
							+ "\t\tPassword: ");
					for (int i = 1; i <= customer.getPassword().length(); i++)
						System.out.print("*");
					System.out.println(
							"\t\tAddress: " + customer.getAddress() + "\t\tPhone Number: " + customer.getPhoneNo());
				} else
					System.out.println("Customer with ID " + customerID + " does not exist!!");
				break;

			case 8:
				System.out.print("\nEnter Customer ID: ");
				customerID = scanner.nextInt();
				if (customerService.deleteCustomer(customerID))
					System.out.println("\nCustomer deleted successfully!");
				else
					System.out.println("\nCustomer with ID " + customerID + "doesn't exist!!");
				break;

			case 9:
				scanner.close();
				System.out.println("\n||  Thank you for using Retail Management Services ||");
				System.exit(0);
				break;

			default:
				System.out.println("\nInvalid choice!!");
			}
		} catch (InputMismatchException ex) {
			System.out.println("\nPlease enter valid input!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean validate(String userName, String password) {
		return userName.equals(USERNAME) && password.equals(PASSWORD);
	}

}
