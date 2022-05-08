package client;

import presentation.AdminPresentation;
import presentation.AdminPresentationImpl;
import presentation.CustomerPresentation;
import presentation.CustomerPresentationImpl;

public class Main {

	public static void main(String[] args) {
		CustomerPresentation customerPresentationImpl = new CustomerPresentationImpl();
		AdminPresentation adminPresentation = new AdminPresentationImpl();
		customerPresentationImpl.primaryMenu();
	}

}
