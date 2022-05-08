package presentation;

import java.util.List;
import java.util.Scanner;

import entity.Customer;
import entity.Product;
import service.CartService;
import service.CartServiceImpl;
import service.CustomerService;
import service.CustomerServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;

public class CustomerPresentationImpl implements CustomerPresentation {

	CustomerService customerService = new CustomerServiceImpl();
	ProductService productService = new ProductServiceImpl();
	CartService cartService = new CartServiceImpl();
	private int customerLoggedID = Integer.MIN_VALUE;

	@Override
	public void primaryMenu() {
		System.out.println("Welcome to Retail Store!");
		System.out.println("1. Login");
		System.out.println("2. Register");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		if (choice == 1) {
			System.out.println("Enter Customer ID");
			int customerID = scanner.nextInt();
			System.out.println("Enter Password");
			String password = scanner.next();
			if (customerService.loginCustomer(customerID, password)) {
				customerLoggedID = customerID;
				System.out.println("Logged In Successfully.");
				secondaryMenu();
			} else
				System.out.println("Log In Failed!");
		} else if (choice == 2) {
			System.out.println("Enter Customer ID");
			int customerID = scanner.nextInt();
			System.out.println("Enter Name");
			String name = scanner.next();
			System.out.println("Enter Password");
			String password = scanner.next();
			System.out.println("Enter Address");
			String address = scanner.next();
			System.out.println("Enter Phone Number");
			String phoneNo = scanner.next();
			Customer customer = new Customer(customerID, name, password, address, phoneNo);
			if (customerService.registerCustomer(customer)) {
				customerLoggedID = customerID;
				System.out.println("Registered Successfully.");
				secondaryMenu();
			} else
				System.out.println("Registration Failed!");
		} else {
			System.out.println("Invalid choice!");
		}
	}

	@Override
	public void secondaryMenu() {
		System.out.println("1. List All Products");
		System.out.println("2. Add Product to Cart");
		System.out.println("3. Delete Product from Cart");
		System.out.println("4. Display Cart");
		System.out.println("5. Empty Cart");
		System.out.println("6. CheckOut");
		System.out.println("7. Update Password");
		System.out.println("8. Exit");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			List<Product> products = productService.getAllProducts();
			for (Product product : products) {
			}
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			scanner.close();
			System.exit(0);
			break;
		default:
			System.out.println("Invalid choice!!");
		}
	}

}
