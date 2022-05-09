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
		
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter your choice\n1.Admin\n2.Customer");
		int choice1=scanner.nextInt();
		if(choice1==1) {
			System.out.println("Enter username:");
			String userName=scanner.next();
			System.out.println("Enter password");
			String password=scanner.next();
			if(adminPresentation.validate(userName, password)) {
				while(true) {
					adminPresentation.showMenu();
					System.out.println("Enter your choice:");
					int choice2=scanner.nextInt();
					adminPresentation.performMenu(choice2);
				}
			}
			else
				System.out.println("Invalid username or password!");
		}
		else if(choice1==2) {
			customerPresentationImpl.primaryMenu();
		}
		else
			System.out.println("Invalid choice!");
	}

}
