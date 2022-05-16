package presentation;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import entity.Cart;
import entity.Customer;
import entity.Product;
import service.BillService;
import service.BillServiceImpl;
import service.CartService;
import service.CartServiceImpl;
import service.CustomerService;
import service.CustomerServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;

public class CustomerPresentationImpl implements CustomerPresentation {

	private CustomerService customerService = new CustomerServiceImpl();
	private ProductService productService = new ProductServiceImpl();
	private CartService cartService = new CartServiceImpl();
	private BillService billService = new BillServiceImpl();
	private int customerLoggedID = Integer.MIN_VALUE;

	@Override
	public void primaryMenu() {
		System.out.println("\nHello Customer!");
		System.out.println("\n1. Login");
		System.out.println("2. Register");
		System.out.println("3. Exit");
		System.out.print("Enter your choice: ");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		int customerID = 0;
		String password = "";
		switch (choice) {
		case 1:
			try {
				System.out.print("Enter Your ID: ");
				customerID = scanner.nextInt();
				System.out.print("Enter Your Password: ");
				scanner.nextLine();
				password = scanner.nextLine();
			} catch (InputMismatchException ex) {
				System.out.println("Please Enter valid input!");
			}
			if (customerService.loginCustomer(customerID, password)) {
				customerLoggedID = customerID;
				System.out.println("Logged In Successfully.");
				secondaryMenu();
			} else
				System.out.println("Log In Failed!");
			break;

		case 2:
			try {
				System.out.print("Enter Customer ID: ");
				customerID = scanner.nextInt();
				System.out.print("Enter Your Name: ");
				scanner.nextLine();
				String name = scanner.nextLine();
				System.out.print("Enter Your Password: ");
				password = scanner.nextLine();
				System.out.print("Enter Your Address: ");
				String address = scanner.nextLine();
				System.out.print("Enter Your Phone Number: ");
				String phoneNo = scanner.next();
				Customer customer = new Customer(customerID, name, password, address, phoneNo);
				if (customerService.registerCustomer(customer)) {
					customerLoggedID = customerID;
					System.out.println("Registered Successfully.");
					secondaryMenu();
				} else
					System.out.println("Registration Failed!");
			} catch (InputMismatchException ex) {
				System.out.println("Please Enter valid input!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;

		case 3:
			scanner.close();
			System.out.println("||  Thank you for visiting  ||");
			System.exit(0);
			break;

		default:
			System.out.println("Invalid choice!");
		}
	}

	@Override
	public void secondaryMenu() {
		
		
		while (true) {
			System.out.println("\n1. List All Products");
			System.out.println("2. Add Product to Cart");
			System.out.println("3.Display Cart");
			System.out.println("4. Update Password");
			System.out.println("5. Logout");
			System.out.print("Enter your choice: ");
			try {
				Scanner scanner = new Scanner(System.in);
				int choice = scanner.nextInt(), productID = 0;
				List<Cart> cartProducts = null;
				switch (choice) {
				case 1:
					List<Product> products = productService.getAllProducts();
					if (products.size() > 0)
						for (Product product : products)
							System.out.println("Product ID: " + product.getProductID() + "\t\tProduct Name: "
									+ product.getName() + "\t\tCategory: " + product.getCategory() + "\t\tQuantity: "
									+ product.getQuantity() + "\t\tPrice: " + product.getPrice());
					else
						System.out.println("Sorry we are out of business!!");
					break;

				case 2:
					System.out.print("Enter Product Id: ");
					productID = scanner.nextInt();
					System.out.print("Enter Quantity: ");
					int quantity = scanner.nextInt();
					if (quantity <= 0)
						System.out.println("Quantity cannot be zero or negative!!");
					else {
						Optional<Product> productOptional = productService.getProduct(productID);
						if (productOptional.isPresent()) {
							if (productOptional.get().getQuantity() < quantity)
								System.out.println("Insufficient Stock!");
							else if (cartService.addItemsInCart(productOptional.get(), quantity, customerLoggedID))
								System.out.println("Product added to cart successfully!");
						} else
							System.out.println("Product with ID " + productID + " does not exist!");
					}
					break;

				

				case 3:
					cartProducts = cartService.getCart(customerLoggedID);
					if (cartProducts.size() > 0) {
						for (Cart cart : cartProducts) 
							System.out.println("Product ID: " + cart.getProductID() + "  Quantity: "
									+ cart.getQuantity() + "  Total Amount: " + cart.getTotalAmount());
					    cartFunctions();
					    }
					else
						System.out.println("The cart is empty!");
					break;

			

				case 4:
					System.out.print("Enter old password: ");
					scanner.nextLine();
					String oldPassword = scanner.nextLine();
					System.out.print("Enter new password: ");
					String newPassword = scanner.nextLine();
					if (customerService.updatePasssword(customerLoggedID, oldPassword, newPassword))
						System.out.println("Password changed successfully!");
					else
						System.out.println("Unsuccessful!");
					break;

				case 5:
					System.out.println("||  Logged Out Successfully  ||");
					primaryMenu();
					break;

				default:
					System.out.println("Invalid choice!!");
				}
			} catch (InputMismatchException ex) {
				System.out.println("Please Enter valid input!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	@Override
	public void cartFunctions() {
		System.out.println("1:Delete Product from Cart\n2:Checkout.\n3:Delete Cart");
		System.out.println("Enter Choice");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		switch (choice){
		case 1:
			
			System.out.print("Enter Product Id: ");
			int productID = scanner.nextInt();
			if (cartService.deleteItemFromCart(productID, customerLoggedID))
				System.out.println("Product deleted from cart successfully!");
			else
				System.out.println("Product with ID: " + productID + " not in cart!");
			
			break;
		case 2:
			if (billService.generateBill(customerLoggedID)) {
				List<Cart> displayCart = cartService.getCart(customerLoggedID);
				for (Cart cart : displayCart)
					System.out.println("Product ID: " + cart.getProductID() + "\t\tQuantity: "
							+ cart.getQuantity() + "\t\tTotal Amount: " + cart.getTotalAmount());
				if (cartService.emptyCart(customerLoggedID))
					System.out.println("Thank you for shopping with us");
			} else
				System.out.println("Bill generation failed!");
			break;
			
		case 3:
			
			List<Cart>cartProducts = cartService.getCart(customerLoggedID);
			if (cartProducts.size() > 0) {
				for (Cart cart : cartProducts) {
					Product product = productService.getProduct(cart.getProductID()).get();
					product.setQuantity(product.getQuantity() + cart.getQuantity());
					productService.updateProduct(product);
				}
				if (cartService.emptyCart(customerLoggedID))
					System.out.println("Cart deleted successfully!");
			} else
				System.out.println("The cart is empty!");
			
		}
			
		
	}

}
