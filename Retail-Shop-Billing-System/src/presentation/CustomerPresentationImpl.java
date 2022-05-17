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
		System.out.println("\n1. Login");
		System.out.println("2. Register");
		System.out.println("3. Exit the Shop");
		System.out.print("Enter your choice: ");
		try {
			Scanner scanner = new Scanner(System.in);
			int customerID = 0;
			String password = "";
			switch (scanner.nextInt()) {
			case 1:
				System.out.print("\nEnter Your ID: ");
				customerID = scanner.nextInt();
				System.out.print("Enter Your Password: ");
				scanner.nextLine();
				password = scanner.nextLine();
				if (customerService.loginCustomer(customerID, password)) {
					customerLoggedID = customerID;
					System.out.println("\nHello Cutomer " + customerLoggedID);
					secondaryMenu();
				} else
					System.out.println("Log In Failed!");
				break;

			case 2:
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
					System.out.println("\nRegistered Successfully.");
					secondaryMenu();
				} else
					System.out.println("\nRegistration Failed!");
				break;

			case 3:
				scanner.close();
				System.out.println("\n||  Thank You For Visiting Retail Shop  ||");
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
	public void secondaryMenu() {
		while (true) {
			System.out.println("\n1. List All Products");
			System.out.println("2. Add Product to Cart");
			System.out.println("3. Display Cart");
			System.out.println("4. Update Password");
			System.out.println("5. Logout");
			System.out.print("Enter your choice: ");
			try {
				Scanner scanner = new Scanner(System.in);
				switch (scanner.nextInt()) {
				case 1:
					List<Product> products = productService.getAllProducts();
					System.out.println();
					if (products.size() > 0)
						for (Product product : products)
							System.out.println("Product ID: " + product.getProductID() + "\t\tProduct Name: "
									+ product.getName() + "\t\tCategory: " + product.getCategory() + "\t\tQuantity: "
									+ product.getQuantity() + "\t\tPrice: " + product.getPrice());
					else
						System.out.println("Sorry we are out of business!!");
					break;

				case 2:
					System.out.print("\nEnter Product Id: ");
					int productID = scanner.nextInt();
					System.out.print("Enter Quantity: ");
					int quantity = scanner.nextInt();
					if (quantity <= 0)
						System.out.println("\nQuantity cannot be zero or negative!!");
					else {
						Optional<Product> productOptional = productService.searchProduct(productID);
						if (productOptional.isPresent()) {
							if (productOptional.get().getQuantity() < quantity)
								System.out.println("\nInsufficient Stock!");
							else if (cartService.addItemsInCart(productOptional.get(), quantity, customerLoggedID))
								System.out.println("\nProduct added to cart successfully!");
						} else
							System.out.println("\nProduct with ID: " + productID + " does not exist!");
					}
					break;

				case 3:
					List<Cart> cartProducts = cartService.getCart(customerLoggedID);
					System.out.println();
					if (cartProducts.size() > 0) {
						for (Cart cart : cartProducts)
							System.out.println("Product ID: " + cart.getProductID() + "  Quantity: "
									+ cart.getQuantity() + "  Price: " + cart.getTotalAmount());
						cartMenu();
					} else
						System.out.println("The cart is empty!");
					break;

				case 4:
					System.out.print("\nEnter old password: ");
					scanner.nextLine();
					String oldPassword = scanner.nextLine();
					System.out.print("Enter new password: ");
					String newPassword = scanner.nextLine();
					if (customerService.updatePasssword(customerLoggedID, oldPassword, newPassword))
						System.out.println("\nPassword changed successfully!");
					else
						System.out.println("\nUnsuccessful!");
					break;

				case 5:
					System.out.println("\n||  Logged Out Successfully  ||");
					primaryMenu();
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
	}

	@Override
	public void cartMenu() {
		System.out.println("\n1. Delete Product from Cart\n2. Checkout\n3. Empty Cart\n4. Return to Main Menu");
		System.out.print("Enter Choice: ");
		try {
			Scanner scanner = new Scanner(System.in);
			switch (scanner.nextInt()) {
			case 1:
				System.out.print("\nEnter Product Id: ");
				int productID = scanner.nextInt();
				if (cartService.deleteItemFromCart(productID, customerLoggedID))
					System.out.println("\nProduct deleted from cart successfully!");
				else
					System.out.println("\nProduct with ID: " + productID + " not in cart!");
				break;

			case 2:
				List<Cart> cartProducts=cartService.getCart(customerLoggedID);
				double totalBillAmount=billService.generateBill(customerLoggedID);
				for(Cart cart:cartProducts)
				{
					Product prod=productService.searchProduct(cart.getProductID()).get();
					double taxRate=billService.getTaxRate(prod.getCategory());
					double taxAmount=billService.getTaxAmount(prod.getCategory(), cart.getQuantity(),prod.getPrice());
					double totalAmountWithTax=billService.totalAmountAfterTax(cart.getTotalAmount(), taxAmount);
					System.out.println("Product ID: "+cart.getProductID()+"\t\tQuantity: "+cart.getQuantity()+"\t\tTotal Amount: "+cart.getTotalAmount()+"\t\tTax Rate: "+taxRate+"\t\tTax Amount: "+taxAmount+"\t\tNet Price:"+totalAmountWithTax);
				}
				System.out.println("Total Bill Amount = " + totalBillAmount);
				System.out.println("Thank you for shopping with us");
				break;

			case 3:
				if (productService.updateProductsQuantity(customerLoggedID) && cartService.emptyCart(customerLoggedID))
					System.out.println("\nCart emptied successfully!");
				else
					System.out.println("\nCart could not be emptied, please contact customer care!");
			case 4:
				secondaryMenu();
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

}
