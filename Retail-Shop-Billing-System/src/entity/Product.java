package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class Product {

	int id;
	String name;
	String category;
	double price;
	int quantity;

}
