package entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class Transaction {

	int customerID;
	int transacID;
	int noOfProducts;
	LocalDateTime timestamp;
	double billAmount;

}
