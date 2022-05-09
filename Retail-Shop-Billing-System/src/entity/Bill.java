package entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Bill {

	int customerID;
	int productID;
	LocalDateTime timestamp;
	int quantity;
	double totalAmount;

}
