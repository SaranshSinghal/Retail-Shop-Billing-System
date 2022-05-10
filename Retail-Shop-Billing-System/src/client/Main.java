package client;

import java.util.Scanner;

import presentation.AdminPresentation;
import presentation.AdminPresentationImpl;
import presentation.CustomerPresentation;
import presentation.CustomerPresentationImpl;

public class Main {

	public static void main(String[] args) {
		CustomerPresentation customerPresentationImpl = new CustomerPresentationImpl();
		AdminPresentation adminPresentation = new AdminPresentationImpl();
		Scanner scanner = new Scanner(System.in);
		System.out.println("||  Welcome to Retail Store E-Commerce Services  ||");
		while (true) {
			System.out.print("\n1. Admin\n2. Customer\n3. Exit\n\nEnter your choice: ");
			int choice1 = scanner.nextInt();
			switch (choice1) {
			case 1:
				System.out.print("\nEnter username: ");
				String userName = scanner.next();
				System.out.print("Enter password: ");
				scanner.nextLine();
				String password = scanner.nextLine();
				if (adminPresentation.validate(userName, password)) {
					while (true) {
						adminPresentation.showMenu();
						System.out.print("Enter your choice: ");
						int choice2 = scanner.nextInt();
						adminPresentation.performMenu(choice2);
					}
				} else
					System.out.println("Invalid username or password!");
				break;
			case 2:
				customerPresentationImpl.primaryMenu();
				break;
			case 3:
				scanner.close();
				System.out.println("||  THANK YOU  ||");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice!!");
				break;
			}
		}
	}

}
