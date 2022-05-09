package presentation;

public interface AdminPresentation {

	boolean validate(String userName, String password);

	void showMenu();

	void performMenu(int choice);

}
