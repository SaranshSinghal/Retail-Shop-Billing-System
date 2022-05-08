package presentation;

import java.util.Optional;
import java.util.Scanner;

import entity.Product;
import service.ProductService;
import service.ProductServiceImpl;

public class AdminPresentationImpl implements AdminPresentation {

	
	ProductService productService=new ProductServiceImpl();
	@Override
	public void showMenu() {
		System.out.println("1.Add new Product");
		System.out.println("2.Delete product");
		System.out.println("3.Update product");
		System.out.println("4.Search product by productID");
		System.out.println("5.Exit");
	}

	@Override
	public void performMenu(int choice) {
		Scanner scanner=new Scanner(System.in);
		int productID=0;
		switch(choice) {
		case 1:Product newProduct=new Product();
			System.out.println("Enter productID:");
			newProduct.setProductID(scanner.nextInt());
			System.out.println("Enter product name:");
			newProduct.setName(scanner.next());
			System.out.println("Enter product category:");
			newProduct.setCategory(scanner.next());
			System.out.println("Enter product price:");
			newProduct.setPrice(scanner.nextDouble());
			System.out.println("Enter product quantity:");
			newProduct.setQuantity(scanner.nextInt());
			if(productService.addProduct(newProduct))
				System.out.println("Product added successfully");
			else
				System.out.println("Product was not added! Please enter correct details");
			break;
		case 2:System.out.println("Enter productID:");
			if(productService.deleteProduct(scanner.nextInt()))
				System.out.println("Product deleted successfully");
			else
				System.out.println("Product deletion failed! No such product exists");
			break;
		case 3:System.out.println("Enter productID:");
			productID=scanner.nextInt();
			Optional<Product> optionalProduct=productService.getProduct(productID);
			if(optionalProduct.isPresent())
			{
				System.out.println("Select field to update\n1.price/n2.quantity\n3.Both");
				int updateChoice=scanner.nextInt();
				if(updateChoice==1) {
					System.out.println("Enter new price of the product:");
					optionalProduct.get().setPrice(scanner.nextDouble());
				}
				else if(updateChoice==2) {
					System.out.println("Enter new quantity of the product:");
					optionalProduct.get().setQuantity(scanner.nextInt());
				}
				else if(updateChoice==3) {
					System.out.println("Enter new price of the product:");
					optionalProduct.get().setPrice(scanner.nextDouble());
					System.out.println("Enter new quantity of the product:");
					optionalProduct.get().setQuantity(scanner.nextInt());
				}
				else {
					System.out.println("Invalid choice!");
				}
				if(updateChoice>=1 && updateChoice<=3) {
					if(productService.updateProduct(optionalProduct.get()))
						System.out.println("Product updated successfully");
					else
						System.out.println("Product updation failed!");
				}
			}
			else
				System.out.println("Product with "+productID+" does not exist");
			break;
		case 4:System.out.println("Enter productID:");
			productID=scanner.nextInt();
			Optional<Product> product=productService.getProduct(productID);
			if(product.isPresent())
				System.out.println(product);
			else
				System.out.println("Product with "+productID+" does not exist");
			break;
		case 5:System.exit(0);
			break;
		default:System.out.println("Invalid choice! Please enter valid choice");
		}
	}

}
