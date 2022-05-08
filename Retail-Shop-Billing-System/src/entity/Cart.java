package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Cart {

	int customerID;
	int productID;
	int quantity;
	double totalAmount;

}
